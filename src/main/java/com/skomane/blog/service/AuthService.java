package com.skomane.blog.service;

import com.skomane.blog.dto.AuthenticationResponseDTO;
import com.skomane.blog.dto.LoginRequestDTO;
import com.skomane.blog.dto.RegisterRequestDTO;

import java.util.Optional;

public interface AuthService {
    void signup(RegisterRequestDTO registerRequest);
    AuthenticationResponseDTO login(LoginRequestDTO loginRequest);
    Optional<org.springframework.security.core.userdetails.User> getCurrentUser();
    void verifyAccount(String token);
}
