package com.example.Second.dtos.doctor;

import com.example.Second.enums.DoctorType;
import com.example.Second.enums.Gender;
import lombok.Data;

@Data
public class CreateDoctorDto {

    private String fullName;

    private Gender gender;

    private String username;

    private String password;

    private String phone;

    private DoctorType type;
}
