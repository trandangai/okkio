package io.okkio.services;


import io.okkio.domain.User;
import io.okkio.dto.UserDto;

public interface UserServices {
    UserDto insert(UserDto dto);
    boolean findUserByUsername(String username);
    boolean findUserByEmail(String email);
    User getUserByEmail(String email);
    UserDto get(Long id);
    User findUserById(Long id);
    User findUserByEmailAndPassword(String email, String password);
    UserDto getUserByUsername(String username);
    boolean resetPassword(String email);
}
