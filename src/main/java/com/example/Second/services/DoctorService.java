package com.example.Second.services;

import com.example.Second.dtos.doctor.CreateDoctorDto;
import com.example.Second.dtos.doctor.ShowDoctorDto;
import com.example.Second.dtos.doctor.UpdateDoctorDto;
import com.example.Second.entities.Doctor;
import com.example.Second.exception.RecordExitException;
import com.example.Second.exception.ResourceNotFoundException;
import com.example.Second.repositories.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ShowDoctorDto> findAllDoctors(){
        List<Doctor> sortedDoctorsList = doctorRepository.findAll().stream().sorted(
                (o1, o2)->o2.getCreatedAt().compareTo(o1.getCreatedAt())
        ).collect(Collectors.toList());

        return sortedDoctorsList.stream().map(
                obj -> modelMapper.map(obj, ShowDoctorDto.class)
        ).collect(Collectors.toList());
    }

    public ShowDoctorDto createNewDoctor(CreateDoctorDto doctorRequest){
        if(doctorRepository.findByFullName(doctorRequest.getFullName()) !=null)
        {
            throw new RecordExitException("another record exit with same name");
        }
        Doctor doctorData = new Doctor();
        doctorData.setFullName(doctorRequest.getFullName());
        doctorData.setGender(doctorRequest.getGender());
        doctorData.setUserName(doctorRequest.getUsername());
        doctorData.setPassword(doctorRequest.getPassword());
        doctorData.setPhone(doctorRequest.getPhone());
        doctorData.setType(doctorRequest.getType());

        return modelMapper.map(
                doctorRepository.save(doctorData),ShowDoctorDto.class
        );
    }

    public ShowDoctorDto updateDoctor(Long doctorId, UpdateDoctorDto doctorRequest){
        if (doctorRepository.findById(doctorId).isEmpty()){
            throw new ResourceNotFoundException("doctor with id: [" + doctorId + "] is not found.");
        }
        Doctor doctorData = doctorRepository.findById(doctorId).get();
        doctorData.setFullName(doctorRequest.getFullName());
        doctorData.setGender(doctorRequest.getGender());
        doctorData.setUserName(doctorRequest.getUsername());
        doctorData.setPassword(doctorRequest.getPassword());
        doctorData.setPhone(doctorRequest.getPhone());
        doctorData.setType(doctorRequest.getType());

        return modelMapper.map(
                doctorRepository.save(doctorData),ShowDoctorDto.class
        );
    }

    public ResponseEntity deleteDoctor(Long doctorId){
        if (doctorRepository.findById(doctorId).isEmpty()){
            throw new ResourceNotFoundException("doctor with id: [" + doctorId + "] is not found.");
        }
        Doctor doctorData = doctorRepository.findById(doctorId).get();
        doctorRepository.delete(doctorData);
        return ResponseEntity.ok().build();
    }

}
