package com.cts.employee.repo;

import com.cts.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

//   TODO: get employee by ID and department

}
