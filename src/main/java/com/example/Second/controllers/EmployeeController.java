package com.example.Second.controllers;

import com.example.Second.dtos.employee.CreateEmployeeDto;
import com.example.Second.dtos.employee.ShowEmployeeDto;
import com.example.Second.dtos.employee.UpdateEmployeeDto;
import com.example.Second.services.EmployeeService;
import com.example.Second.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/employees")
    public List<ShowEmployeeDto> findAllEmployees() {

        emailService.send("test@gmail.com", emailService.buildEmail("Hi Sara"));

        return employeeService.findAllEmployees();
    }

    @PostMapping("/employees")
    public ShowEmployeeDto createNewEmployee(@RequestBody CreateEmployeeDto employeeRequest) {

        return employeeService.createNewEmployee(employeeRequest);
    }

    @PutMapping("/employees/{id}")
    public ShowEmployeeDto updateEmployee(@PathVariable("id") Long employeeId,
                                          @RequestBody UpdateEmployeeDto employeeRequest) {

        return employeeService.updateEmployee(employeeId, employeeRequest);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity deleteEmployee(@PathVariable("id") Long employeeId) {

        return employeeService.deleteEmployee(employeeId);
    }
}
