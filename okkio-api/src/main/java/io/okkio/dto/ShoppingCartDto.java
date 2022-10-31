package io.okkio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class ShoppingCartDto {
    private BigDecimal total;
    private List<PDShoppingCartDto> shoppingCarts;
    private Long shoppingCartId;
}
