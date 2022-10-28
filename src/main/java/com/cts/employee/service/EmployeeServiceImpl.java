package com.cts.employee.service;

import com.cts.employee.model.Employee;
import com.cts.employee.model.EmployeePage;
import com.cts.employee.model.EmployeeSearchCriteria;
import com.cts.employee.repo.EmployeeCriteriaRepo;
import com.cts.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.text.MessageFormat;



@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo repo;

    @Autowired
    EmployeeCriteriaRepo criteriaRepo;

    public static final String EMPLOYEE_NOT_FOUND = "Employee with ID {0} was not found";

    @Override
    public Employee getEmployee(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                MessageFormat.format(EMPLOYEE_NOT_FOUND, id)));
    }

    @Override
    public Page<Employee> getAllEmployees(EmployeePage employeePage,
                                          EmployeeSearchCriteria searchCriteria) {
        return criteriaRepo.findAllWithFilters(employeePage, searchCriteria);
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
