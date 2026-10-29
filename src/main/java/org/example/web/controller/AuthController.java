package org.example.web.controller;

import org.example.domain.model.RegistrationRequest;
import org.example.domain.service.AuthService;
import org.example.web.mapper.SignUpRequestWebMapper;
import org.example.web.model.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final SignUpRequestWebMapper signUpRequestWebMapper;
    private final AuthService authService;

    public AuthController(SignUpRequestWebMapper signUpRequestWebMapper, AuthService authService) {
        this.signUpRequestWebMapper = signUpRequestWebMapper;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody SignUpRequest signUpRequest){
        RegistrationRequest registrationRequest = signUpRequestWebMapper.toDomain(signUpRequest);
        if(authService.register(registrationRequest) == true){
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body("Registration failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestHeader("Authorization") String authHeader){
        String auth = authHeader.replace("Basic ", "");
        UUID uuid = authService.login(auth);
        if(uuid != null){
            return ResponseEntity.ok("uuid: "+ uuid);
        } else{
            return ResponseEntity.status(401).body("Unauthorized response");
        }

    }
}
