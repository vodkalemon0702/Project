package com.fsse2401.project_man.service;

import com.fsse2401.project_man.data.domainObject.CartItem.response.CartItemResponseData;
import com.fsse2401.project_man.data.domainObject.CartItem.response.PutCartItemResponseData;
import com.fsse2401.project_man.data.entity.CartItemEntity;
import com.fsse2401.project_man.data.user.domainObject.FirebaseUserData;

import java.util.List;

public interface CartItemService {

    PutCartItemResponseData putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);

    List<CartItemResponseData> getAllCartProductsByUserId(FirebaseUserData firebaseUserData);

    CartItemResponseData updateCartQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);
}
