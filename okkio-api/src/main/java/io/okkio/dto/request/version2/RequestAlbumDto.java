package io.okkio.dto.request.version2;

import io.okkio.dto.version2.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestAlbumDto extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private String status;
}