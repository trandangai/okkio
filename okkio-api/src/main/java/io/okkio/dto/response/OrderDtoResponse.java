package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDtoResponse {
    private Long id;
    private String orderCode;
    private String orderStatus;
    private String description;
    private String email;
    private String shippingTo;
    private String address;
    private String phone;
    private String note;
    private String fullName;
    private BigDecimal shippingFee;
    private BigDecimal price;
    private BigDecimal total;
    List<OrderItemDtoResponse> orderItems;
}
