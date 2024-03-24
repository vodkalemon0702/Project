package com.fsse2401.project_man.service.Impl;

import com.fsse2401.project_man.data.domainObject.transaction.response.TransactionResponseData;
import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.entity.TransactionEntity;
import com.fsse2401.project_man.data.entity.TransactionProductEntity;
import com.fsse2401.project_man.data.entity.status.TransactionStatus;
import com.fsse2401.project_man.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_man.data.user.entity.UserEntity;
import com.fsse2401.project_man.exception.cart.CartItemNotFoundException;
import com.fsse2401.project_man.exception.cart.InvalidQuantityException;
import com.fsse2401.project_man.exception.transaction.TransactionFailedException;
import com.fsse2401.project_man.exception.transaction.TransactionNotFoundException;
import com.fsse2401.project_man.repository.TransactionRepository;
import com.fsse2401.project_man.service.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final UserService userService;
    private final CartItemService cartItemService;
    private final TransactionProductService transactionProductService;
    private final TransactionRepository transactionRepository;
    private final ProductService productService;

    @Autowired
    public TransactionServiceImpl(UserService userService,
                                  CartItemService cartItemService,
                                  TransactionProductService transactionProductService,
                                  TransactionRepository transactionRepository, ProductService productService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.transactionProductService = transactionProductService;
        this.transactionRepository = transactionRepository;
        this.productService = productService;
    }

    @Override

    public TransactionResponseData prepareTransaction(FirebaseUserData firebaseUserData) {
        try {
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            List<CartItemEntity> userCart = cartItemService.getAllCartItemEntity(userEntity);
            if (userCart.isEmpty()) {
                throw new CartItemNotFoundException(userEntity.getUid());
            }

            TransactionEntity transactionEntity = new TransactionEntity(userEntity);
            transactionEntity = transactionRepository.save(transactionEntity);
            List<TransactionProductEntity> transactionProductEntityList = new ArrayList<>();
            for (CartItemEntity cartItemEntity : userCart) {
                TransactionProductEntity transactionProductEntity =
                        transactionProductService.createTransactionProduct(transactionEntity, cartItemEntity);
                transactionProductEntityList.add(transactionProductEntity);
                transactionEntity.setTotal(
                        transactionEntity.getTotal().add(transactionProductEntity.getPrice().multiply(
                                        new BigDecimal(transactionProductEntity.getQuantity())
                                )
                        )
                );
            }
            transactionEntity = transactionRepository.save(transactionEntity);
            return new TransactionResponseData(transactionEntity, transactionProductEntityList);

        } catch (CartItemNotFoundException ex) {
            logger.warn("Prepare transaction: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public TransactionResponseData getTransactionByTid(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            TransactionEntity transactionEntity = getEntityByTidAndFirebaseUid(tid, firebaseUserData.getFirebaseUid());
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.getEntityListByTransaction(transactionEntity);
            if (transactionProductEntityList.isEmpty()){
                throw new TransactionNotFoundException(transactionEntity.getUserEntity().getUid());
            }
            return new TransactionResponseData(transactionEntity, transactionProductEntityList);
        } catch (TransactionNotFoundException ex) {
            logger.warn("Get transaction: " + ex.getMessage());
            throw ex;
        }
    }


    @Override
    public boolean payTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            TransactionEntity transactionEntity = getEntityByTidAndFirebaseUid(tid, firebaseUserData.getFirebaseUid());
            if (transactionEntity.getStatus() != TransactionStatus.PREPARE) {
                throw new TransactionNotFoundException(transactionEntity.getUserEntity().getUid());
            }
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.getEntityListByTransaction(transactionEntity);
            for (TransactionProductEntity transactionProductEntity : transactionProductEntityList) {
                if (!productService.isValidQuantity(transactionProductEntity.getPid(), transactionProductEntity.getQuantity())) {
                    throw new InvalidQuantityException(transactionProductEntity.getQuantity());
                }
            }
            for (TransactionProductEntity transactionProductEntity : transactionProductEntityList) {
                productService.deductStock(transactionProductEntity.getPid(), transactionProductEntity.getQuantity());
            }
            transactionEntity.setStatus(TransactionStatus.PROCESSING);
            transactionRepository.save(transactionEntity);
            return true;
        } catch (TransactionNotFoundException | InvalidQuantityException ex) {
            logger.warn("Pay transaction: " + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public TransactionResponseData finishTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {

            TransactionEntity transactionEntity = getEntityByTidAndFirebaseUid(tid, firebaseUserData.getFirebaseUid());
            if (transactionEntity.getStatus() != TransactionStatus.PROCESSING) {
                throw new TransactionFailedException(transactionEntity.getTid());
            }
            cartItemService.emptyUserCart(firebaseUserData.getFirebaseUid());
            transactionEntity.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transactionEntity);
            return new TransactionResponseData(transactionEntity, transactionProductService.getEntityListByTransaction(transactionEntity));
        }catch (TransactionFailedException | TransactionNotFoundException ex){
            logger.warn("Finish transaction: " + ex.getMessage());
            throw ex;
        }
    }
    public TransactionEntity getEntityByTidAndFirebaseUid(Integer tid, String firebaseUid) {
        return transactionRepository.findByUserEntityFireBaseUidAndTid(firebaseUid, tid).orElseThrow(
                () -> new TransactionNotFoundException(tid));
    }
}
