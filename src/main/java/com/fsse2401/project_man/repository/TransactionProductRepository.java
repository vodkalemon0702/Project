package com.fsse2401.project_man.repository;

import com.fsse2401.project_man.data.entity.TransactionEntity;
import com.fsse2401.project_man.data.entity.TransactionProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionProductRepository extends CrudRepository<TransactionProductEntity,Integer> {
    List<TransactionProductEntity> findAllByTransaction(TransactionEntity transaction);
}
