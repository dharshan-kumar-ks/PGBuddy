package com.example.pgbuddy.Dtos;

import lombok.Getter;
import lombok.Setter;

// DTO class for SignUp request
@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String password;

    // getters & setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
