//package io.okkio.controllers;
//
//import io.okkio.common.Constants;
//import io.okkio.domain.ShoppingCart;
//import io.okkio.dto.ShoppingCartDto;
//import io.okkio.dto.request.RequestShoppingCartDto;
//import io.okkio.dto.version2.ProductDetailDto;
//import io.okkio.security.JwtTokenProvider;
//import io.okkio.services.ShoppingCartServices;
//import io.okkio.services.version2.ProductDetailServicesV2;
//import io.okkio.util.ResponseUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v2/shopping-cart")
//public class ShoppingCartController {
//
//    private ProductDetailServicesV2 productDetailServices;
//
//    private ShoppingCartServices shoppingCartServices;
//
//    private JwtTokenProvider jwtTokenProvider;
//
//    public ShoppingCartController(ProductDetailServicesV2 productDetailServices, ShoppingCartServices shoppingCartServices,
//                                  JwtTokenProvider jwtTokenProvider) {
//        this.productDetailServices = productDetailServices;
//        this.shoppingCartServices = shoppingCartServices;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @PostMapping
//    public ResponseEntity<ShoppingCart> add(@RequestBody RequestShoppingCartDto dto) {
//        if (dto.getProductDetailId() == null || dto.getProductDetailId() < 0) {
//            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
//        } else {
//            ProductDetailDto productDetailDto = productDetailServices.getProductDetailById(dto.getProductDetailId());
//            if (productDetailDto == null) {
//                return ResponseUtil.badRequest(Constants.MESSAGE_NOT_FOUND);
//            }
//        }
//        if (StringUtils.isEmpty(dto.getStatus())) {
//            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
//        } else {
//            if (!validatedStatus(dto.getStatus())) {
//                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
//            }
//        }
//        ShoppingCart result = shoppingCartServices.addShoppingCart(dto);
//        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, result);
//    }
//
//    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_ADMIN')")
//    @PutMapping
//    public ResponseEntity<?> update(@RequestHeader("Authorization") String token,
//                                    @RequestBody RequestShoppingCartDto dto) {
//        if (dto.getId() == null || dto.getId() < 0) {
//            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
//        }
//        ShoppingCart util = shoppingCartServices.getShoppingCartById(dto.getId());
//        if (util == null) {
//            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
//        }
//        if (dto.getProductDetailId() == null || dto.getProductDetailId() < 0) {
//            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
//        } else {
//            ProductDetailDto productDetailDto = productDetailServices.getProductDetailById(dto.getProductDetailId());
//            if (productDetailDto == null) {
//                return ResponseUtil.badRequest(Constants.MESSAGE_NOT_FOUND);
//            }
//        }
//        Long id = jwtTokenProvider.getUserIdFromBearerToken(token);
//        if (id < 0) {
//            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_NOT_EXISTED, null);
//        }
//        if (!StringUtils.isEmpty(dto.getStatus())) {
//            if (!validatedStatus(dto.getStatus())) {
//                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
//            }
//        }
//        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, shoppingCartServices.update(dto, token));
//    }
//
//    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_ADMIN')")
//    @DeleteMapping
//    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token,
//                                    @RequestBody RequestShoppingCartDto dto) {
//
//        if (dto.getId() == null || dto.getId() < 0) {
//            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
//        }
//        ShoppingCart util = shoppingCartServices.getShoppingCartById(dto.getId());
//        if (util == null) {
//            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
//        }
//        if (dto.getProductDetailId() == null || dto.getProductDetailId() < 0) {
//            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
//        } else {
//            ProductDetailDto productDetailDto = productDetailServices.getProductDetailById(dto.getProductDetailId());
//            if (productDetailDto == null) {
//                return ResponseUtil.badRequest(Constants.MESSAGE_NOT_FOUND);
//            }
//        }
//        Long id = jwtTokenProvider.getUserIdFromBearerToken(token);
//        if (id < 0) {
//            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_NOT_EXISTED, null);
//        }
//        shoppingCartServices.deleteShoppingCartById(dto.getId(), Constants.DEACTIVATED_STATUS, token);
//        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id: " + id, null);
//    }
//
//    private boolean validatedStatus(String status) {
//        return Constants.ACTIVATED_STATUS.equals(status)
//                || Constants.DEACTIVATED_STATUS.equals(status);
//    }
//}
