package io.okkio.dto;

import io.okkio.dto.response.ResponseLocationDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ContactDto {
    private String title;
    private String address;
    private String phone;
    private String email;
    private String facebook;
    private String instagram;
    private List<ResponseLocationDto> locations;
}