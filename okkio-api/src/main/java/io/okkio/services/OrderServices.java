package io.okkio.services;

import io.okkio.domain.Order;
import io.okkio.domain.ShoppingCart;
import io.okkio.dto.OrderDto;
import io.okkio.dto.response.OrderDtoPagingResponse;
import io.okkio.dto.response.OrderDtoResponse;

import java.util.List;

public interface OrderServices {
    Order addOrder(String phone, String email, String name);
    Order addOrderAndOrderItems(List<ShoppingCart> dto, String phoneNumber, String email, String name);
    Order getOrderById(Long orderId);
    List<Order> getOrderByUserIdAndOrderStatus(Long userId, int orderStatus, String status);
    OrderDto updateAndGetOrder(Long receiptId, Long okkioStatusId, String updatedBy, Long orderId, String phoneNumber);
    boolean update(Long okkioStatusId, String updatedBy, Long orderId);
    OrderDtoResponse getOrderDetailById(Long orderId);
    Order getOrderByOrderCode(String orderCode);
    OrderDtoPagingResponse getAllOrderAndOrderItem(Integer pageNumber, Integer pageSize, String sortBy);
}
