package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderDtoPagingResponse {
    private List<OrderDtoResponse> content;
    private int totalPages;
    private Long totalElements;
    private boolean last;
    private int size;
    private int number;
    private int numberOfElements;
}
