package com.example.Second.dtos.doctor;

import com.example.Second.enums.DoctorType;
import com.example.Second.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ShowDoctorDto {
    private Long id;

    private String fullName;

    private Gender gender;

    private String phone;

    private DoctorType type;

    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date updatedAt;
}
