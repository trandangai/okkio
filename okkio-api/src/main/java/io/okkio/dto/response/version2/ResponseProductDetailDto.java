package io.okkio.dto.response.version2;

import io.okkio.domain.version2.ProductDetailV2;
import io.okkio.dto.version2.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ResponseProductDetailDto extends BaseDto {
    private Long id;
    private String name;
    private String shortName;
    private String description;
    private String status;
    // WHOLE BEANS / ESPRESSO MACHINE / POUR OVER / PHIN / OTHERS
    private String grind;
    private String size;
    // lightest, medium, darknest
    private String flavorNote;
    // 6 levels
    private String roastLevel;
    private String altitude;
    private String varietal;
    private String processing;
    private int material;
    private List<String> productImages;
    private List<ProductDetailV2> suggestionProducts = new ArrayList<>();
    private Long productId;
    private BigDecimal price;
}