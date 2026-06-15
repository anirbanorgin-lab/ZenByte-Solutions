package com.ems.backend.ems_backend.repository;

import com.ems.backend.ems_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Employee_repository extends JpaRepository<Employee,Long> {

}
