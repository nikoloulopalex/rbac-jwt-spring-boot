package com.roles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
 
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {
 
    private static final long serialVersionUID = 1L;
 
    public StudentNotFoundException() {
        super("Student does not exist");
    }
 
}