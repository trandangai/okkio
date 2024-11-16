package io.okkio.dto.response.version2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductDtoPagingResponse {
    private List<ResponseProductDto> content;
    private int totalPages;
    private Long totalElements;
    private boolean last;
    private int size;
    private int number;
    private int numberOfElements;
}
