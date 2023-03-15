package org.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int userId;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private double salary;
    private Role role;
}