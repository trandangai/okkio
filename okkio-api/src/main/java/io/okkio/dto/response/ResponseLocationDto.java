package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseLocationDto {
    private Long id;
    private String name;
    private String status;
    private String address;
    private String phone;
}