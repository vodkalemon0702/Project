package com.fsse2401.project_man.data.domainObject.CartItem.response;

import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartItemResponseData {
    private Integer pid;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer cartQuantity;
    private Integer stock;

    public CartItemResponseData(CartItemEntity entity) {
        this.pid = entity.getProductEntity().getPid();
        this.name = entity.getProductEntity().getName();
        this.description = entity.getProductEntity().getDescription();
        this.imageUrl = entity.getProductEntity().getImageUrl();
        this.cartQuantity = entity.getQuantity();
        this.stock = entity.getProductEntity().getStock();
        this.price = entity.getProductEntity().getPrice();
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

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
