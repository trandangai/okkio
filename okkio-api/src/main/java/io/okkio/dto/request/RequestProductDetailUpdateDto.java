package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class RequestProductDetailUpdateDto {
    private Long id;
    private String name;
    private String status;
    private String description;
    private String headerImages;
    private String footerImages;
}