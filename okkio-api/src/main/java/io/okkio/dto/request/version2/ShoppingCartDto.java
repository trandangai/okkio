package io.okkio.dto.request.version2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShoppingCartDto {
    private Long id;
    private String status;
    private Long productDetailId;
    private String grind;
    private String size;
    private int quantity;
    private String transaction;
}