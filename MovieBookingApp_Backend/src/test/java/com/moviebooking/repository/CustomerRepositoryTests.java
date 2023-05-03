package com.moviebooking.repository;

import com.moviebooking.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    private Customer customer;
    private Customer customer1;

    @BeforeEach
    void setUp(){
        customer = new Customer("userFirst", "userLast", "abc@gmail.com", 1, "user123", "pass123", "pass123", 1111111111, "user");
        mongoTemplate.save(customer);
        customer1 = new Customer("userFirst", "userLast", "abc1@gmail.com", 2, "user1234", "pass123", "pass123", 1111111111, "user");

    }

    @Test
    public void existByEmailId_True(){
        boolean result = customerRepository.existsByEmailId(customer.getEmailId());
        assertThat(result).isEqualTo(true);
    }
    @Test
    public void existByEmailId_False(){
        boolean result = customerRepository.existsByEmailId(customer1.getEmailId());
        assertThat(result).isEqualTo(false);
    }
    @Test
    public void existByLoginId_True(){
        boolean result = customerRepository.existsByLoginId(customer.getLoginId());
        assertThat(result).isEqualTo(true);
    }
    @Test
    public void existByLoginId_False(){
        boolean result = customerRepository.existsByLoginId(customer1.getLoginId());
        assertThat(result).isEqualTo(false);
    }
    @Test
    public void findByUserNameTest_ReturnsObject(){
        Optional<Customer> result = customerRepository.findByUserName(customer.getUserName());
        assertThat(result.get()).isEqualTo(customer);
    }

}
