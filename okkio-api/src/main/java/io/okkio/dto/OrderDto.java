package io.okkio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OrderDto {
    private Long id;
    private String orderCode;
    private String orderStatus;
    private String description;
//    private String status;
    private String email;
    private String shippingTo;
    private String address;
    private BigDecimal discount;
    private BigDecimal shippingFee;
    private BigDecimal price;
    private BigDecimal total;
    private ShoppingCartDto orderDetail;
}
