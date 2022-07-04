package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.dto.UserDto;
import io.okkio.dto.response.UserDtoResponse;
import io.okkio.mybatis.UserMybatis;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.UserServices;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserServices userServices;

    private JwtTokenProvider jwtTokenProvider;

    private UserMybatis userMybatis;

    public UserController(UserServices userServices, JwtTokenProvider jwtTokenProvider, UserMybatis userMybatis) {
        this.userServices = userServices;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userMybatis = userMybatis;
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> insert(@RequestBody UserDto dto) {
        boolean isExistedByUser;
        boolean isExistedByEmail;
        if (StringUtils.isEmpty(dto.getUsername())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            isExistedByUser = userServices.findUserByUsername(dto.getUsername());
        }
        if (StringUtils.isEmpty(dto.getEmail())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            isExistedByEmail = userServices.findUserByEmail(dto.getEmail());
        }
        if (isExistedByUser || isExistedByEmail) {
            return ResponseUtil.ok(Constants.MESSAGE_USER_IS_EXISTED, null);
        }
        UserDto result = userServices.insert(dto);
        List<UserDto> results = new ArrayList<>();
        results.add(result);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_USER_SUCCESS, results);
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_GUEST', 'OKKIO_ADMIN')")
    @GetMapping
    public ResponseEntity<UserDto> get(@RequestHeader("Authorization") String token) {
        Long id = jwtTokenProvider.getUserIdFromBearerToken(token);
        if (id < 1) {
            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_NOT_EXISTED, null);
        }
        UserDtoResponse result = userMybatis.getUserById(id);
        if (result == null) {
            return ResponseUtil.notFound(Constants.MESSAGE_NOT_FOUND);
        }
        List<UserDtoResponse> results = new ArrayList<>();
        results.add(result);
        return ResponseUtil.ok(Constants.MESSAGE_GET_USER_SUCCESS, results);
    }

    // TODO update user information
}
