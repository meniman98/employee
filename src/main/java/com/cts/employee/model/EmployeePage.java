package com.cts.employee.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class EmployeePage {
//    page number, normally starts at 0
    private int pageNumber = 0;
//    the amount of pages, by default it's 10
    private int pageSize = 10;
//    the sorting direction goes ascending
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "name";
//    TODO perhaps add more sorting variables like department


}
