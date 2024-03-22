package com.fsse2401.project_man.data.dto.cartItem.response;

import com.fsse2401.project_man.data.domainObject.cartItem.response.PutCartItemResponseData;

public class PutCartItemResponseDto {
    private String result;

    public PutCartItemResponseDto(PutCartItemResponseData data) {
        this.result = data.getResult();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
