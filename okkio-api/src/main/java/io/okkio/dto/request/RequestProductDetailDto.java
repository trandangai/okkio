package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestProductDetailDto {
    private Long id;
    private String name;
    private String status;
    private String description;
    private String grind;
    private String size;
    private String subscription;
    private int quantity;
    private int roastLevel;
    private String tastingNotes;
    private String readyToDrink;
    private String suitableFor;
    private String shippingDelivery;
    private List<String> headerImages;
    private String footerImages;
    private String suggestion;
    private BigDecimal price;
}