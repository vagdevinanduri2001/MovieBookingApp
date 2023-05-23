package com.moviebooking.service;

import com.moviebooking.entity.Customer;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.CustomerAlreadyExistsException;
import com.moviebooking.exception.CustomerNotFoundException;
import com.moviebooking.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTests {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private SequenceGenerator sequenceGenerator;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("userFirst", "userLast", "abc@gmail.com", 1, "user123", "pass123", "pass123", 1111111111, "user");
    }

    @Test
    public void registerCustomer_Success() throws Exception {
        when(sequenceGenerator.getSequenceNumber("customer_seq")).thenReturn(1);
        when(passwordEncoder.encode("pass")).thenReturn("pass");
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer customer1 = customerService.registerCustomer(customer);
        assertThat(customer1).isNotNull();
    }
    @Test
    public void registerCustomer_ExistByEmail() throws Exception {
        when(customerRepository.existsByEmailId(customer.getEmailId())).thenReturn(true);
        CustomerAlreadyExistsException e =assertThrows(CustomerAlreadyExistsException.class, () -> {
            customerService.registerCustomer(customer);
        });
        verify(customerRepository, never()).save(any(Customer.class));
        assertEquals("Customer already existed with this email!",e.getMessage());
    }

    @Test
    public void registerCustomer_ExistByLoginId(){
        when(customerRepository.existsByLoginId(customer.getLoginId())).thenReturn(true);
        CustomerAlreadyExistsException e =assertThrows(CustomerAlreadyExistsException.class,()->{
            customerService.registerCustomer(customer);
        });
        verify(customerRepository, never()).save(any(Customer.class));
        assertEquals("Customer exists with this login Id!",e.getMessage());
    }

    @Test
    public void registerCustomer_PasswordsIncorrect(){
        Customer customer1 = new Customer("userFirst", "userLast", "abc@gmail.com", 1, "user123", "pass", "pass123", 1111111111, "user");
        CommonException e = assertThrows(CommonException.class,()->{customerService.registerCustomer(customer1);});
        verify(customerRepository, never()).save(any(Customer.class));
        assertEquals("Password and confirm Password fields must be equal.",e.getMessage());
    }
    @Test
    public void forgotPassword_Success(){
        when(customerRepository.findByUserName("user123")).thenReturn(Optional.of(customer));
        String result = customerService.changePassword("user123","pass123");
        assertEquals("password updated successfully",result);
    }
    @Test
    public void forgotPassword_CommonException(){
        when(customerRepository.findByUserName("user123")).thenReturn(Optional.of(customer));
        CommonException e = assertThrows(CommonException.class,()->{customerService.changePassword("user123","");});
        verify(customerRepository, never()).save(any(Customer.class));
        assertEquals("password cannot be null",e.getMessage());
    }
    @Test
    public void forgotPassword_CustomerException(){
        CustomerNotFoundException e = assertThrows(CustomerNotFoundException.class,()->{customerService.changePassword("user123","pass123");});
        verify(customerRepository, never()).save(any(Customer.class));
        assertEquals("Customer not found with username: user123",e.getMessage());
    }

}