package com.cts.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Data
@Table(name = "employees")
// TODO add validation
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String department;

    public Employee() {}

    public Employee(Long id, String name, LocalDate dateOfBirth, String department) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
    }


}



