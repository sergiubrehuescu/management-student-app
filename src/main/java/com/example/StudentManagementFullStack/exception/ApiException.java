package com.example.StudentManagementFullStack.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ApiException {
    private final String message;
    @JsonIgnore
    private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;


    public ApiException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }
}
