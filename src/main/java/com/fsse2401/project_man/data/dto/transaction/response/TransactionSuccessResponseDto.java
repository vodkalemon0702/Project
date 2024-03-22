package com.fsse2401.project_man.data.dto.transaction.response;

public class TransactionSuccessResponseDto {
    private String status;

    public TransactionSuccessResponseDto() {
        setStatus("Success");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
