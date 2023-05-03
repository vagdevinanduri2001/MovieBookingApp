package com.moviebooking.jwtConfig;

import com.moviebooking.entity.Customer;
import com.moviebooking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Customer> customer = repository.findByUserName(username);
        return customer.map(CustomerUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("user not found: "+username));
    }
}
