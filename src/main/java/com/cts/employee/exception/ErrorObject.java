package com.cts.employee.exception;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ErrorObject {
    private int status;
    private String reason;
    private LocalTime timeStamp;

}
