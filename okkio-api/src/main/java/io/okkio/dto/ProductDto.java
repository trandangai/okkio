package io.okkio.dto;

import io.okkio.domain.Product;
import io.okkio.domain.ProductDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private String status;
    private String description;
    private Long productDetailId;
    private Long categoryId;
    private ProductDetail productDetails;
}
