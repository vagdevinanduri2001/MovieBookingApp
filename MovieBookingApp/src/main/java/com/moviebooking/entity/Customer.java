package com.moviebooking.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Indexed(unique = true)
    private String userName;
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password must be given")
    private String password;
    @NotNull(message = "confirmPassword cannot be null")
    @NotBlank(message = "confirmPassword must be given")
    private String confirmPassword;
    @NotNull(message = "contactNo cannot be null")
    private long contactNo;
    @NotNull(message = "role cannot be null")
    @NotBlank(message = "role must be given")
    private String role;
}
