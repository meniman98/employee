package com.cts.employee.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.time.LocalDate;

public record Employee(@Id
                       @GeneratedValue(strategy = GenerationType.AUTO)
                       Long id,
                       String name, LocalDate dateOfBirth, Department department) {
}



