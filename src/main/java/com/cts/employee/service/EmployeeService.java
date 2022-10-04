package com.cts.employee.service;

import com.cts.employee.model.Employee;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface EmployeeService {
    Employee getEmployee(Long id);

    List<Employee> getAllEmployees();

    Employee createEmployee(Employee employee);

    Employee editEmployee(Long id,Employee newEmployee);

    void deleteEmployee(Long id);
}
