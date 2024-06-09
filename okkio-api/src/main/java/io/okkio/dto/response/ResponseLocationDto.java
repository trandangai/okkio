package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseLocationDto {
    private Long id;
    private String name;
    private String status;
    private String address;
    private String phone;
    private List<String> images;
    private String parkingLot;
    private String store;
}