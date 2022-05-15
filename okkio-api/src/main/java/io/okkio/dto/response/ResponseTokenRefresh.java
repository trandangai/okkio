package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResponseTokenRefresh implements Serializable {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
