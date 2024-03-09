package io.okkio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class TokenDto implements Serializable {
    private Long expiresIn;
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String roleName;
}
