package io.okkio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private int roleId;
}