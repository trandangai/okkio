package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.*;
import io.okkio.dto.LocationDto;
import io.okkio.dto.OrderDto;
import io.okkio.dto.request.RequestCheckoutDto;
import io.okkio.dto.request.RequestPaymentDto;
import io.okkio.dto.request.RequestProcessStatus;
import io.okkio.dto.request.RequestShipmentDto;
import io.okkio.dto.response.OrderDtoResponse;
import io.okkio.dto.response.OrderItemDtoResponse;
import io.okkio.dto.response.ResponseCheckout;
import io.okkio.services.*;
import io.okkio.util.ResponseUtil;
import io.okkio.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v2/order")
@Slf4j
public class OrderController {

    private final LocationServices locationServices;

    private ShoppingCartServices shoppingCartServices;

    private OrderServices orderServices;

    private ShipmentServices shipmentServices;

    private ReceiptServices receiptServices;

    private PaymentServices paymentServices;

    private OrderItemServices orderItemServices;

    public OrderController(OrderServices orderServices, ShipmentServices shipmentServices,
                           ReceiptServices receiptServices, PaymentServices paymentServices,
                           ShoppingCartServices shoppingCartServices,
                           OrderItemServices orderItemServices, LocationServices locationServices) {
        this.orderServices = orderServices;
        this.shipmentServices = shipmentServices;
        this.receiptServices = receiptServices;
        this.paymentServices = paymentServices;
        this.shoppingCartServices = shoppingCartServices;
        this.orderItemServices = orderItemServices;
        this.locationServices = locationServices;
    }

