package io.okkio.services;

import io.okkio.domain.OrderItem;
import io.okkio.dto.response.OrderItemDtoResponse;

import java.util.List;

public interface OrderItemServices {
    OrderItem addOrderItem(OrderItem orderItem, String email);
    List<OrderItemDtoResponse> getOrderItemsByOrderId(Long orderId);
}
