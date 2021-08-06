package com.example.Second.controllers;

import com.example.Second.dtos.doctor.CreateDoctorDto;
import com.example.Second.dtos.doctor.ShowDoctorDto;
import com.example.Second.dtos.doctor.UpdateDoctorDto;
import com.example.Second.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @GetMapping("/doctors")
    public List<ShowDoctorDto> findAllDoctors(){
        return doctorService.findAllDoctors();
    }

    @PostMapping("/doctors")
    public ShowDoctorDto createNewDoctor(@RequestBody CreateDoctorDto doctorRequest){
        return doctorService.createNewDoctor(doctorRequest);
    }

    @PutMapping("/doctors/{id}")
    public ShowDoctorDto updateDoctor(@PathVariable("id") Long doctorId, @RequestBody UpdateDoctorDto doctorRequest){
        return doctorService.updateDoctor(doctorId,doctorRequest);
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity deleteDoctor(@PathVariable("id") Long doctorId){
        return doctorService.deleteDoctor(doctorId);
    }
}
