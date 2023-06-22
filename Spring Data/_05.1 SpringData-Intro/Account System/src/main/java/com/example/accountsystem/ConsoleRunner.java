package com.example.accountsystem;

import com.example.accountsystem.models.User;
import com.example.accountsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        User first = new User("Hristina", 32);
        userService.registerUser(first);

        User second = new User("Hristina", 32);
        userService.registerUser(second);

    }
}
