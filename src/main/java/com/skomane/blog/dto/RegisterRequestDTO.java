package com.skomane.blog.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;
    private boolean enabled;
}
