package com.moviebooking.controller;

import com.moviebooking.exception.CustomerNotFoundException;
import com.moviebooking.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.moviebooking.entity.Customer;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.CustomerAlreadyExistsException;
import com.moviebooking.service.CustomerServiceImpl;

import jakarta.validation.Valid;


@RestController
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private JwtService jwtService;

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Customer customer) throws MethodArgumentNotValidException {
        try {
            customerService.registerCustomer(customer);
            logger.info("----------------Customer Created------------------");
            return new ResponseEntity<>("You have successfully registered...you can Login now!", HttpStatus.CREATED);
        } catch (CustomerAlreadyExistsException e) {
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (CommonException e) {
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/{userName}/forgot")
    @PreAuthorize(value = "hasAuthority('User')")
    public ResponseEntity<?> forgotPassword(@RequestHeader("Authorization") String token, @PathVariable String userName, @RequestBody String password) {
        String username = jwtService.extractUsername(token.substring(7));
        if (userName.equals(username)) {
            try {
                String result = customerService.forgotPassword(userName, password);
                logger.info("----------------Password changed------------------");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (CustomerNotFoundException e) {
                logger.info("----------------"+e.getMessage()+"------------------");
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            } catch (CommonException e) {
                logger.info("----------------"+e.getMessage()+"------------------");
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            logger.info("----------------You are not Authorized to change the password------------------");
            throw new CommonException("You are not Authorized to change the password");
        }

    }

}
