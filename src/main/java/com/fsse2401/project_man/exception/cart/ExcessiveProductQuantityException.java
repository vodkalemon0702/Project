package com.fsse2401.project_man.exception.cart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExcessiveProductQuantityException extends RuntimeException{
    public ExcessiveProductQuantityException(Integer quantity,Integer stock){
        super(String.format("Quantity are equal stock- Quantity: %s , stock: %s",quantity,stock));
    }
}
