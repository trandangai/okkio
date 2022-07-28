package io.okkio.services.impl;

import io.okkio.domain.OrderItem;
import io.okkio.services.OrderItemServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * OrderItemServicesImpl
 */
@Slf4j
@Service
public class OrderItemServicesImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemServices {

    public OrderItemServicesImpl(JpaRepository<OrderItem, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public OrderItem addOrderItem(OrderItem orderItem, String email) {
        orderItem.setCreatedBy(email);
        return super.save(orderItem);
    }
}
