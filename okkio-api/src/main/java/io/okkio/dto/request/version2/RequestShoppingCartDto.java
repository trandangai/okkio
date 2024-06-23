package io.okkio.dto.request.version2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class RequestShoppingCartDto {
    private Long productDetailId;
    private String grind;
    private String size;
    private int quantity;
    private BigDecimal price;
}