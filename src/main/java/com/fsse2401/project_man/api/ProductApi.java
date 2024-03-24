package com.fsse2401.project_man.api;

import com.fsse2401.project_man.data.domainObject.product.response.ProductResponseData;
import com.fsse2401.project_man.data.dto.product.response.GetAllProductResponseDto;
import com.fsse2401.project_man.data.dto.product.response.ProductResponseDto;
import com.fsse2401.project_man.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/product")
public class ProductApi {
    private final ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<GetAllProductResponseDto> getAllProduct() {
        List<GetAllProductResponseDto> getAllProductResponseDtoList = new ArrayList<>();
        for (ProductResponseData productResponseData : productService.getAllProduct()) {
            getAllProductResponseDtoList.add(new GetAllProductResponseDto(productResponseData));
        }
        return getAllProductResponseDtoList;
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductByPid(@PathVariable Integer id) {
        ProductResponseData getProductByPidResponseData = productService.getProductByPid(id);
        return new ProductResponseDto(getProductByPidResponseData);
    }
}
