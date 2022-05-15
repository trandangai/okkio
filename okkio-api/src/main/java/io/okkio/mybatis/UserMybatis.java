package io.okkio.mybatis;

import io.okkio.domain.User;
import io.okkio.dto.response.UserDtoResponse;

import java.util.List;

public interface UserMybatis {
    List<User> getAll();
    UserDtoResponse getUserById(Long id);
}
