package com.cts.employee.service;

import com.cts.employee.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findByDepartment(String department);

    List<Employee> findByName(String name);

    List<Employee> findByDateOfBirth(LocalDate dateOfBirth);

    Employee getEmployee(Long id);

    List<Employee> getAllEmployees();

    Employee createEmployee(Employee employee);

    Employee editEmployee(Long id,Employee newEmployee);

    void deleteEmployee(Long id);
}
