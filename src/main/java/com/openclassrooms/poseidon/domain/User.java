package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    private String role;

}
