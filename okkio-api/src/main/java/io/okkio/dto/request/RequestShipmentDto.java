package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestShipmentDto {
    private Long id;
    private String detail;
    private String status;
    private String type;
    private Long receiptId;
    private Long orderId;
    private String shippingTo;
    private String address;
    private String paymentMethod;
}