package com.cts.employee.service;

import com.cts.employee.model.Employee;
import com.cts.employee.model.EmployeePage;
import com.cts.employee.model.EmployeeSearchCriteria;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Employee getEmployee(Long id);

    Page<Employee> getAllEmployees(EmployeePage employeePage,
                                   EmployeeSearchCriteria searchCriteria);

    Employee createEmployee(Employee employee);

    Employee editEmployee(Long id,Employee newEmployee);

    void deleteEmployee(Long id);
}
