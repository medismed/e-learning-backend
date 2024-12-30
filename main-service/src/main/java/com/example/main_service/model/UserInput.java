package com.example.main_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInput {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String linkdin;
    private String role;
    private String image;
}
