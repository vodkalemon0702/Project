package com.fsse2401.project_man.data.dto.transactionProduct;

import com.fsse2401.project_man.data.domainObject.transcationProduct.response.TransactionProductResponseData;
import com.fsse2401.project_man.data.dto.product.response.ProductResponseDto;

import java.math.BigDecimal;

public class TransactionProductResponseDto {
    private Integer tpid;
    private ProductResponseDto product;
    private Integer quantity;
    private BigDecimal subTotal;
    public TransactionProductResponseDto(TransactionProductResponseData data) {
        this.tpid = data.getTpid();
        this.product = new ProductResponseDto(data);
        this.quantity = data.getQuantity();
        this.subTotal = data.getPrice().multiply(new BigDecimal(data.getQuantity()));
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public ProductResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDto product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}
