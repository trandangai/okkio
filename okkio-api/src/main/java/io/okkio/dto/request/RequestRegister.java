package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestRegister {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String socialId;
    private String accountType;
}
