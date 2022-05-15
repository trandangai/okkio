package io.okkio.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NonNull
public class RequestTokenRefresh {
    private String refreshToken;
}
