package com.example.Second.entities;

import com.example.Second.enums.DoctorType;
import com.example.Second.enums.Gender;
import com.example.Second.models.AuditModel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "doctors")
public class Doctor extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private DoctorType type;
}
