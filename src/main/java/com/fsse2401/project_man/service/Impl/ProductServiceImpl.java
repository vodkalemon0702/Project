package com.fsse2401.project_man.service.Impl;

import com.fsse2401.project_man.data.domainObject.product.response.ProductResponseData;
import com.fsse2401.project_man.data.entity.ProductEntity;
import com.fsse2401.project_man.exception.InvalidPidException;
import com.fsse2401.project_man.exception.product.ProductListEmptyException;
import com.fsse2401.project_man.exception.product.ProductNotFoundException;
import com.fsse2401.project_man.repository.ProductRepository;
import com.fsse2401.project_man.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseData> getAllProduct() {
        try {
            List<ProductResponseData> responseDataList = new ArrayList<>();
            for (ProductEntity productEntity : productRepository.findAll()) {
                responseDataList.add(new ProductResponseData(productEntity));
            }
            if (responseDataList.isEmpty()) {
                throw new ProductListEmptyException();
            }
            return responseDataList;
        } catch (ProductListEmptyException ex) {
            logger.warn("Get all product：" + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public ProductResponseData getProductByPid(Integer pid) {
        try {
            if (pid < 1){
                throw new InvalidPidException(pid);
            }
            ProductEntity productEntity = getProductById(pid);
            return new ProductResponseData(productEntity);
        }catch (InvalidPidException | ProductNotFoundException ex){
            logger.warn("Get product by pid: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public ProductEntity getProductById(Integer pid) {
        return productRepository.findByPid(pid).orElseThrow(
                () -> new ProductNotFoundException(pid));
    }

    @Override
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
