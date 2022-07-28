package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.*;
import io.okkio.dto.OrderDto;
import io.okkio.dto.ShoppingCartDto;
import io.okkio.dto.request.RequestCheckoutDto;
import io.okkio.dto.request.RequestPaymentDto;
import io.okkio.dto.request.RequestProcessStatus;
import io.okkio.dto.request.RequestShipmentDto;
import io.okkio.dto.response.ResponseCheckout;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.*;
import io.okkio.util.ResponseUtil;
import io.okkio.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    private ShoppingCartServices shoppingCartServices;

    private JwtTokenProvider jwtTokenProvider;

    private OrderServices orderServices;

    private ShipmentServices shipmentServices;

    private ReceiptServices receiptServices;

    private PaymentServices paymentServices;

    public OrderController(OrderServices orderServices, ShipmentServices shipmentServices,
                           ReceiptServices receiptServices, PaymentServices paymentServices,
                           ShoppingCartServices shoppingCartServices,
                           JwtTokenProvider jwtTokenProvider) {
        this.orderServices = orderServices;
        this.shipmentServices = shipmentServices;
        this.receiptServices = receiptServices;
        this.paymentServices = paymentServices;
        this.shoppingCartServices = shoppingCartServices;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER','OKKIO_ADMIN')")
    @PostMapping("/process-to-checkout")
    public ResponseEntity<ShoppingCartDto> processCheckout(@RequestHeader("Authorization") String token) {
        User user = jwtTokenProvider.getUserFromJWT(token);
        if (user == null) {
            log.warn("MESSAGE_USER_OR_ORDER_IS_NOT_EXISTED with user id: " + jwtTokenProvider.getUserIdFromBearerToken(token));
            return ResponseUtil.ok(Constants.MESSAGE_USER_OR_ORDER_IS_NOT_EXISTED, null);
        }
        List<ShoppingCart> shoppingCarts = shoppingCartServices.getAllShoppingCartByUserId(user.getId(), Constants.ACTIVATED_STATUS);
        if (shoppingCarts == null || shoppingCarts.isEmpty()) {
            log.warn("MESSAGE_ORDER_IS_NOT_EXISTED with user id: " + jwtTokenProvider.getUserIdFromBearerToken(token));
            return ResponseUtil.ok(Constants.MESSAGE_ORDER_IS_NOT_EXISTED, null);
        }
        Order result = orderServices.addOrderAndOrderItems(shoppingCarts, user.getId(), user.getEmail());
        if (result == null) {
            return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_FAILED, null);
        }
        // Waiting shopping cart after created order and order items.
        for (ShoppingCart cart: shoppingCarts) {
            shoppingCartServices.updateStatusShoppingCart(cart.getId(), Constants.WAITING_STATUS);
        }
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, shoppingCartServices.getShoppingCartByUser(shoppingCarts));
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER','OKKIO_ADMIN')")
    @PostMapping("/checkout")
    public synchronized ResponseEntity<ResponseCheckout> checkout(@RequestHeader("Authorization") String token,
                                                                  @RequestBody RequestCheckoutDto dto) {
        if (StringUtil.isEmpty(dto.getAddress()) || StringUtil.isEmpty(dto.getFullName())
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
        User user = jwtTokenProvider.getUserFromJWT(token);
        if (user == null) {
            log.warn("MESSAGE_USER_OR_ORDER_IS_NOT_EXISTED with user id: " + jwtTokenProvider.getUserIdFromBearerToken(token));
            return ResponseUtil.ok(Constants.MESSAGE_USER_OR_ORDER_IS_NOT_EXISTED, null);
        }
        String email = user.getEmail();
        // Get order by draft status
        List<Order> orders = orderServices.getOrderByUserIdAndOrderStatus(user.getId(), 4, Constants.ACTIVATED_STATUS);
        if (orders == null || orders.isEmpty()) {
            log.warn("Error getOrderByUserIdAndOrderStatus with user: " + email);
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        // Create Receipt with status waiting
        Long orderId = orders.get(0).getId();
        Receipt receipt = createdReceipt(orderId, dto.getPaymentMethod(), email);
        if (receipt == null) {
            log.warn("Error createdReceipt with order id: " + orderId);
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        Long receiptId = receipt.getId();
        // Create shipment.
        String address = dto.getAddress() + " - " + dto.getFullName() + " - " + dto.getPhone() + " - " + dto.getNote();
        Shipment shipment = createdShipment(address, dto.getDeliveryMethod(), orderId, receiptId, dto.getShippingTo());
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

    @PreAuthorize("hasAnyRole('OKKIO_USER','OKKIO_ADMIN')")
    @PutMapping("/checkout-process")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @RequestBody RequestProcessStatus dto) {
        if (dto.getReceiptId() == null) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        Receipt receipt = receiptServices.getReceiptById(dto.getReceiptId());
        if (receipt == null) {
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        User user = jwtTokenProvider.getUserFromJWT(token);
        if (user == null) {
            log.warn("MESSAGE_USER_OR_ORDER_IS_NOT_EXISTED with user id: " + jwtTokenProvider.getUserIdFromBearerToken(token));
            return ResponseUtil.ok(Constants.MESSAGE_USER_OR_ORDER_IS_NOT_EXISTED, null);
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
            OrderDto result = orderServices.updateAndGetOrder(receipt.getId(), receiptStatus, user.getEmail(), dto.getOrderId(), user.getId());
            return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, result);
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, null);
    }

    private boolean validatedReceiptStatus(String status) {
        return Constants.RECEIPT_PAID_STATUS.equals(status)
                || Constants.RECEIPT_ISSUED_STATUS.equals(status);
    }

    private boolean validatedPaymentMethod(String paymentMethod) {
        return Constants.PAYMENT_METHOD_COD.equals(paymentMethod)
                || Constants.PAYMENT_METHOD_VISA.equals(paymentMethod)
                || Constants.PAYMENT_METHOD_ATM.equals(paymentMethod)
                || Constants.PAYMENT_METHOD_MOMO.equals(paymentMethod);
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

    private Shipment createdShipment(String note, String type, Long orderId, Long receiptId, String shippingTo) {
        RequestShipmentDto dto = new RequestShipmentDto();
        dto.setDetail(note);
        dto.setOrderId(orderId);
        dto.setStatus(Constants.ACTIVATED_STATUS);
        dto.setType(type);
        dto.setReceiptId(receiptId);
        dto.setShippingTo(shippingTo);
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
