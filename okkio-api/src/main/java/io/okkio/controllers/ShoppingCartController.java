package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.ProductDetail;
import io.okkio.domain.ShoppingCart;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.ShoppingCartDto;
import io.okkio.dto.request.RequestProductDetailDto;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.dto.request.RequestShoppingCartDto;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.CategoryServices;
import io.okkio.services.ProductDetailServices;
import io.okkio.services.ShoppingCartServices;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    private ProductDetailServices productDetailServices;

    private ShoppingCartServices shoppingCartServices;

    private JwtTokenProvider jwtTokenProvider;

    public ShoppingCartController(ProductDetailServices productDetailServices, ShoppingCartServices shoppingCartServices,
                                  JwtTokenProvider jwtTokenProvider) {
        this.productDetailServices = productDetailServices;
        this.shoppingCartServices = shoppingCartServices;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER','OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<ShoppingCart> add(@RequestHeader("Authorization") String token,
                                            @RequestBody RequestShoppingCartDto dto) {
        if (dto.getProductDetailId() == null || dto.getProductDetailId() < 0) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            ProductDetailDto productDetailDto = productDetailServices.getProductDetailById(dto.getProductDetailId());
            if (productDetailDto == null) {
                return ResponseUtil.badRequest(Constants.MESSAGE_NOT_FOUND);
            }
        }
        Long id = jwtTokenProvider.getUserIdFromBearerToken(token);
        if (id < 1) {
            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_NOT_EXISTED, null);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        ShoppingCart result = shoppingCartServices.addShoppingCart(dto, token);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, result);
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_ADMIN')")
    @GetMapping("/get-by-user")
    public ResponseEntity<ShoppingCartDto> getAllShoppingCartByUserId(@RequestHeader("Authorization") String token) {
        Long id = jwtTokenProvider.getUserIdFromBearerToken(token);
        if (id < 0) {
            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_NOT_EXISTED, null);
        }
        List<ShoppingCart> shoppingCarts = shoppingCartServices.getAllShoppingCartByUserId(id, Constants.ACTIVATED_STATUS);
        if (shoppingCarts == null || shoppingCarts.isEmpty()) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, shoppingCartServices.getShoppingCartByUser(shoppingCarts));
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token,
                                    @RequestBody RequestShoppingCartDto dto) {
        if (dto.getId() == null || dto.getId() < 0) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        ShoppingCart util = shoppingCartServices.getShoppingCartById(dto.getId());
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        if (dto.getProductDetailId() == null || dto.getProductDetailId() < 0) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            ProductDetailDto productDetailDto = productDetailServices.getProductDetailById(dto.getProductDetailId());
            if (productDetailDto == null) {
                return ResponseUtil.badRequest(Constants.MESSAGE_NOT_FOUND);
            }
        }
        Long id = jwtTokenProvider.getUserIdFromBearerToken(token);
        if (id < 0) {
            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_NOT_EXISTED, null);
        }
        if (!StringUtils.isEmpty(dto.getStatus())) {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, shoppingCartServices.update(dto, token));
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token,
                                    @RequestBody RequestShoppingCartDto dto) {

        if (dto.getId() == null || dto.getId() < 0) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        ShoppingCart util = shoppingCartServices.getShoppingCartById(dto.getId());
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        if (dto.getProductDetailId() == null || dto.getProductDetailId() < 0) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            ProductDetailDto productDetailDto = productDetailServices.getProductDetailById(dto.getProductDetailId());
            if (productDetailDto == null) {
                return ResponseUtil.badRequest(Constants.MESSAGE_NOT_FOUND);
            }
        }
        Long id = jwtTokenProvider.getUserIdFromBearerToken(token);
        if (id < 0) {
            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_NOT_EXISTED, null);
        }
        if (!StringUtils.isEmpty(dto.getStatus())) {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        shoppingCartServices.deleteShoppingCartById(dto.getId(), dto.getStatus(), token);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }

    private boolean validatedStatus(String status) {
        return Constants.ACTIVATED_STATUS.equals(status)
                || Constants.DEACTIVATED_STATUS.equals(status);
    }
}
