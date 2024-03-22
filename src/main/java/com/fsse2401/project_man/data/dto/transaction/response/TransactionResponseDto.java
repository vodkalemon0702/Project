package com.fsse2401.project_man.data.dto.transaction.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fsse2401.project_man.data.domainObject.transaction.response.TransactionResponseData;
import com.fsse2401.project_man.data.domainObject.transcationProduct.response.TransactionProductResponseData;
import com.fsse2401.project_man.data.dto.transactionProduct.TransactionProductResponseDto;
import com.fsse2401.project_man.data.entity.status.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@JsonPropertyOrder({"tid","buyer_uid","datetime","status","total","items"})
public class TransactionResponseDto {
    private Integer tid;
    @JsonProperty("buyer_uid")
    private Integer buyerUid;
    @JsonFormat(pattern = "yyyyMMdd'T'HH:mm:ss")
    private LocalDateTime datetime;
    private TransactionStatus status;
    private BigDecimal total;
    private List<TransactionProductResponseDto> items = new ArrayList<>();

    public TransactionResponseDto(TransactionResponseData data) {
        this.tid = data.getTid();
        this.buyerUid = data.getUser().getUid();
        this.datetime = data.getDateTime();
        this.status = data.getStatus();
        this.total = data.getTotal();
        setItems(data);
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(Integer buyerUid) {
        this.buyerUid = buyerUid;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<TransactionProductResponseDto> getItems() {
        return items;
    }

    public void setItems(List<TransactionProductResponseDto> items) {
        this.items = items;
    }
    public void setItems(TransactionResponseData data){
        for (TransactionProductResponseData transactionProductResponseData : data.getTransactionProductList()){
            this.items.add(new TransactionProductResponseDto(transactionProductResponseData));
        }
    }
}
