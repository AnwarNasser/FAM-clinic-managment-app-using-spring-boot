package com.example.Second.services;

import com.example.Second.dtos.patient_reservation.CreatePatientReservationDto;
import com.example.Second.dtos.patient_reservation.ShowPatientReservationDto;
import com.example.Second.dtos.patient_reservation.UpdatePatientReservationDto;
import com.example.Second.entities.Employee;
import com.example.Second.entities.PatientReservation;
import com.example.Second.exception.RecordExitException;
import com.example.Second.exception.ResourceNotFoundException;
import com.example.Second.repositories.EmployeeRepository;
import com.example.Second.repositories.PatientReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientReservationService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientReservationRepository patientReservationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<ShowPatientReservationDto> findAllPatientsReservations() {

        List<PatientReservation> sortedPatientsReservationsList = patientReservationRepository.findAll()
                .stream().sorted(
                        (o1, o2)->o2.getCreatedAt().compareTo(o1.getCreatedAt())
                ).collect(Collectors.toList());

        return sortedPatientsReservationsList.stream().map(
                obj -> modelMapper.map(obj, ShowPatientReservationDto.class)
        ).collect(Collectors.toList());
    }

    public ShowPatientReservationDto createNewPatientReservation(CreatePatientReservationDto patientReservationRequest) {

        if (patientReservationRepository.findByFullName(patientReservationRequest.getFullName()) !=null){
            throw new RecordExitException("aother recored exit with same name");
        }
        Long employeeId = patientReservationRequest.getEmployeeId();

        if (employeeRepository.findById(employeeId).isEmpty()) {

            throw new ResourceNotFoundException("employee with id: [" + employeeId + "] is not found.");
        }

        Employee employeeData = employeeRepository.findById(employeeId).get();

        PatientReservation patientReservationData = new PatientReservation();

        patientReservationData.setFullName(patientReservationRequest.getFullName());
        patientReservationData.setGender(patientReservationRequest.getGender());
        patientReservationData.setEmail(patientReservationRequest.getEmail());
        patientReservationData.setPhone(patientReservationRequest.getPhone());
        patientReservationData.setFeeling(patientReservationRequest.getFeeling());
        patientReservationData.setIsActive(true);
        patientReservationData.setEmployee(employeeData);

        return modelMapper.map(
                patientReservationRepository.save(patientReservationData), ShowPatientReservationDto.class
        );
    }

    public ShowPatientReservationDto updatePatientReservation(Long patientReservationId,
                                                              UpdatePatientReservationDto patientReservationRequest) {

        if (patientReservationRepository.findById(patientReservationId).isEmpty()) {

            throw new ResourceNotFoundException(
                    "patient reservationId with id: [" + patientReservationId + "] is not found."
            );
        }

        PatientReservation patientReservationData = patientReservationRepository.findById(patientReservationId).get();

        Long employeeId = patientReservationRequest.getEmployeeId();

        if (employeeRepository.findById(employeeId).isEmpty()) {

            throw new ResourceNotFoundException("employee with id: [" + employeeId + "] is not found.");
        }

        Employee employeeData = employeeRepository.findById(employeeId).get();

        patientReservationData.setFullName(patientReservationRequest.getFullName());
        patientReservationData.setGender(patientReservationRequest.getGender());
        patientReservationData.setEmail(patientReservationRequest.getEmail());
        patientReservationData.setPhone(patientReservationRequest.getPhone());
        patientReservationData.setFeeling(patientReservationRequest.getFeeling());
        patientReservationData.setEmployee(employeeData);

        return modelMapper.map(
                patientReservationRepository.save(patientReservationData), ShowPatientReservationDto.class
        );
    }

    public ResponseEntity deletePatientReservation(Long patientReservationId) {

        if (patientReservationRepository.findById(patientReservationId).isEmpty()) {

            throw new ResourceNotFoundException(
                    "patient reservationId with id: [" + patientReservationId + "] is not found."
            );
        }

        PatientReservation patientReservationData = patientReservationRepository.findById(patientReservationId).get();

        patientReservationData.setIsActive(false);

        patientReservationRepository.save(patientReservationData);

        return ResponseEntity.ok().build();
    }

}
