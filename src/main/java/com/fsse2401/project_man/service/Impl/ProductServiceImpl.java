package com.fsse2401.project_man.service.Impl;

import com.fsse2401.project_man.data.domainObject.product.response.ProductResponseData;
import com.fsse2401.project_man.data.entity.ProductEntity;
import com.fsse2401.project_man.exception.RequestDataMissingException;
import com.fsse2401.project_man.exception.product.ProductNotFoundException;
import com.fsse2401.project_man.repository.ProductRepository;
import com.fsse2401.project_man.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    List<ProductEntity> productEntityList = new ArrayList<>();
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseData> getAllProduct() {
        List<ProductResponseData> getAllProductResponseDataList = new ArrayList<>();
        for (ProductEntity productEntity : productRepository.findAll()) {
            ProductResponseData getAllProductResponseData = new ProductResponseData(productEntity);
            getAllProductResponseDataList.add(getAllProductResponseData);
        }
        if (getAllProductResponseDataList.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return getAllProductResponseDataList;
    }

    @Override
    public ProductResponseData getProductByPid(Integer pid) {
        if (pid == null) {
            throw new RequestDataMissingException();
        }
        ProductEntity productEntity = getProductById(pid);
        ProductResponseData productResponseData = new ProductResponseData(productEntity);
        return productResponseData;
    }

    @Override
    public ProductEntity getProductById(Integer pid) {
        Optional<ProductEntity> productEntity = productRepository.findByPid(pid);
        return productEntity.orElseThrow(ProductNotFoundException::new);
    }

    public boolean isValidQuantity(ProductEntity entity, Integer quantity) {
        if (quantity < 1) {
            return false;
        } else if (quantity > entity.getStock()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidQuantity(Integer pid, Integer quantity) {
        ProductEntity productEntity = getProductById(pid);
        if (quantity < 1) {
            return false;
        } else if (quantity > productEntity.getStock()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deductStock(Integer pid, Integer quantity) {
        ProductEntity productEntity = getProductById(pid);
        if (!isValidQuantity(productEntity, quantity)) {
            return false;
        }
        productEntity.setStock(productEntity.getStock() - quantity);
        productRepository.save(productEntity);
        return true;
    }
}
