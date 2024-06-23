package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OrderItemDtoResponse {
    private Long id;
    private String orderStatus;
    private Long productDetailId;
    private Long orderId;
    private BigDecimal price;
    private String grind;
    private String size;
    private int quantity;
}
