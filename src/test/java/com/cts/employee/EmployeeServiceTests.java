package com.cts.employee;

import com.cts.employee.model.Employee;
import com.cts.employee.repo.EmployeeRepo;
import com.cts.employee.service.EmployeeService;
import com.cts.employee.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.cts.employee.TestUtils.BIRTHDAY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    EmployeeRepo repo;

    @InjectMocks
    EmployeeServiceImpl service;

    static Employee employee = new Employee("Huncho", BIRTHDAY, "Engineering");
    static Employee employee2 = new Employee("Donald-Trump", BIRTHDAY, "Engineering");
    static Employee employee3 = new Employee("Benjamin-Franklin", BIRTHDAY, "Engineering");
    static List<Employee> employeeList = List.of(employee, employee2, employee3);

    @Test
    void getEmployeeSuccess() {
        when(repo.findById(1L)).thenReturn(Optional.of(employee));

        Employee retrievedEmployee = service.getEmployee(1L);

        assertThat(retrievedEmployee, equalToObject(employee));
        assertThat(retrievedEmployee.getName(), is("Huncho"));
    }

    @Test
    void getEmployeeWithFakeId() {
        when(repo.findById(1L)).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        assertThrows(ResponseStatusException.class, () -> service.getEmployee(1L));
    }

    @Test
    void getAllEmployeesSuccess() {
        when(repo.findAll()).thenReturn(employeeList);
        List<Employee> retrievedEmployeesList = service.getAllEmployees();
        assertThat(retrievedEmployeesList, hasSize(3));
        assertSame(retrievedEmployeesList, employeeList);
    }

    @Test
    void getAllEmployeesReturnsEmptyListWhenGivenEmptyList() {
        List<Employee> emptyList = List.of();
        when(repo.findAll()).thenReturn(emptyList);
        List<Employee> retrievedEmployeeList = service.getAllEmployees();
        assertThat(retrievedEmployeeList, is(empty()));
    }

    @Test
    void getAllEmployeesReturnsNullWhenGivenNull() {
        List<Employee> nullList = null;
        when(repo.findAll()).thenReturn(null);
        List<Employee> retrievedEmployeeList = service.getAllEmployees();
        assertNull(retrievedEmployeeList);
    }
}
