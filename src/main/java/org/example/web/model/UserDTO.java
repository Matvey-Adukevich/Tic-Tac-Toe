package org.example.web.model;

import java.util.UUID;

public class UserDTO {
    private UUID uuid;
    private String login;
    private String password;

    public UserDTO(UUID uuid, String login, String password){
        this.uuid = uuid;
        this.login = login;
        this.password = password;
    }

    public UUID getUUID(){
        return this.uuid;
    }

    public String getLogin(){
        return this.login;
    }

    public String getPassword(){
        return this.password;
    }
}
