package io.okkio.services;

import io.okkio.domain.Order;
import io.okkio.domain.ShoppingCart;
import io.okkio.dto.OrderDto;

import java.util.List;

public interface OrderServices {
    Order addOrder(String phone, String email);
    Order addOrderAndOrderItems(List<ShoppingCart> dto, String phoneNumber, String email);
    Order getOrderById(Long orderId);
    List<Order> getOrderByUserIdAndOrderStatus(Long userId, int orderStatus, String status);
    OrderDto updateAndGetOrder(Long receiptId, Long okkioStatusId, String updatedBy, Long orderId, String phoneNumber);
    boolean update(Long okkioStatusId, String updatedBy, Long orderId);
}
