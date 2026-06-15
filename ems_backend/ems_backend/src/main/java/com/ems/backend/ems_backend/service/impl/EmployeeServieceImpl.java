package com.ems.backend.ems_backend.service.impl;

import com.ems.backend.ems_backend.dto.EmployeeDto;
import com.ems.backend.ems_backend.entity.Employee;
import com.ems.backend.ems_backend.mapper.Employee_mapper;
import com.ems.backend.ems_backend.repository.Employee_repository;
import com.ems.backend.ems_backend.service.EmployeeServiece;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor


public class EmployeeServieceImpl implements EmployeeServiece {

    private Employee_repository employee_repository;
    @Override
    public EmployeeDto creatEmployee(EmployeeDto employeeDto) {

        Employee employee = Employee_mapper.mapToEmployee(employeeDto);
        Employee savedEmployee;
        savedEmployee = employee_repository.save(employee);
        return Employee_mapper.mapToEmployeeDto(savedEmployee);
    }
}
