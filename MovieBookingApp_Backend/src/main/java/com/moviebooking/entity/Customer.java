package com.moviebooking.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "customers")
public class Customer {

    @Transient
    public static final String SEQUENCE_NAME = "customer_id_sequence";

    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "firstName must be given")
    private String firstName;
    @NotNull(message = "lastName cannot be null")
    @NotBlank(message = "lastName must be given")
    private String lastName;
    @NotNull(message = "emailId cannot be null")
    @NotBlank(message = "emailId must be given")
    @Email(message = "check your email")
    private String emailId;

    @Id
    private int loginId;


    @NotNull(message = "userName cannot be null")
    @NotBlank(message = "userName must be given")
    private String userName;


    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password must be given")
    @Pattern(regexp = ".*[a-zA-Z]+.*",message = "password must have atleast an alphabetic character")
//    @Min(value = 7,message = "password must have minimum 7 characters")
    private String password;

    @NotNull(message = "confirmPassword cannot be null")
    @NotBlank(message = "confirmPassword must be given")
    private String confirmPassword;
    @NotNull(message = "contactNo cannot be null")
    private long contactNo;

    private String role="user";
}
