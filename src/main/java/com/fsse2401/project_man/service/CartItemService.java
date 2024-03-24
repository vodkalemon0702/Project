package com.fsse2401.project_man.service;

import com.fsse2401.project_man.data.domainObject.cartItem.response.CartItemResponseData;
import com.fsse2401.project_man.data.domainObject.cartItem.response.PutCartItemResponseData;
import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_man.data.user.entity.UserEntity;

import java.util.List;

public interface CartItemService {


    boolean putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);

    List<CartItemResponseData> getAllCartProductsByUserId(FirebaseUserData firebaseUserData);

    CartItemResponseData updateCartQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);


    boolean deleteCartItem(FirebaseUserData firebaseUserData, Integer pid);

    List<CartItemEntity> getAllCartItemEntity(UserEntity user);

    void emptyUserCart(String firebaseUid);
}
