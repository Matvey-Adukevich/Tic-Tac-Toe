package org.example.datasource.mapper;

import org.example.datasource.model.UserEntity;
import org.example.domain.model.User;

public class UserEntityMapper {
    public User toDomain(UserEntity userEntity){
        User user = new User(userEntity.getUUID(), userEntity.getLogin(), userEntity.getPassword());
        return user;
    }

    public UserEntity toEntity(User user){
        UserEntity userEntity = new UserEntity(user.getUUID(), user.getLogin(), user.getPassword());
        return userEntity;
    }
}
