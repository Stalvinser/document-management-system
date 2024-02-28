package com.astarus.documentmanagementsystem.registration;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
