package org.example.web.mapper;

import org.example.domain.model.RegistrationRequest;
import org.example.web.model.SignUpRequest;

public class SignUpRequestWebMapper {
    public RegistrationRequest toDomain(SignUpRequest signUpRequest){
        RegistrationRequest registrationRequest =
                new RegistrationRequest(signUpRequest.getLogin(), signUpRequest.getPassword());
        return registrationRequest;
    }
}
