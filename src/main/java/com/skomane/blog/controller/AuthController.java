package com.skomane.blog.controller;

import com.skomane.blog.dto.AuthenticationResponseDTO;
import com.skomane.blog.dto.LoginRequestDTO;
import com.skomane.blog.dto.RegisterRequestDTO;
import com.skomane.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequestDTO registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity("User Registration Successful", HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO login(@RequestBody LoginRequestDTO loginRequest){
        return authService.login(loginRequest);
    }

}
