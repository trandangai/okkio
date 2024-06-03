package io.okkio.controllers.version2;

import io.okkio.common.Constants;
import io.okkio.domain.Product;
import io.okkio.domain.version2.ProductV2;
import io.okkio.dto.request.version2.RequestProductDto;
import io.okkio.dto.response.ResponseProducts;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.version2.ProductServicesV2;
import io.okkio.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v2/product")
public class ProductControllerV2 {

    private ProductServicesV2 productServices;

    private JwtTokenProvider jwtTokenProvider;

    public ProductControllerV2(ProductServicesV2 productServices, JwtTokenProvider jwtTokenProvider) {
        this.productServices = productServices;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<Product> add(@RequestBody RequestProductDto dto, HttpServletRequest httpServletRequest) {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        boolean isExistedByProduct;
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            isExistedByProduct = productServices.isExistedProduct(dto.getDescription());
        }
        if (isExistedByProduct) {
            return ResponseUtil.ok(Constants.MESSAGE_USER_IS_EXISTED, null);
        }
        dto.setEmail(email);
        ProductV2 result = productServices.addProduct(dto);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductV2>> getAllProduct() {
        List<ProductV2> result = productServices.getAllProduct();
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, productServices.getAllProduct());
    }

    @GetMapping("/by-category-id")
    public ResponseEntity<List<ResponseProducts>> getByCategoryId(@Param("categoryId") Long categoryId) {
        return null;
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestProductDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            ProductV2 util = productServices.getProductById(dto.getId());
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
        ProductV2 util = productServices.getProductById(id);
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        productServices.deleteProductById(id);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<Product> getProductById(@Param("id") Long id) {
        ProductV2 util = productServices.getProductById(id);
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, util);
    }

    @GetMapping("/get-by-slug")
    public ResponseEntity<Product> getProductById(@Param("slug") String slug) {
        ProductV2 util = productServices.getProductBySlug(slug);
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