package com.cts.employee;

import com.cts.employee.model.Employee;
import com.cts.employee.repo.EmployeeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.cts.employee.Utils.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = EmployeeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
// TODO: add integrated tests and comments at the end
class EmployeeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepo repo;

    @Test
    void contextLoads() {
    }

    private String toJson(final Object object) {
        try {

            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            return mapper.writeValueAsString(object);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }


    @Test
    void getAllEmployeesAndReturn200Status() throws Exception {
        mockMvc.perform(get(EMPLOYEE_END_POINT + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", isA(Iterable.class)))
                .andDo(print());
    }

    @Test
    void getEmployeeReturnsEmployeeInstanceAnd200Status() throws Exception {
        mockMvc.perform(get(EMPLOYEE_END_POINT + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createEmployeeAndReturn200Status() throws Exception {
        Employee employee = new Employee("Bob", BIRTHDAY, "engineering");

        mockMvc.perform(post(EMPLOYEE_END_POINT + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(employee))).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void editEmployeeAndReturn200Status() throws Exception {
        Employee newEmployee = new Employee("Huncho Jack", BIRTHDAY, "engineering");
        mockMvc.perform(put(EMPLOYEE_END_POINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(newEmployee)))
                .andExpect(jsonPath("$.name", is("Huncho Jack")));
    }

    @Test
    void deleteEmployeeAndReturn200Status() throws Exception {
        mockMvc.perform(delete(EMPLOYEE_END_POINT + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void deleteWithNonExistingIdReturn404Status() throws Exception {
        mockMvc.perform(delete(EMPLOYEE_END_POINT + "/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    //    Validation tests
    @Test
    void createInvalidEmployeeThenReturn400() throws Exception {
//        Date of birth is in the past
        Employee employee = new Employee("Bob", LocalDate.now(), "Engineering");
//        Name is too short
        Employee employee1 = new Employee("B", BIRTHDAY, "Engineering");
//        Department can't be blank
        Employee employee2 = new Employee("Bob", BIRTHDAY, "");
//        Name can't be empty
        Employee employee3 = new Employee("", BIRTHDAY, "Engineering");

        List<Employee> employeeList = List.of(employee, employee1, employee2);

        for (Employee employeeItem : employeeList) {
            mockMvc.perform(post(EMPLOYEE_END_POINT + "/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(employeeItem))).andExpect(status().is4xxClientError())
                    .andDo(print());
        }
    }

    @Test
    void assertThatIdIsAutomaticallyGenerated() throws Exception {
//        employee without ID
        Employee employee = new Employee("Bob", BIRTHDAY, "Engineering");
        repo.save(employee);
        mockMvc.perform(get(EMPLOYEE_END_POINT + "/" + employee.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andDo(print());
    }

}
