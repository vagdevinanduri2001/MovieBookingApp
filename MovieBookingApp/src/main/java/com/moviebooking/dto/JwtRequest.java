package com.moviebooking.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    private String userName;
    private String password;
}
