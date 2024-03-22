package com.fsse2401.project_man.service.Impl;

import com.fsse2401.project_man.data.domainObject.cartItem.response.CartItemResponseData;
import com.fsse2401.project_man.data.domainObject.cartItem.response.PutCartItemResponseData;
import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.entity.ProductEntity;
import com.fsse2401.project_man.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_man.data.user.entity.UserEntity;
import com.fsse2401.project_man.exception.RequestDataInvalidException;
import com.fsse2401.project_man.exception.RequestDataMissingException;
import com.fsse2401.project_man.exception.cart.ExcessiveProductQuantityException;
import com.fsse2401.project_man.exception.cart.QuantityInvalidException;
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
    public PutCartItemResponseData putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity) {
        try {
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            ProductEntity productEntity = productService.getProductById(pid);
            if (pid == null || quantity == null) {
                throw new RequestDataMissingException();
            }
            if (quantity < 1) {
                throw new QuantityInvalidException();
            }
            if (productService.getProductById(pid).getStock() < quantity){
                throw new ExcessiveProductQuantityException();
            }
            if (productEntity.getStock() < 1) {
                throw new ProductOutOfStockException();
            }
            if (getCartItemEntityByUidAndPid(userEntity.getUid(),productEntity.getPid()) != null) {
                CartItemEntity cartItemEntity = getCartItemEntityByUidAndPid(userEntity.getUid(),productEntity.getPid());
                if (cartItemEntity.getQuantity() == productEntity.getStock()){
                    throw new ExcessiveProductQuantityException();
                }
                int newQuantity = cartItemEntity.getQuantity() + quantity;
                cartItemEntity.setQuantity(newQuantity);
                cartItemRepository.save(cartItemEntity);
                PutCartItemResponseData putCartItemResponseData = new PutCartItemResponseData();
                putCartItemResponseData.setResult("SUCCESS");
                return putCartItemResponseData;
            } else {
                CartItemEntity cartItemEntity = new CartItemEntity(userEntity,productEntity,quantity);
//                cartItemEntity.setProductEntity(productEntity);
//                cartItemEntity.setUserEntity(userEntity);
//                cartItemEntity.setQuantity(quantity);
                cartItemRepository.save(cartItemEntity);
                PutCartItemResponseData putCartItemResponseData = new PutCartItemResponseData();
                putCartItemResponseData.setResult("SUCCESS");
                return putCartItemResponseData;
            }
        } catch (RequestDataMissingException e) {
            logger.info("Put cart item:Data missing!");
            throw e;
        }  catch (QuantityInvalidException e) {
            logger.info("Put cart item:Quantity invalid!");
            throw e;
        } catch (ProductOutOfStockException e) {
            logger.info("Put cart item:Product is out of stock!");
            throw e;
        }catch (ExcessiveProductQuantityException e){
            logger.info("Put cart item:Product stock not enough!");
            throw e;
        }
    }
    @Override
    public List<CartItemResponseData> getAllCartProductsByUserId(FirebaseUserData firebaseUserData) {
        try {
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            List<CartItemEntity> cartItemEntityList = cartItemRepository.findAllByUserEntity(userEntity);
            if (cartItemEntityList.isEmpty()){
                throw new ProductNotFoundException();
            }
            List<CartItemResponseData> cartItemResponseDataList = new ArrayList<>();
            for (CartItemEntity cartItemEntity : cartItemEntityList) {
                CartItemResponseData cartItemResponseData = new CartItemResponseData(cartItemEntity);
                cartItemResponseDataList.add(cartItemResponseData);
            }
            return cartItemResponseDataList;
        }catch (ProductNotFoundException e){
            logger.info("Get all cart product:Cart is empty!");
            throw e;
        }
    }
    @Override
    public CartItemResponseData updateCartQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity){
        try{
            if (quantity < 1){
                throw new QuantityInvalidException();
            }
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityUidAndProductEntityPid(userEntity.getUid(),pid);
            if (cartItemEntity == null){
                throw new ProductNotFoundException();
            }
            if (quantity > cartItemEntity.getProductEntity().getStock()){
                throw new ExcessiveProductQuantityException();
            }
            if (quantity == cartItemEntity.getQuantity()){
                throw new QuantityInvalidException();
            }
            cartItemEntity.setQuantity(quantity);
            cartItemRepository.save(cartItemEntity);
            return new CartItemResponseData(cartItemEntity);
        }catch (QuantityInvalidException e){
            logger.info("Update quantity:Quantity invalid!");
            throw e;
        }catch (ProductNotFoundException e){
            logger.info("Update quantity:Product not found in the cart!");
            throw e;
        }catch (ExcessiveProductQuantityException e){
            logger.info("Update quantity:Product' stock not enough!");
            throw e;
        }
    }
    @Override
    public PutCartItemResponseData deleteCartItem(FirebaseUserData firebaseUserData, Integer pid){
        try {
            UserEntity userEntity = userService.getEntityByFireBaseUserData(firebaseUserData);
            if (pid < 1 || pid == null){
                throw new RequestDataInvalidException();
            }
            CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityUidAndProductEntityPid(userEntity.getUid(), pid);
            if (cartItemEntity == null){
                throw new ProductNotFoundException();
            }
            cartItemRepository.delete(cartItemEntity);
            PutCartItemResponseData putCartItemResponseData = new PutCartItemResponseData();
            putCartItemResponseData.setResult("SUCCESS");
            return putCartItemResponseData;
        }catch (RequestDataMissingException e){
            logger.info("Delete cart item:Product ID invalid!");
            throw e;
        }catch (ProductNotFoundException e){
            logger.info("Delete cart item:Product not found!");
            throw e;
        }
    }
    public CartItemEntity getCartItemEntityByUidAndPid(Integer uid,Integer pid){
        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityUidAndProductEntityPid(uid,pid);
        return cartItemEntity;
    }
    @Override
    public List<CartItemEntity> getAllCartItemEntity(UserEntity user){
        List<CartItemEntity>cartItemEntityList =  cartItemRepository.findAllByUserEntity(user);
        return cartItemEntityList;
    }
    @Override
    public void emptyUserCart(String firebaseUid){
        cartItemRepository.deleteAllByUserEntity_FireBaseUid(firebaseUid);
    }
}
