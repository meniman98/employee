package com.cts.employee.controller;

import com.cts.employee.model.Employee;
import com.cts.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepo repo;

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employee = repo.findById(id);
        if (employee.isPresent()) {
            return new ResponseEntity<Employee>(employee.get() ,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }
}
