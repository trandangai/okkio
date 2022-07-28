package io.okkio.services;

import io.okkio.domain.Order;
import io.okkio.domain.ShoppingCart;
import io.okkio.dto.OrderDto;

import java.util.List;

public interface OrderServices {
    Order addOrder(Long userId, String email);
    Order addOrderAndOrderItems(List<ShoppingCart> dto, Long userId, String email);
    Order getOrderById(Long orderId);
    List<Order> getOrderByUserIdAndOrderStatus(Long userId, int orderStatus, String status);
    OrderDto updateAndGetOrder(Long receiptId, Long okkioStatusId, String updatedBy, Long orderId, Long userId);
    boolean update(Long okkioStatusId, String updatedBy, Long orderId);
}
