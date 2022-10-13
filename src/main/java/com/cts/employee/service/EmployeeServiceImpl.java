package com.cts.employee.service;

import com.cts.employee.model.Employee;
import com.cts.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo repo;

    public static final String EMPLOYEE_NOT_FOUND = "Employee with ID {0} was not found";

    @Override
    public List<Employee> findByDepartment(String department) {
        List<Employee> employeeList = repo.findByDepartment(department);
        if (employeeList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    MessageFormat.format(
                            "No employees were found in the {0} department", department));
        }
        return employeeList;
    }

    @Override
    public List<Employee> findByName(String name) {
        List<Employee> employeeList = repo.findByNameIsIgnoreCase(name);
        if (employeeList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    MessageFormat.format(
                            "No employees were found with name {0}", name));
        }
        return employeeList;
    }

    @Override
    public List<Employee> findByDateOfBirth(LocalDate dateOfBirth) {
        List<Employee> employeeList = repo.findByDateOfBirth(dateOfBirth);
        if (employeeList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    MessageFormat.format(
                            "No employees were found with a date of birth of {0}", dateOfBirth.toString()));
        }
        return employeeList;
    }

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
    public Employee editEmployee(Long id, Employee newDetails) {
        return repo.findById(id)
                .map(employee -> {
                    employee.setName(newDetails.getName());
                    employee.setDateOfBirth(newDetails.getDateOfBirth());
                    employee.setDepartment(newDetails.getDepartment());
                    return repo.save(employee);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        MessageFormat.format(EMPLOYEE_NOT_FOUND, id)));
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
