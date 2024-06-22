package io.okkio.services.impl;

import io.okkio.common.Constants;
import io.okkio.domain.Order;
import io.okkio.domain.OrderItem;
import io.okkio.domain.ShoppingCart;
import io.okkio.dto.OrderDto;
import io.okkio.dto.ShoppingCartDto;
import io.okkio.dto.response.OrderDtoPagingResponse;
import io.okkio.dto.response.OrderDtoResponse;
import io.okkio.dto.response.OrderItemDtoResponse;
import io.okkio.mybatis.OrderMybatis;
import io.okkio.repository.OrderRepository;
import io.okkio.repository.ReceiptRepository;
import io.okkio.services.OrderItemServices;
import io.okkio.services.OrderServices;
import io.okkio.services.ShoppingCartServices;
import io.okkio.services.version2.ProductDetailServicesV2;
import io.okkio.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * OrderServicesImpl
 */
@Slf4j
@Service
public class OrderServicesImpl extends BaseServiceImpl<Order, Long> implements OrderServices {

    public OrderServicesImpl(JpaRepository<Order, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private ProductDetailServicesV2 productDetailServices;

    @Autowired
    private OrderItemServices orderItemServices;

    @Autowired
    private ShoppingCartServices shoppingCartServices;

    @Autowired
    private OrderMybatis orderMybatis;

    @Value("${order.description}")
    private String description;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public synchronized Order addOrderAndOrderItems(List<ShoppingCart> shoppingCarts, String phone, String email, String name) {
        log.info("Start addOrderAndOrderItems with email={}, phone={}", email, phone);
        Order result = addOrder(phone, email, name);
        if (result == null) {
            log.warn("Add order failed with email: {}  and phone: {}", email, phone);
            return null;
        }
        for (ShoppingCart dto : shoppingCarts) {
            dto.setOrderId(result.getId());
            OrderItem item = new OrderItem();
            item.setOrderId(result.getId());
                // STOCKING
            item.setOkkioStatusId(2L);
            item.setProductDetailId(dto.getProductDetailId());
            item.setPrice(productDetailServices.getProductDetailById(dto.getProductDetailId()).getPrice());
            item.setGrind(dto.getGrind());
            item.setSize(dto.getSize());
            item.setQuantity(dto.getQuantity());
            orderItemServices.addOrderItem(item, email);
            // Need update shopping cart id with order id
            shoppingCartServices.updateShoppingCarts(dto);
        }
        log.info("End addOrderAndOrderItems with email={}, phone={}", email, phone);
        return result;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findProductById(orderId);
    }

    @Override
    public List<Order> getOrderByUserIdAndOrderStatus(Long userId, int orderStatus, String status) {
        return orderRepository.findOrderByUserIdAndOrderStatus(userId, orderStatus, status);
    }

    @Override
    public OrderDto updateAndGetOrder(Long receiptId, Long okkioStatusId, String updatedBy, Long orderId, String phoneNumber) {
        // Update Receipt Status
        int isUpdated = receiptRepository.updateStatusReceipt(receiptId, okkioStatusId, updatedBy);
        if (isUpdated > 0 && okkioStatusId == 10L) {
            // updated order Approved status
            update(6L, updatedBy, orderId);
            log.warn("OrderServicesImpl - Updated with receipt id: " + receiptId + " and status: " + okkioStatusId);
            // Get order detail with PAID receipt status
            OrderDto result = orderMybatis.getOrderDto(orderId);
            if (result == null) {
                log.error("updateAndGetOrder error getOrderDto orderId: " + orderId + " and status: " + okkioStatusId);
                return null;
            }
            result.setTotal(result.getShippingFee().add(result.getPrice()));
            List<ShoppingCart> shoppingCarts = shoppingCartServices.getAllShoppingCartByUserId(phoneNumber, Constants.ACTIVATED_STATUS, orderId);
            if (!shoppingCarts.isEmpty()) {
                ShoppingCartDto dto = shoppingCartServices.getShoppingCartByUser(shoppingCarts);
                result.setOrderDetail(dto);
//                result.setTotal(dto.getTotal());
                // Update deactivate status shopping cart
//                for (ShoppingCart cart : shoppingCarts) {
//                    shoppingCartServices.updateStatusShoppingCart(cart.getId(), Constants.DEACTIVATED_STATUS);
//                }
            }
            return result;
        } else if (isUpdated > 0 && okkioStatusId == 11L) {
            // updated order FAILED status
            update(7L, updatedBy, orderId);
            log.warn("OrderServicesImpl - Updated with receipt id: " + receiptId + " and status: " + okkioStatusId);
            // Get order detail with ISSUED receipt status
            OrderDto result = orderMybatis.getOrderDto(orderId);
            if (result == null) {
                log.error("updateAndGetOrder error getOrderDto orderId: " + orderId + " and status: " + okkioStatusId);
                return null;
            }
            result.setTotal(result.getShippingFee().add(result.getPrice()));
            List<ShoppingCart> shoppingCarts = shoppingCartServices.getAllShoppingCartByUserId(phoneNumber, Constants.ACTIVATED_STATUS,orderId);
            if (!shoppingCarts.isEmpty()) {
                ShoppingCartDto dto = shoppingCartServices.getShoppingCartByUser(shoppingCarts);
                result.setOrderDetail(dto);
//                result.setTotal(dto.getTotal());
                // Update deactivate status shopping cart
//                for (ShoppingCart cart : shoppingCarts) {
//                    shoppingCartServices.updateStatusShoppingCart(cart.getId(), Constants.DEACTIVATED_STATUS);
//                }
            }
            return result;
        }
        log.warn("Cant updated receipt status and get order detail");
        return null;
    }

    @Override
    public boolean update(Long okkioStatusId, String updatedBy, Long orderId) {
        int updated = orderRepository.update(okkioStatusId, updatedBy, orderId);
        if (updated > 0) {
            log.warn("OrderServicesImpl - Updated to success with orderId " + orderId + " with status: " + okkioStatusId);
            return true;
        }
        log.warn("OrderServicesImpl - Updated to failed with orderId: " + orderId + " with status: " + okkioStatusId);
        return false;
    }

    @Override
    public OrderDtoResponse getOrderDetailById(Long orderId) {
        log.info("Start OrderServicesImpl - Get order detail by orderId: {}", orderId);
        OrderDtoResponse result = orderMybatis.getOrderDetailDto(orderId);
        result.setTotal(result.getPrice().add(result.getShippingFee()));
        log.debug("End OrderServicesImpl - Get order detail by orderId: {} and result: {}", orderId, result);
        log.info("End OrderServicesImpl - Get order detail by orderId: {}", orderId);
        return result;
    }

    @Override
    public Order getOrderByOrderCode(String orderCode) {
        return orderRepository.findOrderByOrderCode(orderCode);
    }

    @Override
    public OrderDtoPagingResponse getAllOrderAndOrderItem(Integer pageNumber, Integer pageSize, String sortBy) {
        log.info("Start OrderServicesImpl - Get All order and order item");
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Order> orders = orderRepository.findOrderByStatus(paging);
        if (orders.isEmpty()) {
            log.error("getAllOrderAndOrderItem error no orders found");
            return null;
        }
        OrderDtoPagingResponse result = new OrderDtoPagingResponse();
        result.setSize(orders.getSize());
        result.setTotalElements(orders.getTotalElements());
        result.setTotalPages(orders.getTotalPages());
        result.setLast(orders.isLast());
        result.setNumber(orders.getNumber());
        result.setNumberOfElements(orders.getNumberOfElements());
        List<OrderDtoResponse> DTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDtoResponse orderDtoResponse = orderMybatis.getOrderDetailDto(order.getId());
            if (orderDtoResponse != null) {
                orderDtoResponse.setTotal(orderDtoResponse.getPrice().add(orderDtoResponse.getShippingFee()));
                List<OrderItemDtoResponse> orderItemDtoResponses = orderItemServices.getOrderItemsByOrderId(order.getId());
                if (!ObjectUtils.isEmpty(orderItemDtoResponses)) {
                    orderDtoResponse.setOrderItems(orderItemDtoResponses);
                }
            }
            DTOs.add(orderDtoResponse);
        }
        result.setContent(DTOs);
        log.info("End OrderServicesImpl - Get All order and order item");
        return result;
    }

    @Override
    public Order addOrder(String phone, String email, String name) {
        Order order = new Order();
        order.setStatus(Constants.ACTIVATED_STATUS);
        // Status Draft
        order.setOkkioStatusId(4L);
        order.setDescription(description);
        order.setCreatedBy(email);
        order.setEmail(email);
        order.setPhone(phone);
        order.setFullName(name);
        order.setOrderCode("OKKIO-" + DateUtil.toString(new Date(), "dd-MM-yyyy-HH:mm:ss") + "-" + UUID.randomUUID().toString());
        return super.save(order);
    }
}
