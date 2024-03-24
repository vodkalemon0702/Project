package com.fsse2401.project_man.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Integer pid){
        super(String.format("Product not found:Pid=%s",pid));
    }
}
