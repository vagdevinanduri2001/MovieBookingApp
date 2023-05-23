package com.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForgotPassword {
    private String password;
    private String confirmPassword;
}
