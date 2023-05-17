package com.moviebooking.dto;

import com.moviebooking.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private Customer customer;
    private String token;
}
