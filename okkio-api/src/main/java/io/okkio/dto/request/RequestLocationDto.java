package io.okkio.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.okkio.dto.version2.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestLocationDto extends BaseDto {
    private Long id;
    private String name;
    private String status;
    private String address;
    private String phone;
    private String title;
    private String description;
    private String openTime;
    private String openDay;
    private String conceptStore;
    private List<String> images;
    private String parkingLot;
    private String store;
}