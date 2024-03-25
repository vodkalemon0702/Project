package com.fsse2401.project_man.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionFailedException extends RuntimeException{
    public TransactionFailedException(Integer tid){
        super(String.format("transaction failed, tid: %s",tid));
    }
}
