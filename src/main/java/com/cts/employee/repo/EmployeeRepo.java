package com.cts.employee.repo;

import com.cts.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// TODO: entity manager spring boot
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Override
    Optional<Employee> findById(Long aLong);
}
