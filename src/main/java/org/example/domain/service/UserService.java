package org.example.domain.service;

import org.example.domain.model.User;

import java.util.UUID;

public interface UserService {
    boolean registerUser(User user);
    User findByLogin(String login);
    User findByUUID(UUID uuid);
}
