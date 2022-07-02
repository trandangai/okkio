package io.okkio.dto.response;

import io.okkio.domain.Product;
import io.okkio.domain.ProductDetail;
import io.okkio.dto.ProductDetailDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseProducts {
    private Product product;
    private ResponseProductDetailCategoryDto productDetail;
}
