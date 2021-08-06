package com.example.Second.dtos.patient_reservation;

import com.example.Second.enums.Gender;
import lombok.Data;

@Data
public class CreatePatientReservationDto {

    private String fullName;

    private Gender gender;

    private String email;

    private String phone;

    private String feeling;

    private Long employeeId;
}
