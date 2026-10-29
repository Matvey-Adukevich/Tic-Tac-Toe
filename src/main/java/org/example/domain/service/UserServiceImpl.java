package org.example.domain.service;

import org.example.datasource.repository.UserRepository;
import org.example.domain.model.User;

import java.util.UUID;

public class UserServiceImpl implements  UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerUser(User user) {
        try{
            userRepository.saveUser(user);
            System.out.println("User saved successfully: " + user.getLogin());
        }catch (Exception e){
            System.out.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        return user;
    }

    @Override
    public User findByUUID(UUID uuid) {
        User user = userRepository.findByUUID(uuid);
        return user;
    }
}
