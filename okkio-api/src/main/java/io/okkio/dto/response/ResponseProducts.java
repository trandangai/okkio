package io.okkio.dto.response;

import io.okkio.domain.Product;
import io.okkio.domain.ProductDetail;
import io.okkio.dto.ProductDetailDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseProducts {
    private Product product;
    private List<ResponseProductDetailCategoryDto> productDetail;
}
