package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResponseCheckout implements Serializable {
    private Long receiptId;
    private Long orderId;
}
