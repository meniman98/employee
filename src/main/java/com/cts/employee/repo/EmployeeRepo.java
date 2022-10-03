package com.cts.employee.repo;

import com.cts.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where upper(e.department) = upper(?1)")
    List<Employee> findByDepartment(@NonNull String department);

    List<Employee> findByNameIsIgnoreCase(@NonNull String name);


}
