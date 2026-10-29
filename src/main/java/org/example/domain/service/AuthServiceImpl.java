package org.example.domain.service;

import org.example.domain.model.RegistrationRequest;
import org.example.domain.model.User;

import java.util.Base64;
import java.util.UUID;

public class AuthServiceImpl implements AuthService{
    private final UserService userService;

    public AuthServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean register(RegistrationRequest registrationRequest) {
        User existsUser = userService.findByLogin(registrationRequest.getLogin());
        if(existsUser != null){
            System.out.println("Registration failed: user already exists - " + registrationRequest.getLogin());
            return false;
        }
        User user = new User(UUID.randomUUID(), registrationRequest.getLogin(), registrationRequest.getPassword());
        boolean result = userService.registerUser(user);
        System.out.println("Registration result for " + registrationRequest.getLogin() + ": " + result);
        return result;
    }

    @Override
    public UUID login(String user_) {
        String decoded = new String(Base64.getDecoder().decode(user_));
        String[] parts = decoded.split(":");
        String login = parts[0];
        String password = parts[1];
        User user = userService.findByLogin(login);
        if(user != null && user.getPassword().equals(password)){
            return user.getUUID();
        }
        return null;
    }
}
