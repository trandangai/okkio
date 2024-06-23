package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestShoppingCartDto {
    private Long id;
    private String status;
    private Long productDetailId;
    private String grind;
    private String size;
    private int quantity;
}