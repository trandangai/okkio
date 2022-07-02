package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseCategoryDto {
    private Long id;
    private String name;
    private String status;
}
