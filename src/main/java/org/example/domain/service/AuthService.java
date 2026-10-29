package org.example.domain.service;

import org.example.domain.model.RegistrationRequest;

import java.util.UUID;

public interface AuthService {
    boolean register(RegistrationRequest registrationRequest);
    UUID login(String user);
}
