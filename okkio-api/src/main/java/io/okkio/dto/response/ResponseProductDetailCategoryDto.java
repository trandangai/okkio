package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class ResponseProductDetailCategoryDto {
    private Long id;
    private String name;
    private String description;
    private List<String> headerImages;
    private String footerImages;
}
