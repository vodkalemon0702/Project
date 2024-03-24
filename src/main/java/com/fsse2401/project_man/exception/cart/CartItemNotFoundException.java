package com.fsse2401.project_man.exception.cart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException(Integer uid){
        super(String.format("cart item not found,user id:%s",uid));
    }
}
