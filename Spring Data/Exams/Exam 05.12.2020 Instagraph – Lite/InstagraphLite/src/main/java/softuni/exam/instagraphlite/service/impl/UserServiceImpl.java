package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.DTO.UserSeedDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static String USERS_PATH_FILE = "src/main/resources/files/users.json";

    private final UserRepository userRepository;

    private final PictureRepository pictureRepository;

    private final Gson gson;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_PATH_FILE));
    }

    @Override
    public String importUsers() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readFromFileContent(), UserSeedDto[].class))
                .filter(userSeedDto -> {
                    boolean isValid = validationUtil.isValid(userSeedDto);

                    Optional<User> userByUserName = userRepository.findByUsername(userSeedDto.getUsername());

                    if (userByUserName.isPresent()) {
                        isValid = false;
                    }

                    Optional<Picture> pictureByPath = this.pictureRepository.findByPath(userSeedDto.getProfilePicture());

                    if (pictureByPath.isEmpty()) {
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported User: %s",
                                    userSeedDto.getUsername()) : "Invalid User")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(userSeedDto -> {
                    User user = modelMapper.map(userSeedDto, User.class);

                    Optional<Picture> userPicture = pictureRepository.findByPath(userSeedDto.getProfilePicture());

                    if (userPicture.isPresent()) {
                        user.setProfilePicture(userPicture.get());
                    }

                    return user;
                })
                .forEach(userRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder sb = new StringBuilder();
        List<User> users = this.userRepository.usersOrderedByCountPosts();

        for (User user : users) {
            sb.append(String.format("""
                            User: %s
                            Post count: %d
                            ==Post Details:
                            """
                    , user.getUsername(), user.getPosts().size()
            ));

            List<Post> postsOrdered = this.userRepository.getAllPostsByUser(user);
            for (Post post : postsOrdered) {
                sb.append(String.format("----Caption: %s\n" +
                                "----Picture Size: %.2f\n"
                        , post.getCaption(), post.getPictures().getSize()));
            }
        }
        return sb.toString();
    }
}
