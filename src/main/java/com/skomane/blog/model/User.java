package com.skomane.blog.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(name = "user_name")
    private String userName;

    @NotBlank(message = "Password is required")
    @Column(name = "password")
    private String password;

    @Email
    @NotEmpty(message = "Email is required")
    @Column(name = "email")
    private String email;

    private boolean enabled;

}
