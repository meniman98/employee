package com.cts.employee;

import com.cts.employee.model.Employee;
import com.cts.employee.model.EmployeePage;
import com.cts.employee.model.EmployeeSearchCriteria;
import com.cts.employee.repo.EmployeeCriteriaRepo;
import com.cts.employee.repo.EmployeeRepo;
import com.cts.employee.service.EmployeeService;
import com.cts.employee.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.cts.employee.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    EmployeeRepo repo;

    @Mock
    EmployeeCriteriaRepo criteriaRepo;

    @InjectMocks
    EmployeeServiceImpl service;

    static Employee employee = new Employee("Huncho", BIRTHDAY, "Engineering");
    static Employee employee2 = new Employee("Donald-Trump", BIRTHDAY, "Engineering");
    static Employee employee3 = new Employee("Benjamin-Franklin", BIRTHDAY, "Engineering");
    static List<Employee> employeeList = List.of(employee, employee2, employee3);
    static final EmployeePage EMPLOYEE_PAGE = new EmployeePage();
    static final EmployeeSearchCriteria EMPLOYEE_SEARCH_CRITERIA = new EmployeeSearchCriteria();

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
        Page<Employee> retrievedEmployeesList = service.getAllEmployees(
                EMPLOYEE_PAGE,
                EMPLOYEE_SEARCH_CRITERIA);
//        TODO find a way to assert the size of the retrieved list
//        assertThat(retrievedEmployeesList, hasSize());
        assertSame(retrievedEmployeesList, employeeList);
    }

    @Test
    void getAllEmployeesReturnsEmptyListWhenGivenEmptyList() {
//        TODO find a way
//        Page<Employee> emptyList =
//        when(criteriaRepo.findAllWithFilters(any(), any()))
//                .thenReturn(emptyList);
        Page<Employee> retrievedEmployeeList = service.getAllEmployees(EMPLOYEE_PAGE, EMPLOYEE_SEARCH_CRITERIA);
//        TODO find a way to assert the list is empty
//        assertThat(retrievedEmployeeList, is(empty()));
    }

    @Test
    void getAllEmployeesReturnsNullWhenGivenNull() {
        when(repo.findAll()).thenReturn(null);
        Page<Employee> retrievedEmployeeList = service.getAllEmployees(EMPLOYEE_PAGE, EMPLOYEE_SEARCH_CRITERIA);
        assertNull(retrievedEmployeeList);
    }

    @Test
    void editEmployeeSuccess() {
//        given
        Employee newEmployee = new Employee("Barack-Obama", BIRTHDAY, "politics");
        Employee oldEmployee = new Employee("George-Washington", BIRTHDAY, "politics");
//        when
        when(repo.findById(100L)).thenReturn(Optional.of(oldEmployee));
        when(repo.save(newEmployee)).thenReturn(newEmployee);
//        then
        Employee modifiedEmployee = service.editEmployee(100L, newEmployee);
        assertSame(newEmployee, modifiedEmployee);

    }

    @Test
    void givenWrongId_whenPutRequest_returnNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () ->
                service.editEmployee(1L, new Employee()));

    }

    @Test
    void givenEmployeeObject_whenPostRequest_returnSuccess() {
        when(repo.save(employee)).thenReturn(employee);
        Employee retrievedEmployee = service.createEmployee(employee);
        verify(repo, atLeastOnce()).save(employee);
        assertSame(retrievedEmployee, employee);
    }


//    TODO: write a delete test
    @Test
    void givenCorrectId_whenDeleteRequest_returnSuccess() {

    }
}
