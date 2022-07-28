package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class RequestPaymentDto {
    private Long id;
    private BigDecimal amount;
    private String status;
    private String paymentMethod;
    private Long receiptId;
}