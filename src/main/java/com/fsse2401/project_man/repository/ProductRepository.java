package com.fsse2401.project_man.repository;

import com.fsse2401.project_man.data.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity,Integer> {
    Optional<ProductEntity> findByPid(Integer pid);
}
