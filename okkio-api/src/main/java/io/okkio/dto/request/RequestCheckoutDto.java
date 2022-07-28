package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class RequestCheckoutDto {
    private String shippingTo;
    private String address;
    private String note;
    private String fullName;
    private String email;
    private String phone;
    private String deliveryMethod;
    private String paymentMethod;
    private BigDecimal total;
}