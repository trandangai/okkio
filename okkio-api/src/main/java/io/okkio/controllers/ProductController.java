package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.Categories;
import io.okkio.domain.Product;
import io.okkio.domain.ProductDetail;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.ProductDto;
import io.okkio.dto.request.RequestProductDetailDto;
import io.okkio.dto.request.RequestProductDto;
import io.okkio.dto.response.ResponseProductDetailCategoryDto;
import io.okkio.dto.response.ResponseProducts;
import io.okkio.services.CategoryServices;
import io.okkio.services.ProductDetailServices;
import io.okkio.services.ProductServices;
import io.okkio.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/product")
public class ProductController {

    private ProductServices productServices;

    private CategoryServices categoryServices;

    private ProductDetailServices productDetailServices;

    public ProductController(ProductServices productServices, CategoryServices categoryServices,
                             ProductDetailServices productDetailServices) {
        this.productServices = productServices;
        this.categoryServices = categoryServices;
        this.productDetailServices = productDetailServices;
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<Product> add(@RequestBody RequestProductDto dto) {
        boolean isExistedByProduct;
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            isExistedByProduct = productServices.isExistedProduct(dto.getDescription());
        }
        if (isExistedByProduct) {
            return ResponseUtil.ok(Constants.MESSAGE_USER_IS_EXISTED, null);
        }
        Product result = productServices.addProduct(dto);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, result);
    }

//    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_GUEST', 'OKKIO_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, productServices.getAllProduct());
    }

    @GetMapping("/by-category-id")
    public ResponseEntity<List<ResponseProducts>> getByCategoryId(@Param("categoryId") Long categoryId) {
        Categories categories = categoryServices.getCategoriesById(categoryId);
        if (categories == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        List<ResponseProducts> result = new ArrayList<>();
        if ("ALL PRODUCTS".equals(categories.getName())) {
            List<Categories> total = categoryServices.getAllCategories();
            for (long i = 2; i <= total.size(); i++) {
                List<Product> tmp = productServices.getProductByCategoryId(i);
                if (tmp == null || tmp.isEmpty()) {
                    log.warn("ALL PRODUCTS - getByCategoryId - getProductByCategoryId is null with category id: " + categoryId);
                    continue;
                }
                for (Product dto : tmp) {
                    List<ProductDetail> productDetails = productDetailServices.getProductDetailByProductId(dto.getId());
                    if (productDetails == null || productDetails.isEmpty()) {
                        log.warn("getByCategoryId - getByCategoryId is null with category id: " + categoryId);
                        continue;
                    }
                    ResponseProducts responseProducts = new ResponseProducts();
                    responseProducts.setProduct(dto);
                    List<ResponseProductDetailCategoryDto> repProductDetail = new ArrayList<>();
                    for (ProductDetail productDetail: productDetails) {
                        ResponseProductDetailCategoryDto detail = productDetailServices.getProductDetailByCategoryId(productDetail.getId());
                        if (detail != null) {
                            repProductDetail.add(detail);
                        }
                    }
                    responseProducts.setProductDetail(repProductDetail);
                    result.add(responseProducts);
                }
            }
        } else {
            List<Product> tmp = productServices.getProductByCategoryId(categoryId);
            if (tmp == null || tmp.isEmpty()) {
                log.warn("getByCategoryId - getProductByCategoryId is null with category id: " + categoryId);
                return null;
            }
            for (Product dto : tmp) {
                List<ProductDetail> productDetails = productDetailServices.getProductDetailByProductId(dto.getId());
                if (productDetails == null || productDetails.isEmpty()) {
                    log.warn("getByCategoryId - getByCategoryId is null with category id: " + categoryId);
                    continue;
                }
                ResponseProducts responseProducts = new ResponseProducts();
                responseProducts.setProduct(dto);
                List<ResponseProductDetailCategoryDto> repProductDetail = new ArrayList<>();
                for(ProductDetail productDetail: productDetails) {
                    ResponseProductDetailCategoryDto detail = productDetailServices.getProductDetailByCategoryId(productDetail.getId());
                    if (detail != null) {
                        repProductDetail.add(detail);
                    }
                }
                responseProducts.setProductDetail(repProductDetail);
                result.add(responseProducts);
            }
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, result);
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestProductDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            Product util = productServices.getProductById(dto.getId());
            if (util == null) {
                return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
            }
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, productServices.update(dto));
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @DeleteMapping
    public ResponseEntity<List<Product>> delete(@Param("id") Long id) {
        Product util = productServices.getProductById(id);
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        productServices.deleteProductById(id);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<Product> getProductById(@Param("id") Long id) {
        Product util = productServices.getProductById(id);
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, util);
    }

    private boolean validatedStatus(String status) {
        return Constants.ACTIVATED_STATUS.equals(status)
                || Constants.DEACTIVATED_STATUS.equals(status);
    }
}
