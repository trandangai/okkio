package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestUtilDto {
    private Long id;
    private String name;
    private String status;
    private String description;
    private int type;
    private String code;
}