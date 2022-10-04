package com.cts.employee.controller;

import com.cts.employee.model.Employee;
import com.cts.employee.service.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController implements com.cts.employee.service.EmployeeService {

    @Autowired
    EmployeeServiceImpl service;

    @Override
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") Long id) {
        return service.getEmployee(id);
    }

    @Override
    @GetMapping("/")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }


    @Override
    @PostMapping("/")
    public Employee createEmployee(@RequestBody @Valid Employee employee) {
        return service.createEmployee(employee);
    }

    @Override
    @PutMapping("/{id}")
    public Employee editEmployee(@PathVariable Long id, @RequestBody @Valid Employee newEmployee) {
        return service.editEmployee(id, newEmployee);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}
