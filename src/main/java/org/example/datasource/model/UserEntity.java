package org.example.datasource.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @Column(name="uuid")
    private UUID uuid;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    public UserEntity(){
        this.uuid = null;
        this.login = null;
        this.password = null;
    }

    public UserEntity(UUID uuid, String login, String password){
        this.uuid = uuid;
        this.login = login;
        this.password = password;
    }

    public UUID getUUID(){
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
