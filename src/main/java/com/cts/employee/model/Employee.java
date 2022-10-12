package com.cts.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnoreProperties
    private Long id;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Department can't be empty")
    private String department;

    public Employee() {}

    public Employee(String name, LocalDate dateOfBirth, String department) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
    }


}



