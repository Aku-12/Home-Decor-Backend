package com.example.homedecor.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactPojo {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String message;
}
