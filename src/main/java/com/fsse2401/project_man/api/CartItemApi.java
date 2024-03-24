package com.fsse2401.project_man.api;

import com.fsse2401.project_man.data.domainObject.cartItem.response.CartItemResponseData;
import com.fsse2401.project_man.data.domainObject.cartItem.response.CartItemSuccessDto;
import com.fsse2401.project_man.data.dto.cartItem.response.GetAllCartProductByUserIdDto;
import com.fsse2401.project_man.service.CartItemService;
import com.fsse2401.project_man.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemApi {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemApi(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PutMapping("/{pid}/{quantity}")
    public CartItemSuccessDto putCartItem(JwtAuthenticationToken jwtToken,
                                              @PathVariable Integer pid,
                                              @PathVariable Integer quantity) {
        cartItemService.putCartItem(JwtUtil.getFirebaseUserData(jwtToken), pid, quantity);
        return new CartItemSuccessDto();
    }

    @GetMapping
    public List<GetAllCartProductByUserIdDto> getAllCartProductsByUserId(JwtAuthenticationToken jwtToken) {
        List<CartItemResponseData> cartItemResponseDataList = cartItemService.getAllCartProductsByUserId(JwtUtil.getFirebaseUserData(jwtToken));
        List<GetAllCartProductByUserIdDto> getAllCartProductByUserIdDtoList = new ArrayList<>();
        for (CartItemResponseData cartItemResponseData : cartItemResponseDataList) {
            GetAllCartProductByUserIdDto getAllCartProductByUserIdDto = new GetAllCartProductByUserIdDto(cartItemResponseData);
            getAllCartProductByUserIdDtoList.add(getAllCartProductByUserIdDto);
        }
        return getAllCartProductByUserIdDtoList;
    }

    @PatchMapping("/{pid}/{quantity}")
    public GetAllCartProductByUserIdDto updateCartQuantity(JwtAuthenticationToken jwtToken,
                                                           @PathVariable Integer pid,
                                                           @PathVariable Integer quantity) {
        CartItemResponseData cartItemResponseData = cartItemService.updateCartQuantity(JwtUtil.getFirebaseUserData(jwtToken),pid,quantity);
        GetAllCartProductByUserIdDto getAllCartProductByUserIdDto = new GetAllCartProductByUserIdDto(cartItemResponseData);
        return getAllCartProductByUserIdDto;
    }
    @DeleteMapping("/{pid}")
    public CartItemSuccessDto deleteCartItem(JwtAuthenticationToken jwtToken,
                                                 @PathVariable Integer pid){
        cartItemService.deleteCartItem(JwtUtil.getFirebaseUserData(jwtToken),pid);
        return new CartItemSuccessDto();
    }
}
