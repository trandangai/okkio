package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.ProductDetail;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.request.RequestProductDetailDto;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.services.CategoryServices;
import io.okkio.services.ProductDetailServices;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-detail")
public class ProductDetailController {

    private ProductDetailServices productDetailServices;

    private CategoryServices categoryServices;

    public ProductDetailController(ProductDetailServices productDetailServices, CategoryServices categoryServices) {
        this.productDetailServices = productDetailServices;
        this.categoryServices = categoryServices;
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDetail> add(@RequestBody RequestProductDetailDto dto) {
        boolean isExistedByProductDetail;
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            isExistedByProductDetail = productDetailServices.isExistedProductDetail(dto.getName());
        }
        if (isExistedByProductDetail) {
            return ResponseUtil.ok(Constants.MESSAGE_USER_IS_EXISTED, null);
        }
        ProductDetail result = productDetailServices.addProductDetail(dto);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, result);
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_GUEST', 'OKKIO_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductDetail>> getAllProductDetail() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, productDetailServices.getAllProductDetail());
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestProductDetailUpdateDto dto) {
        ProductDetailDto util = productDetailServices.getProductDetailById(dto.getId());
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, productDetailServices.update(dto));
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> delete(@Param("id") Long id) {
        ProductDetailDto util = productDetailServices.getProductDetailById(id);
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        productDetailServices.deleteProductDetailById(id);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_GUEST', 'OKKIO_ADMIN')")
    @GetMapping("/get-by-id")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@Param("id") Long id) {
        ProductDetailDto result = productDetailServices.getProductDetailById(id);
        if (result == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, result);
    }

    private boolean validatedStatus(String status) {
        return Constants.ACTIVATED_STATUS.equals(status)
                || Constants.DEACTIVATED_STATUS.equals(status);
    }
}
