package com.cts.employee.controller;

import com.cts.employee.model.Employee;
import com.cts.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepo repo;

    @GetMapping("/{id}")
    Employee getEmployeeById(@PathVariable("id") Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    Employee createEmployee(@RequestBody Employee employee) {
        return repo.save(employee);
    }

    @PutMapping("/{id}")
    Employee editEmployee(@PathVariable Long id, @RequestBody Employee newEmployee) {
        return repo.findById(id)
                .map(oldEmployee -> {
                    oldEmployee.setName(newEmployee.getName());
                    oldEmployee.setDateOfBirth(newEmployee.getDateOfBirth());
                    oldEmployee.setDepartment(newEmployee.getDepartment());
                    return repo.save(oldEmployee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repo.save(newEmployee);
                });
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
