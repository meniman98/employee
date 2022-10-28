package com.cts.employee.controller;

import com.cts.employee.model.Employee;
import com.cts.employee.model.EmployeePage;
import com.cts.employee.model.EmployeeSearchCriteria;
import com.cts.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import static com.cts.employee.Utils.EMPLOYEE_END_POINT;

@RestController
@RequestMapping(EMPLOYEE_END_POINT)
public class EmployeeController  {

    @Autowired
    EmployeeService service;


    @GetMapping("/{id}")
//    /employees?name=bob
    public Employee getEmployee(@PathVariable("id") Long id) {
        return service.getEmployee(id);
    }


    @GetMapping("/")
    public Page<Employee> getAllEmployees(EmployeePage employeePage,
                                          EmployeeSearchCriteria searchCriteria) {
        return service.getAllEmployees(employeePage, searchCriteria);
    }



    @PostMapping("/")
    public Employee createEmployee(@RequestBody @Valid Employee employee) {
        return service.createEmployee(employee);
    }


    @PutMapping("/{id}")
    public Employee editEmployee(@PathVariable Long id, @RequestBody @Valid Employee newEmployee) {
        return service.editEmployee(id, newEmployee);
    }


    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}
