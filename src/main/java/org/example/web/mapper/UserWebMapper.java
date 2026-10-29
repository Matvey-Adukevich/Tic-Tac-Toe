package org.example.web.mapper;

import org.example.domain.model.User;
import org.example.web.model.UserDTO;

public class UserWebMapper {
    public User toDomain(UserDTO userDTO){
        User user = new User(userDTO.getUUID(), userDTO.getLogin(), userDTO.getPassword());
        return user;
    }

    public UserDTO toWeb(User user){
        UserDTO userDTO = new UserDTO(user.getUUID(), user.getLogin(), user.getPassword());
        return userDTO;
    }
}
