package io.okkio.mybatis;

import io.okkio.domain.User;
import io.okkio.dto.response.UserDtoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMybatis {
    List<User> getAll();
    UserDtoResponse getUserById(Long id);

    int updateInfoUser(@Param("ids") Long ids, @Param("status") String status,
                       @Param("email") String email, @Param("role") int role, @Param("address") String address,
                       @Param("phoneNumber") String phoneNumber, @Param("firstName") String firstName,
                       @Param("lastName") String lastName, @Param("fullName") String fullName,
                       @Param("username") String username, @Param("password") String password);
}
