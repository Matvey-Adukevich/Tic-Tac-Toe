package org.example.web.controller;

import org.example.domain.model.User;
import org.example.domain.service.UserService;
import org.example.web.mapper.UserWebMapper;
import org.example.web.model.UserDTO;
import org.example.web.model.UserStatDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;
    private final UserWebMapper userWebMapper;

    public UserController(UserService userService, UserWebMapper userWebMapper){
        this.userService = userService;
        this.userWebMapper = userWebMapper;
    }

    @GetMapping("/user/{uuid}")
    public ResponseEntity<UserStatDTO> getUserByUuid(@PathVariable UUID uuid){
        User user = userService.findByUUID(uuid);
        UserDTO userDTO = this.userWebMapper.toWeb(user);
        UserStatDTO userStatDTO = new UserStatDTO(userDTO.getUUID(), userDTO.getLogin());
        return ResponseEntity.ok(userStatDTO);
    }
}
