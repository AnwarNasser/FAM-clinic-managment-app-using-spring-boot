package com.example.Second.services;

import com.example.Second.dtos.employee.CreateEmployeeDto;
import com.example.Second.dtos.employee.ShowEmployeeDto;
import com.example.Second.dtos.employee.UpdateEmployeeDto;
import com.example.Second.entities.Employee;
import com.example.Second.exception.RecordExitException;
import com.example.Second.exception.ResourceNotFoundException;
import com.example.Second.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<ShowEmployeeDto> findAllEmployees() {

        List<Employee> sortedEmployeesList = employeeRepository.findAll().stream().sorted(
                (o1, o2)->o2.getCreatedAt().compareTo(o1.getCreatedAt())
        ).collect(Collectors.toList());

        return sortedEmployeesList.stream().map(
                obj -> modelMapper.map(obj, ShowEmployeeDto.class)
        ).collect(Collectors.toList());
    }

    public ShowEmployeeDto createNewEmployee(CreateEmployeeDto employeeRequest) {

        if(employeeRepository.findByFullName(employeeRequest.getFullName()) !=null){
            throw new RecordExitException("another recored exit with same name");
        }
        Employee employeeData = new Employee();

        employeeData.setFullName(employeeRequest.getFullName());

        employeeData.setGender(employeeRequest.getGender());

        employeeData.setUserName(employeeRequest.getUsername());

        employeeData.setPassword(employeeRequest.getPassword());

        employeeData.setPhone(employeeRequest.getPhone());

        employeeData.setRole(employeeRequest.getRole());

        return modelMapper.map(
                employeeRepository.save(employeeData), ShowEmployeeDto.class
        );
    }

    public ShowEmployeeDto updateEmployee(Long employeeId, UpdateEmployeeDto employeeRequest) {

        if (employeeRepository.findById(employeeId).isEmpty()) {

            throw new ResourceNotFoundException("employee with id: [" + employeeId + "] is not found.");
        }

        Employee employeeData = employeeRepository.findById(employeeId).get();

        employeeData.setFullName(employeeRequest.getFullName());

        employeeData.setGender(employeeRequest.getGender());

        employeeData.setUserName(employeeRequest.getUserName());

        employeeData.setPassword(employeeRequest.getPassword());

        employeeData.setPhone(employeeRequest.getPhone());

        employeeData.setRole(employeeRequest.getRole());

        return modelMapper.map(
                employeeRepository.save(employeeData), ShowEmployeeDto.class
        );
    }

    public ResponseEntity deleteEmployee(Long employeeId) {

        if (employeeRepository.findById(employeeId).isEmpty()) {

            throw new ResourceNotFoundException("employee with id: [" + employeeId + "] is not found.");
        }

        Employee employeeData = employeeRepository.findById(employeeId).get();

        employeeRepository.delete(employeeData);

        return ResponseEntity.ok().build();
    }
}
