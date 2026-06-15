package com.ems.backend.ems_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")


public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "First_Name")
    private String firstname;
    @Column(name = "Last_Name")
    private String lastname;
    @Column(name = "email_id", nullable = false, unique = true)
    private String email;
}
