package com.skomane.blog.service.impl;

import com.skomane.blog.dto.AuthenticationResponseDTO;
import com.skomane.blog.dto.LoginRequestDTO;
import com.skomane.blog.dto.RegisterRequestDTO;
import com.skomane.blog.exception.PostNotFoundException;
import com.skomane.blog.exception.SpringBlogException;
import com.skomane.blog.model.NotificationEmail;
import com.skomane.blog.model.User;
import com.skomane.blog.model.VerificationToken;
import com.skomane.blog.repository.UserRepository;
import com.skomane.blog.repository.VerificationTokenRepository;
import com.skomane.blog.security.JwtProvider;
import com.skomane.blog.service.AuthService;
import com.skomane.blog.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Override
    public void signup(RegisterRequestDTO registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateTokenUser(user);
        String subject = "Please Activate your Account";
        String body = "Thank you for signing up yo Spring Blog, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8081/api/v1/auth/accountVerification/" + token;
        mailService.sendMail(new NotificationEmail(subject, user.getEmail(), body));
    }

    private String generateTokenUser(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponseDTO(authenticationToken, loginRequest.getUsername());
    }

    @Override
    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser(){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    @Override
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new PostNotFoundException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUserName();
        User user = userRepository.findByUserName(username).orElseThrow(() ->
                new SpringBlogException("User not found with username " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
