package com.fsse2401.project_man.data.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction_product")
public class TransactionProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tpid;
    @ManyToOne
    @JoinColumn(name = "tid",nullable = false)
    private TransactionEntity transaction;
    @Column(nullable = false)
    private Integer pid;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer quantity;

    public TransactionProductEntity() {
    }

    public TransactionProductEntity(TransactionEntity transaction, CartItemEntity cartItemEntity) {
        this.transaction = transaction;
        this.pid = cartItemEntity.getProductEntity().getPid();
        this.name = cartItemEntity.getProductEntity().getName();
        this.description = cartItemEntity.getProductEntity().getDescription();
        this.imageUrl = cartItemEntity.getProductEntity().getImageUrl();
        this.price = cartItemEntity.getProductEntity().getPrice();
        this.stock = cartItemEntity.getProductEntity().getStock();
        this.quantity = cartItemEntity.getQuantity();
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionEntity transactionEntity) {
        this.transaction = transactionEntity;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
