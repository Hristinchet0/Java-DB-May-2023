package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.DTO.PostSeedRootXMLDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private static String POSTS_FILE_PATH = "src/main/resources/files/posts.xml";

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PictureRepository pictureRepository;

    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    private final XmlParser xmlParser;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_FILE_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(POSTS_FILE_PATH, PostSeedRootXMLDto.class)
                .getPost()
                .stream()
                .filter(postSeedXMLDto -> {
                    boolean isValid = validationUtil.isValid(postSeedXMLDto);

                    Optional<User> userByUsername = userRepository.findByUsername(postSeedXMLDto.getUser().getUsername());

                    if (userByUsername.isEmpty()) {
                        isValid = false;
                    }

                    Optional<Picture> pictureByPath = pictureRepository.findByPath(postSeedXMLDto.getPicture().getPath());

                    if (pictureByPath.isEmpty()) {
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported Post, made by %s",
                                    postSeedXMLDto.getUser().getUsername()) : "Invalid Post")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(postSeedXMLDto -> {
                    Post post = modelMapper.map(postSeedXMLDto, Post.class);

                    Optional<User> userByUsername = userRepository.findByUsername(postSeedXMLDto.getUser().getUsername());
                    Optional<Picture> pictureByPath = pictureRepository.findByPath(postSeedXMLDto.getPicture().getPath());

                    post.setUser(userByUsername.get());
                    post.setPictures(pictureByPath.get());

                    return post;
                })
                .forEach(postRepository::save);

        return sb.toString().trim();
    }
}
