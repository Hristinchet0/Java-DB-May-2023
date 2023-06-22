package com.example.accountsystem.services;

import com.example.accountsystem.models.User;
import com.example.accountsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User user) {
        Optional<User> found = userRepository
                .findByUsername(user.getUsername());

        if (found.isEmpty()) {
            this.userRepository.save(user);
        }


    }
}