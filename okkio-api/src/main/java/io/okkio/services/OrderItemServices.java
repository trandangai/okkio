package io.okkio.services;

import io.okkio.domain.OrderItem;

public interface OrderItemServices {
    OrderItem addOrderItem(OrderItem orderItem, String email);
}
