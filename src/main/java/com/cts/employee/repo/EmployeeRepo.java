package com.cts.employee.repo;

import com.cts.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// TODO: entity manager spring boot
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

//    TODO: validate the query parameter
    @Query("select e from Employee e where upper(e.department) = upper(?1)")
    List<Employee> findByDepartment(@NonNull String department);

    List<Employee> findByNameIsIgnoreCase(@NonNull String name);

    List<Employee> findByDateOfBirth(@NonNull LocalDate dateOfBirth);
}
