package io.okkio.dto.response.version2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseProductDetailCategoryDto {
    private Long id;
    private String name;
    private String description;
    private List<String> productImages;
}
