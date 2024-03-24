package com.fsse2401.project_man.exception.transaction;

public class TransactionFailedException extends RuntimeException{
    public TransactionFailedException(Integer tid){
        super(String.format("transaction failed, tid: %s",tid));
    }
}
