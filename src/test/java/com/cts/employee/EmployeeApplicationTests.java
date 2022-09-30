package com.cts.employee;

import com.cts.employee.model.Employee;
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

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.cts.employee.Utils.BASE_URL;
import static com.cts.employee.Utils.EMPLOYEE_END_POINT;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = EmployeeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class EmployeeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

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
                .andExpect(jsonPath("$", isA(Iterable.class)));
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
        Employee employee = new Employee(1L, "Bob", LocalDate.now(), "engineering");

        mockMvc.perform(post(EMPLOYEE_END_POINT + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(employee))).andExpect(status().isOk());
    }

    @Test
    void editEmployeeAndReturn200Status() throws Exception {
        Employee newEmployee = new Employee(1L, "Huncho Jack", LocalDate.now(), "engineering");
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


}
