package com.example.Second.dtos.employee;

import com.example.Second.enums.Gender;
import com.example.Second.enums.Role;
import lombok.Data;

@Data
public class CreateEmployeeDto {
    private String fullName;

    private Gender gender;

    private String username;

    private String password;

    private String phone;

    private Role role;
}
