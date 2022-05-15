package io.okkio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDtoResponse {
    private Long id;
    private String username;
    private String email;
    private String roleName;
}
