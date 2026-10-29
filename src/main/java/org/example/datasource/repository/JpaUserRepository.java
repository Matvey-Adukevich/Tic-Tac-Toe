package org.example.datasource.repository;

import org.example.datasource.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JpaUserRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByLogin(String login);
}
