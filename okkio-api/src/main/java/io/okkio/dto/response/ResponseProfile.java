package io.okkio.dto.response;

import io.okkio.dto.TokenDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResponseProfile implements Serializable {
    private TokenDto tokenDto;
    private String username;
    private String email;
}
