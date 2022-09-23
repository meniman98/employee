package com.cts.employee.repo;

import com.cts.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Employee findById(long id);
}
