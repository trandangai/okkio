package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestCategoryDto {
    private Long id;
    private String name;
    private String status;
    private int priority;
}