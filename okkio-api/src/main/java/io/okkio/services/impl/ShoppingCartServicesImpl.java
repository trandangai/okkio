package io.okkio.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.okkio.domain.ProductDetail;
import io.okkio.domain.ShoppingCart;
import io.okkio.domain.User;
import io.okkio.dto.PDShoppingCartDto;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.ShoppingCartDto;
import io.okkio.dto.request.RequestProductDetailDto;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.dto.request.RequestShoppingCartDto;
import io.okkio.dto.response.ResponseProductDetailCategoryDto;
import io.okkio.mapper.ProductDetailMapper;
import io.okkio.mapper.ShoppingCartMapper;
import io.okkio.mybatis.ProductDetailMybatis;
import io.okkio.mybatis.ShoppingCartMybatis;
import io.okkio.repository.ProductDetailRepository;
import io.okkio.repository.ShoppingCartRepository;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.ProductDetailServices;
import io.okkio.services.ShoppingCartServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ShoppingCartServicesImpl
 */
@Slf4j
@Service
public class ShoppingCartServicesImpl extends BaseServiceImpl<ShoppingCart, Long> implements ShoppingCartServices {

    public ShoppingCartServicesImpl(JpaRepository<ShoppingCart, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ShoppingCartMybatis shoppingCartMybatis;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ProductDetailServices productDetailServices;

    @Override
    public ShoppingCart getShoppingCartById(Long id) {
        return shoppingCartRepository.findShoppingCartById(id);
    }

    @Override
    public List<ShoppingCart> getAllShoppingCartByUserId(Long userId, String status) {
        return shoppingCartRepository.findShoppingCartByUserIdAndStatusContaining(userId, status);
    }

    @Override
    public ShoppingCart addShoppingCart(RequestShoppingCartDto dto, String token) {
        User user = jwtTokenProvider.getUserFromJWT(token);
        ShoppingCart util = shoppingCartMapper.toEntity(dto);
        util.setUserId(user.getId());
        util.setCreatedBy(user.getEmail());
        return super.save(util);
    }

    @Override
    public boolean update(RequestShoppingCartDto dto, String token) {
        User user = jwtTokenProvider.getUserFromJWT(token);
        int updated = shoppingCartMybatis.updateShoppingCartByIds(dto.getId(), dto.getStatus(), user.getEmail(), dto.getProductDetailId());
        if (updated > 0) {
            log.warn("ShoppingCartServicesImpl - Updated success with id: " + dto.getId() + " and updated status: " + dto.getStatus());
            return true;
        }
        log.warn("ShoppingCartServicesImpl - Update failed with id: " + dto.getId() + " and status: " + dto.getStatus());
        return false;
    }

    @Override
    public boolean deleteShoppingCartById(Long id, String status, String token) {
        User user = jwtTokenProvider.getUserFromJWT(token);
        int updated = shoppingCartMybatis.updateShoppingCartByIds(id, status, user.getEmail(), 0L );
        if (updated > 0) {
            log.warn("ShoppingCartServicesImpl - Delete success with id: " + id + " and updated status: " + status);
            return true;
        }
        log.warn("ShoppingCartServicesImpl - Delete failed with id: " + id + " and status: " + status);
        return false;
    }

    @Override
    public ShoppingCartDto getShoppingCartByUser(List<ShoppingCart> shoppingCarts) {
        ShoppingCartDto result = new ShoppingCartDto();
        List<PDShoppingCartDto> detailDtoList = new ArrayList<>();
        BigDecimal total = BigDecimal.valueOf(0);
        for (ShoppingCart dto : shoppingCarts) {
            PDShoppingCartDto productDetailDto = productDetailServices.getProductDetailShoppingCartById(dto.getProductDetailId(), dto.getId());
            if (productDetailDto != null) {
                detailDtoList.add(productDetailDto);
                BigDecimal quantity = new BigDecimal(productDetailDto.getQuantity());
                total = total.add(quantity.multiply(productDetailDto.getPrice()));
            }
        }
        result.setTotal(total);
        result.setShoppingCarts(detailDtoList);
        return result;
    }

    @Override
    public boolean updateStatusShoppingCart(Long id, String status) {
        int updated = shoppingCartRepository.updateStatusShoppingCart(id, status);
        if (updated > 0) {
            log.info("updateStatusShoppingCart success with shopping cart id: " + id);
            return true;
        }
        log.info("updateStatusShoppingCart failed with shopping cart id: " + id);
        return false;
    }
}
