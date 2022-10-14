package com.cts.employee;


import com.cts.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EmployeeApplication {

    @Autowired
    EmployeeRepo repo;

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }


}
