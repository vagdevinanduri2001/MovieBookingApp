package com.moviebooking.service;

import com.moviebooking.entity.Customer;

public interface CustomerService {

    Customer registerCustomer(Customer customer) throws Exception;

    String changePassword(String username, String password);

    String forgotPassword(String userName, String password, String confirmPassword);

    //Customer updateCustomer(Customer customer);

}
