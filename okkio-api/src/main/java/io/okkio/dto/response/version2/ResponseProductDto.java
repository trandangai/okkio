package io.okkio.dto.response.version2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseProductDto {
    private Long id;
    private String name;
    private String status;
    @JsonIgnore
    private String description;
    private String slug;
    private Long level;
    private List<ResponseProductDto> products;
}
