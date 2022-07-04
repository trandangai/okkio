package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestLocationDto {
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
}