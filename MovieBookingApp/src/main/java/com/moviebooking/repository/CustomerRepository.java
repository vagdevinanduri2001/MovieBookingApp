package com.moviebooking.repository;

import com.moviebooking.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,Integer> {

    boolean existsByEmailId(String emailId);
    boolean existsByLoginId(int loginId);

    Optional<Customer> findByUserName(String userName);

}
