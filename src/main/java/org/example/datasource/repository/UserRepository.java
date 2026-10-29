package org.example.datasource.repository;

import org.example.domain.model.User;

import java.util.UUID;

public interface UserRepository {
    void saveUser(User user);
    User findByLogin(String login);
    User findByUUID(UUID uuid);
}
