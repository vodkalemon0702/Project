package com.fsse2401.project_man.service;

import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.entity.TransactionEntity;
import com.fsse2401.project_man.data.entity.TransactionProductEntity;

import java.util.List;

public interface TransactionProductService {

    TransactionProductEntity createTransactionProduct(TransactionEntity transactionEntity, CartItemEntity cartItemEntity);

    List<TransactionProductEntity> getEntityListByTransaction(TransactionEntity transactionEntity);
}
