package com.cts.employee.exception;

import lombok.Data;

@Data
// TODO: global exception handling
public class CustomException {
    private String errorCode;
    private String errorMessage;
}
