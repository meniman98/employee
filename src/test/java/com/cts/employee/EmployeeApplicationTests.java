package com.cts.employee;

import com.cts.employee.model.Employee;
import com.cts.employee.repo.EmployeeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.cts.employee.TestUtils.BIRTHDAY;
import static com.cts.employee.Utils.EMPLOYEE_END_POINT;
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


    private static String getJSONValidEmployee() {
        Employee validEmployee =
                new Employee("Bob", BIRTHDAY, "engineering");
        return toJson(validEmployee);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepo repo;

    @Test
    void contextLoads() {
    }

    private static String toJson(final Object object) {
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
        mockMvc.perform(post(EMPLOYEE_END_POINT + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSONValidEmployee())).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void editEmployeeAndReturn200Status() throws Exception {
        mockMvc.perform(put(EMPLOYEE_END_POINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSONValidEmployee()))
                .andExpect(jsonPath("$.name", is("Bob")))
                .andExpect(status().isOk())
                .andDo(print());
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
                .andExpect(status().isNotFound());
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

        List<Employee> employeeList = List.of(employee, employee1, employee2, employee3);

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

    @Test
    void givenName_whenGetRequest_returnEmployeeListWithMatchingNames() throws Exception {
//        given
        Employee hunchoTheFirst = new Employee(
                "Huncho", LocalDate.of(2000, 1, 1), "Science");
        Employee hunchoTheSecond = new Employee(
                "Huncho", LocalDate.of(2001, 1, 1), "Politics");
        Employee hunchoTheThird = new Employee(
                "Huncho", LocalDate.of(2002, 1, 1), "Engineering");
        List<Employee> employeeList = List.of(hunchoTheFirst, hunchoTheSecond, hunchoTheThird);
        for (Employee employee : employeeList) {
            repo.save(employee);
        }
//        when
//        TODO: check that each name is "Huncho"
        mockMvc.perform(get(EMPLOYEE_END_POINT + "/name=Huncho")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(Iterable.class)))
                .andDo(print());
    }

    @Test
    void givenDepartment_whenGetRequest_returnEmployeesWithMatchingDepartment() throws Exception {
        //        given
        Employee employee1 = new Employee(
                "Huncho", LocalDate.of(2000, 1, 1), "Engineering");
        Employee employee2 = new Employee(
                "Joe-Biden", LocalDate.of(2001, 1, 1), "Engineering");
        Employee employee3 = new Employee(
                "Hilary-Clinton", LocalDate.of(2002, 1, 1), "Engineering");
        List<Employee> employeeList = List.of(employee1, employee2, employee3);
        for (Employee employee : employeeList) {
            repo.save(employee);
        }
//        when
//        TODO: check that each department is "Engineering"
        mockMvc.perform(get(EMPLOYEE_END_POINT + "/department=engineering")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(Iterable.class)))
                .andDo(print());
    }

    @Test
    void givenDateOfBirth_whenGetRequest_returnEmployeesWithMatchingDateOfBirth() throws Exception {
        mockMvc.perform(get(EMPLOYEE_END_POINT + "/dob=1998-05-08")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                TODO: check that each birthday is the same
                .andExpect(jsonPath("$", isA(Iterable.class)))
                .andDo(print());
    }

}
