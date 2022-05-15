package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestProfile {
    private String username;
    private String password;
    private String email;
}
