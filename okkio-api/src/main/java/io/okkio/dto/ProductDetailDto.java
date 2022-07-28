package io.okkio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductDetailDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String grind;
    private String size;
    private String subscription;
    private int quantity;
    private int roastLevel;
    private Object tastingNotes;
    private String readyToDrink;
    private String suitableFor;
    private Object shippingDelivery;
    private List<String> headerImages;
    private String footerImages;
    private Object suggestion;
    private BigDecimal price;
}
