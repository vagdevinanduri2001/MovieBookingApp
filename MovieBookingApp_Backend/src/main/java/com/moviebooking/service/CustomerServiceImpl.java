package com.moviebooking.service;

import com.moviebooking.entity.Customer;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.CustomerAlreadyExistsException;
import com.moviebooking.exception.CustomerNotFoundException;
import com.moviebooking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer registerCustomer(Customer customer) throws Exception {
        if(customerRepository.existsByUserName(customer.getUserName())){
            throw new CustomerAlreadyExistsException("Customer already existed with this userName!");
        }else if (customerRepository.existsByEmailId(customer.getEmailId())) {
            throw new CustomerAlreadyExistsException("Customer already existed with this email!");
        } else if (customerRepository.existsByLoginId(customer.getLoginId())) {
            throw new CustomerAlreadyExistsException("Customer exists with this login Id!");
        } else if (!(customer.getPassword().equals(customer.getConfirmPassword()))) {
            throw new CommonException("Password and confirm Password fields must be equal.");
        } else {
            customer.setLoginId(sequenceGenerator.getSequenceNumber(Customer.SEQUENCE_NAME));
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customer.setConfirmPassword(passwordEncoder.encode(customer.getConfirmPassword()));
            return customerRepository.save(customer);
        }

    }

    public String forgotPassword(String username, String password) {

        Optional<Customer> customer = customerRepository.findByUserName(username);

        if (customer.isPresent()) {
            if(password==null || password=="") {
                throw new CommonException("password cannot be null");
            }else {
                customer.get().setPassword(passwordEncoder.encode(password));

                customer.get().setConfirmPassword(passwordEncoder.encode(password));

                customerRepository.save(customer.get());
                return "password updated successfully";
            }
        } else {
            throw new CustomerNotFoundException("Customer not found with username: " + username);
        }

    }

}
