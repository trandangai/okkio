package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestLogin {
    private String email;
    private String password;
}
