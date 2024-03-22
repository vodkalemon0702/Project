package com.fsse2401.project_man.data.entity;

import com.fsse2401.project_man.data.entity.status.TransactionStatus;
import com.fsse2401.project_man.data.user.entity.UserEntity;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;
    @ManyToOne
    @JoinColumn(name = "buyer_uid", nullable = false)
    private UserEntity userEntity;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Column(nullable = false)
    private BigDecimal total;


    public TransactionEntity() {
    }

    public TransactionEntity(UserEntity user) {
        this.userEntity = user;
        this.dateTime = LocalDateTime.now();
        this.status = TransactionStatus.PREPARE;
        this.total = BigDecimal.ZERO;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

}
