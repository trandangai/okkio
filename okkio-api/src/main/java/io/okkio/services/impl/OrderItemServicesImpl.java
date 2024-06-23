package io.okkio.services.impl;

import io.okkio.domain.OrderItem;
import io.okkio.dto.response.OrderItemDtoResponse;
import io.okkio.mybatis.OrderItemMybatis;
import io.okkio.services.OrderItemServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OrderItemServicesImpl
 */
@Slf4j
@Service
public class OrderItemServicesImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemServices {

    public OrderItemServicesImpl(JpaRepository<OrderItem, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private OrderItemMybatis orderItemMybatis;

    @Override
    public OrderItem addOrderItem(OrderItem orderItem, String email) {
        log.info("Start Add order item: {}", orderItem);
        orderItem.setCreatedBy(email);
        log.info("End Add order item: {}", orderItem);
        return super.save(orderItem);
    }

    @Override
    public List<OrderItemDtoResponse> getOrderItemsByOrderId(Long orderId) {
        log.info("Start getOrderItemsByOrderId: {}", orderId);
        List<OrderItemDtoResponse> result = orderItemMybatis.getOrderItemByOrderId(orderId);
        log.info("End getOrderItemsByOrderId: {}", orderId);
        return result;
    }
}
