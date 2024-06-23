package io.okkio.dto.response.version2;

import io.okkio.domain.version2.ProductDetailV2;
import io.okkio.domain.version2.ProductV2;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class ResponseProductSlugDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String slug;
    private Long level;
    private String levelCode;
    Set<ProductDetailV2> productDetailV2s;
    List<ProductV2> productV2s;
}
