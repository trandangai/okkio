package io.okkio.controllers.version2;

import io.okkio.common.Constants;
import io.okkio.domain.ProductDetail;
import io.okkio.domain.version2.ProductDetailV2;
import io.okkio.domain.version2.ProductV2;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.dto.request.version2.RequestProductDetailDto;
import io.okkio.dto.response.version2.ResponseProductDetailDto;
import io.okkio.dto.version2.ProductDetailDto;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.version2.ProductDetailServicesV2;
import io.okkio.services.version2.ProductServicesV2;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
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
@RequestMapping("/api/v2/product-detail")
public class ProductDetailControllerV2 {

    private ProductDetailServicesV2 productDetailServices;

    private ProductServicesV2 productServices;

    private JwtTokenProvider jwtTokenProvider;

    public ProductDetailControllerV2(ProductDetailServicesV2 productDetailServices, ProductServicesV2 productServices,
                                     JwtTokenProvider jwtTokenProvider) {
        this.productDetailServices = productDetailServices;
        this.productServices = productServices;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDetailV2> add(@RequestBody RequestProductDetailDto dto, HttpServletRequest httpServletRequest) {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        boolean isExistedByProductDetail;
        boolean isExistedByProduct = true;
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            isExistedByProductDetail = productDetailServices.isExistedProductDetail(dto.getName());
        }
        if (dto.getProductId() < 0) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            ProductV2 product = productServices.getProductById(dto.getProductId());
            if (ObjectUtils.isEmpty(product)) {
                isExistedByProduct = false;
            }
        }
        if (!isExistedByProduct) {
            return ResponseUtil.ok(Constants.MESSAGE_PRODUCT_IS_NOT_EXISTS, "Product is not exists with product id: " + dto.getProductId());
        }
        if (isExistedByProductDetail) {
            return ResponseUtil.ok(Constants.MESSAGE_PRODUCT_DETAIL_IS_EXISTED, "Product detail is exited with name: " + dto.getName());
        }
        dto.setEmail(email);
        ProductDetailV2 result = productDetailServices.addProductDetail(dto);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDetailDto>> getAllProductDetail() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, productDetailServices.getAllProductDetail());
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestProductDetailUpdateDto dto, HttpServletRequest httpServletRequest) {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        if (dto.getId() == null) {
            return ResponseUtil.ok(Constants.MESSAGE_BAD_REQUEST + " with Id is null ", null);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        dto.setEmail(email);
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

    @GetMapping("/get-by-id")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@Param("id") Long id) {
        ProductDetailDto result = productDetailServices.getProductDetailById(id);
        if (result == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, result);
    }

    @GetMapping("/get-by-slug")
    public ResponseEntity<ResponseProductDetailDto> getProductDetailBySlug(@Param("slug") String slug) {
        ResponseProductDetailDto result = productDetailServices.getProductDetailBySlug(slug);
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
