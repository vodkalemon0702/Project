package com.fsse2401.project_man.service;

import com.fsse2401.project_man.data.domainObject.product.response.ProductResponseData;
import com.fsse2401.project_man.data.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductResponseData> getAllProduct();

    ProductResponseData getProductByPid(Integer pid);

    ProductEntity getProductById(Integer pid);
}
