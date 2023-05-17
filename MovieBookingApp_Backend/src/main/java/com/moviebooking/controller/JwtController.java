package com.moviebooking.controller;

import com.moviebooking.dto.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moviebooking.dto.JwtRequest;
import com.moviebooking.service.JwtService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class JwtController {
	Logger logger = LoggerFactory.getLogger(JwtController.class);
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
	
	@PostMapping("/signIn")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) {
//		System.out.println("in login request");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        if(authentication.isAuthenticated()){
            logger.info("----------------User Logged in and token generated------------------");
            return jwtService.generateToken(jwtRequest.getUserName());
        }else{
            logger.info("----------------invalid user request!------------------");
            throw new UsernameNotFoundException("invalid user request!");
        }
    }

}
