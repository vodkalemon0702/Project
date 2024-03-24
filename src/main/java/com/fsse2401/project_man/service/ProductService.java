package com.fsse2401.project_man.service;

import com.fsse2401.project_man.data.domainObject.product.response.ProductResponseData;
import com.fsse2401.project_man.data.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponseData> getAllProduct();

    ProductResponseData getProductByPid(Integer pid);

    ProductEntity getProductById(Integer pid);

    boolean isValidQuantity(ProductEntity entity, Integer quantity);

    boolean isValidQuantity(Integer pid, Integer quantity);

    boolean deductStock(Integer pid, Integer quantity);
}
