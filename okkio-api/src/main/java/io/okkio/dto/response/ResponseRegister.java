package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseRegister {
    private Long id;
    private String email;
    private String roleName;
}
