package org.example.domain.model;

public class RegistrationRequest {
    private String login;
    private String password;

    public RegistrationRequest(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
