package com.fsse2401.project_man.api;

import com.fsse2401.project_man.data.domainObject.CartItem.response.CartItemResponseData;
import com.fsse2401.project_man.data.domainObject.CartItem.response.PutCartItemResponseData;
import com.fsse2401.project_man.data.dto.cartItem.response.GetAllCartProductByUserIdDto;
import com.fsse2401.project_man.data.dto.cartItem.response.PutCartItemResponseDto;
import com.fsse2401.project_man.data.dto.product.response.GetProductByPidResponseDto;
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
    public PutCartItemResponseDto putCartItem(JwtAuthenticationToken jwtToken,
                                              @PathVariable Integer pid,
                                              @PathVariable Integer quantity) {
        PutCartItemResponseData putCartItemResponseData = cartItemService.putCartItem(JwtUtil.getFirebaseUserData(jwtToken), pid, quantity);
        PutCartItemResponseDto putCartItemResponseDto = new PutCartItemResponseDto(putCartItemResponseData);
        return putCartItemResponseDto;
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
}
