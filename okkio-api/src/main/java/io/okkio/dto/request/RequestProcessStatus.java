package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestProcessStatus {
    private String statusReceipt;
    private String statusOrder;
    private String statusOrderItem;
    private Long receiptId;
    private Long orderId;
}
