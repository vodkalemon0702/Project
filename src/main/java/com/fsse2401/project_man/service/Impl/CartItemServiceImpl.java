package com.fsse2401.project_man.service.Impl;

import com.fsse2401.project_man.data.domainObject.cartItem.response.CartItemResponseData;
import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.entity.ProductEntity;
import com.fsse2401.project_man.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_man.data.user.entity.UserEntity;
import com.fsse2401.project_man.exception.cart.CartItemNotFoundException;
import com.fsse2401.project_man.exception.cart.InvalidQuantityException;
import com.fsse2401.project_man.exception.product.InvalidPidException;
import com.fsse2401.project_man.exception.product.ProductListEmptyException;
import com.fsse2401.project_man.exception.product.ProductNotFoundException;
import com.fsse2401.project_man.exception.product.ProductOutOfStockException;
import com.fsse2401.project_man.repository.CartItemRepository;
import com.fsse2401.project_man.service.CartItemService;
import com.fsse2401.project_man.service.ProductService;
import com.fsse2401.project_man.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);
    private final UserService userService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemServiceImpl(UserService userService,
                               ProductService productService,
                               CartItemRepository cartItemRepository) {
        this.userService = userService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public boolean putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity) {
        try {
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            ProductEntity productEntity = productService.getProductById(pid);
            if (!productService.isValidQuantity(productEntity, quantity)) {
                throw new InvalidQuantityException(quantity);
            }
            if (productEntity.getStock() < 1) {
                throw new ProductOutOfStockException(productEntity.getStock());
            }
            Optional<CartItemEntity> cartItemEntityOptional = getCartItemByUidAndPid(userEntity.getUid(), productEntity.getPid());
            if (cartItemEntityOptional.isPresent()) {
                if ((cartItemEntityOptional.get().getQuantity() + quantity) > productEntity.getStock()) {
                    throw new InvalidQuantityException(quantity);
                }
                cartItemEntityOptional.get().setQuantity(cartItemEntityOptional.get().getQuantity() + quantity);
                cartItemRepository.save(cartItemEntityOptional.get());
            } else {
                cartItemRepository.save(new CartItemEntity(userEntity, productEntity, quantity));
            }
            return true;
        } catch (InvalidQuantityException | ProductOutOfStockException | ProductNotFoundException ex) {
            logger.warn("Put cart item: " + ex.getMessage());
            throw ex;
        }
    }


    @Override
    public List<CartItemResponseData> getAllCartProductsByUserId(FirebaseUserData firebaseUserData) {
        try {
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            List<CartItemEntity> cartItemEntityList = cartItemRepository.findAllByUserEntity(userEntity);
            if (cartItemEntityList.isEmpty()) {
                throw new ProductListEmptyException();
            }
            List<CartItemResponseData> cartItemResponseDataList = new ArrayList<>();
            for (CartItemEntity cartItemEntity : cartItemEntityList) {
                CartItemResponseData cartItemResponseData = new CartItemResponseData(cartItemEntity);
                cartItemResponseDataList.add(cartItemResponseData);
            }
            return cartItemResponseDataList;
        } catch (ProductListEmptyException ex) {
            logger.warn("Get all cart product: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public CartItemResponseData updateCartQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity) {
        try {
            if (!productService.isValidQuantity(pid, quantity)) {
                throw new InvalidQuantityException(quantity);
            }
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findByUserEntity_UidAndProductEntity_Pid(userEntity.getUid(), pid);
            if (cartItemEntityOptional.isEmpty()) {
                throw new ProductNotFoundException(pid);
            }
            if (quantity == cartItemEntityOptional.get().getQuantity()) {
                throw new InvalidQuantityException(quantity);
            }
            cartItemEntityOptional.get().setQuantity(quantity);
            return new CartItemResponseData(cartItemRepository.save(cartItemEntityOptional.get()));
        } catch (InvalidQuantityException | ProductNotFoundException ex) {
            logger.info("Update quantity:Quantity invalid!");
            throw ex;
        }
    }

    @Override
    public boolean deleteCartItem(FirebaseUserData firebaseUserData, Integer pid) {
        try {
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            if (pid < 1) {
                throw new InvalidPidException(pid);
            }
            Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findByUserEntity_UidAndProductEntity_Pid(userEntity.getUid(), pid);
            if (cartItemEntityOptional.isEmpty()) {
                throw new ProductNotFoundException(pid);
            }
            cartItemRepository.delete(cartItemEntityOptional.get());
            return true;
        } catch (InvalidPidException | ProductNotFoundException ex) {
            logger.warn("Delete cart item: " + ex.getMessage());
            throw ex;
        }
    }

    public CartItemEntity getCartItemEntityByUidAndPid(Integer uid, Integer pid) {
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findByUserEntity_UidAndProductEntity_Pid(uid, pid);
        return cartItemEntityOptional.orElseThrow(() -> new CartItemNotFoundException(uid));
    }

    public Optional<CartItemEntity> getCartItemByUidAndPid(Integer uid, Integer pid) {
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findByUserEntity_UidAndProductEntity_Pid(uid, pid);
        return cartItemEntityOptional;
    }

    @Override
    public List<CartItemEntity> getAllCartItemEntity(UserEntity user) {
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findAllByUserEntity(user);
        return cartItemEntityList;
    }

    @Override
    public void emptyUserCart(String firebaseUid) {
        cartItemRepository.deleteAllByUserEntity_FireBaseUid(firebaseUid);
    }
}
