package com.cts.employee.controller;

import com.cts.employee.model.Employee;
import com.cts.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.cts.employee.Utils.EMPLOYEE_END_POINT;

@RestController
@RequestMapping(EMPLOYEE_END_POINT)
public class EmployeeController  {

    @Autowired
    EmployeeService service;


    @GetMapping("/{name}")
    public List<Employee> findByName(@PathVariable("name") String name) {
        return service.findByName(name);
    }

    @GetMapping("/{department}")
    public List<Employee> findByDepartment(@PathVariable("department") String department) {
        return service.findByDepartment(department);
    }

    @GetMapping("/{dob}")
    public List<Employee> findByDateOfBirth(@PathVariable("dob") LocalDate dateOfBirth) {
        return service.findByDateOfBirth(dateOfBirth);
    }

    @GetMapping("/{id}")
//    /employees?name=bob
    public Employee getEmployee(@PathVariable("id") Long id) {
        return service.getEmployee(id);
    }


    @GetMapping("/")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
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
