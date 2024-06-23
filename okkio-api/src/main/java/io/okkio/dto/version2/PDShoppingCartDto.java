package io.okkio.dto.version2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class PDShoppingCartDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String grind;
    private String size;
    private String subscription;
    private int quantity;
    private List<String> productImages;
    private BigDecimal price;
    private Long shoppingCartId;
}
