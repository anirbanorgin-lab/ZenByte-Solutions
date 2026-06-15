package com.ems.backend.ems_backend.controller;

import com.ems.backend.ems_backend.dto.EmployeeDto;
import com.ems.backend.ems_backend.service.EmployeeServiece;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employees")

public class EmployeeController {
    private EmployeeServiece employeeServiece;

    //add employee rest api
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployeee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeServiece.creatEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

}
