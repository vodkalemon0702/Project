package com.fsse2401.project_man.exception.cart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidQuantityException extends RuntimeException{
    public InvalidQuantityException(Integer quantity){
        super(String.format("Invalid quantity- Quantity:%s",quantity));
    }
}