    @PostMapping("/checkout")
    public synchronized ResponseEntity<ResponseCheckout> checkout(@RequestBody RequestCheckoutDto dto) {
        if (StringUtil.isEmpty(dto.getFullName())
            || StringUtil.isEmpty(dto.getEmail()) || StringUtil.isEmpty(dto.getPhone())
            || StringUtil.isEmpty(dto.getDeliveryMethod()) || StringUtil.isEmpty(dto.getPaymentMethod())
            || dto.getTotal() == null || StringUtil.isEmpty(dto.getShippingTo())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (!validatedPaymentMethod(dto.getPaymentMethod())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_PAYMENT_METHOD_ERROR_CODE);
        }
        if (!validatedDeliveryMethod(dto.getDeliveryMethod())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_DELIVERY_METHOD_ERROR_CODE);
        }
        String email = dto.getEmail();
        // Add ShoppingCart first
        List<ShoppingCart> shoppingCarts = shoppingCartServices.addShoppingCarts(dto.getShoppingCartDto(), dto.getPhone());
        if (shoppingCarts == null || shoppingCarts.isEmpty()) {
            log.warn("Error addShoppingCarts with user: " + email);
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        // Create order
        Order order = orderServices.addOrderAndOrderItems(shoppingCarts, dto.getPhone(), email, dto.getFullName());
        if (order == null) {
            return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_FAILED, null);
        }
        // TODO Still waiting to check that logic
        // Get order by draft status
//        List<Order> orders = orderServices.getOrderByUserIdAndOrderStatus(user.getId(), 4, Constants.ACTIVATED_STATUS);
//        if (orders == null || orders.isEmpty()) {
//            log.warn("Error getOrderByUserIdAndOrderStatus with user: " + email);
//            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
//        }
        // Create Receipt with status waiting
        Long orderId = order.getId();
        Receipt receipt = createdReceipt(orderId, dto.getPaymentMethod(), email);
        if (receipt == null) {
            log.warn("Error createdReceipt with order id: " + orderId);
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        Long receiptId = receipt.getId();
        // Create shipment.
        if (StringUtils.isEmpty(dto.getNote())) {
            dto.setNote("");
        }
        if (dto.getPaymentMethod().equalsIgnoreCase(Constants.PAYMENT_METHOD_PICKUP)) {
            if (dto.getLocationId() != null && dto.getLocationId() > 0) {
                LocationDto location = locationServices.getLocationById(dto.getLocationId());
                if (location == null) {
                    return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST + " with locationId: " + dto.getLocationId());
                }
                dto.setAddress(location.getAddress());
            } else {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST + " with locationId: " + dto.getLocationId());
            }
        } else {
            if (StringUtils.isEmpty(dto.getAddress())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST + " Address is null ");
            }
        }
        Shipment shipment = createdShipment(dto.getNote(), dto.getDeliveryMethod(), orderId, receiptId, dto.getShippingTo(), dto.getAddress(), dto.getPaymentMethod());
        if (shipment == null) {
            log.warn("Error createdShipment with order id: " + orderId + " and receipt id: " + receiptId);
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        // Create payment
        Payment payment = createPayment(dto.getTotal(), dto.getPaymentMethod(), receiptId, email);
        if (payment == null) {
            log.warn("Error createPayment with total id: " + dto.getTotal() + " and receipt id: " + receiptId);
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        // TODO Check total
        // TODO check discount
        // TODO check payment - maybe
        // Updated Order Draft status to Waiting status
        orderServices.update(5L, email, orderId);
        ResponseCheckout result = new ResponseCheckout();
        result.setReceiptId(receiptId);
        result.setOrderId(orderId);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, result);
    }

    @PutMapping("/checkout-process")
    public ResponseEntity<OrderDto> update(@RequestBody RequestProcessStatus dto) {
        if (dto.getReceiptId() == null || dto.getOrderId() == null) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        Receipt receipt = receiptServices.getReceiptById(dto.getReceiptId());
        if (receipt == null) {
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND + " with receipt id: " + dto.getReceiptId());
        }
        Order order = orderServices.getOrderById(dto.getOrderId());
        if (order == null) {
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        if (!StringUtil.isEmpty(dto.getStatusReceipt())) {
            // Update flow from Waiting to PAID or ISSUED
            if (!validatedReceiptStatus(dto.getStatusReceipt())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST + " - Invalid Receipt Status");
            }
            long receiptStatus;
            if (dto.getStatusReceipt().equals(Constants.RECEIPT_PAID_STATUS)) {
                receiptStatus = 10;
            } else {
                receiptStatus = 11;
            }
            // Updated receipt and get order dto with PAID receipt status.
            OrderDto result = orderServices.updateAndGetOrder(receipt.getId(), receiptStatus, order.getEmail(), dto.getOrderId(), order.getPhone());
            return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, result);
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, null);
    }

    @GetMapping("/get-order-by-code")
    public ResponseEntity<?> getOrderById(@RequestParam("code") String code) {
        Order order = orderServices.getOrderByOrderCode(code);
        if (order == null) {
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND + " with code " + code);
        }
        Long orderId = order.getId();
        OrderDtoResponse result = orderServices.getOrderDetailById(orderId);
        if (!ObjectUtils.isEmpty(result)) {
            List<OrderItemDtoResponse> orderItemDtoResponses = orderItemServices.getOrderItemsByOrderId(orderId);
            if (!ObjectUtils.isEmpty(orderItemDtoResponses)) {
                result.setOrderItems(orderItemDtoResponses);
            }
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllOrder(@RequestParam(defaultValue = "0") Integer pageNumber,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, orderServices.getAllOrderAndOrderItem(pageNumber, pageSize, sortBy));
    }

    private boolean validatedReceiptStatus(String status) {
        return Constants.RECEIPT_PAID_STATUS.equals(status)
                || Constants.RECEIPT_ISSUED_STATUS.equals(status);
    }

    private boolean validatedPaymentMethod(String paymentMethod) {
        return Constants.PAYMENT_METHOD_COD.equals(paymentMethod)
                || Constants.PAYMENT_METHOD_VISA.equals(paymentMethod)
                || Constants.PAYMENT_METHOD_ATM.equals(paymentMethod)
                || Constants.PAYMENT_METHOD_MOMO.equals(paymentMethod)
                || Constants.PAYMENT_METHOD_PICKUP.equals(paymentMethod);
    }

    private boolean validatedDeliveryMethod(String deliveryMethod) {
        return Constants.DELIVERY_METHOD_NT.equals(deliveryMethod)
                || Constants.DELIVERY_METHOD_TLH.equals(deliveryMethod)
                || Constants.DELIVERY_METHOD_TQ.equals(deliveryMethod);
    }

    private Receipt createdReceipt(Long orderId, String detail, String email) {
        Receipt receipt = new Receipt();
        receipt.setDetail(detail);
        receipt.setStatus(Constants.ACTIVATED_STATUS);
        // Waiting
        receipt.setOkkioStatusId(9L);
        receipt.setOrderId(orderId);
        receipt.setCreatedBy(email);
        return receiptServices.addReceipt(receipt);
    }

    private Shipment createdShipment(String note, String type, Long orderId, Long receiptId, String shippingTo, String address, String paymentMethod) {
        RequestShipmentDto dto = new RequestShipmentDto();
        dto.setDetail(note);
        dto.setOrderId(orderId);
        dto.setStatus(Constants.ACTIVATED_STATUS);
        dto.setType(type);
        dto.setReceiptId(receiptId);
        dto.setShippingTo(shippingTo);
        dto.setAddress(address);
        dto.setPaymentMethod(paymentMethod);
        return shipmentServices.addShipment(dto);
    }

    private Payment createPayment(BigDecimal amount, String paymentMethod, Long receiptId, String email) {
        RequestPaymentDto dto = new RequestPaymentDto();
        dto.setPaymentMethod(paymentMethod);
        dto.setAmount(amount);
        dto.setStatus(Constants.ACTIVATED_STATUS);
        dto.setReceiptId(receiptId);
        return paymentServices.addPayment(dto, email);
    }
}
