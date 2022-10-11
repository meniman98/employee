package com.cts.employee.service;

import com.cts.employee.model.Employee;
import com.cts.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo repo;

    public static final String EMPLOYEE_NOT_FOUND = "Employee with ID {0} was not found";

    @Override
    public Employee getEmployee(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                MessageFormat.format(EMPLOYEE_NOT_FOUND, id)));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public Employee editEmployee(Long id, Employee newEmployee) {
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

    @Override
    public void deleteEmployee(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    MessageFormat.format(EMPLOYEE_NOT_FOUND, id));
        }
    }
}
