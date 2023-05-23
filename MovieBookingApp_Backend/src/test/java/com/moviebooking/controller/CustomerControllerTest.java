package com.moviebooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebooking.entity.Customer;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.CustomerAlreadyExistsException;
import com.moviebooking.exception.CustomerNotFoundException;
import com.moviebooking.exception.ExceptionHandler;
import com.moviebooking.service.CustomerServiceImpl;
import com.moviebooking.service.JwtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerServiceImpl customerService;
    @Mock
    private JwtService jwtService;

    private MockMvc mockMvc;
    private Customer customer;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).setControllerAdvice(new ExceptionHandler()).build();
        customer = new Customer("userFirst", "userLast", "abc@gmail.com", 1, "Kiran123", "pass123", "pass123", 1111111111, "user");
    }
    @Test
    public void registerTest_success() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("You have successfully registered...you can Login now!");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer))).andReturn();
        assertEquals(201,result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }

    @Test
    public void registerTest_CustomerExistsException() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Customer already existed with this email!");
        Mockito.when(customerService.registerCustomer(any(Customer.class))).thenThrow(new CustomerAlreadyExistsException("Customer already existed with this email!"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))).andReturn();
        assertEquals(406,result.getResponse().getStatus());
        assertEquals(response.getBody(),result.getResponse().getContentAsString());
    }
    @Test
    public void registerTest_CommonException() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Password and confirm Password fields must be equal.");
        Mockito.when(customerService.registerCustomer(any(Customer.class))).thenThrow(new CommonException("Password and confirm Password fields must be equal."));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer))).andReturn();
        assertEquals(406,result.getResponse().getStatus());
        assertEquals(response.getBody(),result.getResponse().getContentAsString());
    }
    @Test
    public void forgotPassword_success() throws Exception {
        ResponseEntity<?> response = ResponseEntity.ok("password updated successfully");
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn(customer.getUserName());
        Mockito.when(customerService.changePassword(customer.getUserName(),"password")).thenReturn("password updated successfully");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/Kiran123/forgot")
                        .header("Authorization","Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("password")).andReturn();
        assertEquals(200,result.getResponse().getStatus());
        assertEquals(response.getBody(),result.getResponse().getContentAsString());
    }
    @Test
    public void forgotPassword_NotAuthorizedTest() throws Exception {
        ResponseEntity<?> response = ResponseEntity.ok("You are not Authorized to change the password");
                mockMvc.perform(MockMvcRequestBuilders.post("/Kiran123/forgot")
                .header("Authorization","Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("password"))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof CommonException))
                        .andExpect(jsonPath("$.errorMessage").value("You are not Authorized to change the password"));
    }
    @Test
    public void forgotPassword_CustomerNotFoundException() throws Exception {
        ResponseEntity<?> response = ResponseEntity.ok("Customer not found with username: Kiran123" );
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn(customer.getUserName());
        Mockito.when(customerService.changePassword(customer.getUserName(),"password")).thenThrow(new CustomerNotFoundException("Customer not found with username: Kiran123"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/Kiran123/forgot")
                .header("Authorization","Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("password")).andReturn();
        assertEquals(406,result.getResponse().getStatus());
        assertEquals(response.getBody(),result.getResponse().getContentAsString());
    }
    @Test
    public void forgotPassword_CommonException() throws Exception {
        ResponseEntity<?> response = ResponseEntity.ok("password cannot be null");
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn(customer.getUserName());
        Mockito.when(customerService.changePassword(customer.getUserName(),"password")).thenThrow(new CommonException("password cannot be null"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/Kiran123/forgot")
                .header("Authorization","Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("password")).andReturn();
        assertEquals(406,result.getResponse().getStatus());
        assertEquals(response.getBody(),result.getResponse().getContentAsString());
    }


}
