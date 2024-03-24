package com.fsse2401.project_man.repository;

import com.fsse2401.project_man.data.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<TransactionEntity,Integer> {
    Optional<TransactionEntity> findByUserEntityFireBaseUidAndTid(String uid,Integer tid);
}
