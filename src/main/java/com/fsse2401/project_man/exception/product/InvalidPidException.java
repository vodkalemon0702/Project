package com.fsse2401.project_man.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPidException extends RuntimeException{
    public InvalidPidException(Integer pid){
        super(String.format("Invalid Pid - Pid:%s",pid));
    }
}
