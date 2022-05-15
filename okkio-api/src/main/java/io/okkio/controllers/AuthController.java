package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.RefreshToken;
import io.okkio.domain.Role;
import io.okkio.domain.User;
import io.okkio.dto.TokenDto;
import io.okkio.dto.UserDto;
import io.okkio.dto.request.RequestLogin;
import io.okkio.dto.request.RequestProfile;
import io.okkio.dto.request.RequestRegister;
import io.okkio.dto.request.RequestTokenRefresh;
import io.okkio.dto.response.ResponseProfile;
import io.okkio.dto.response.ResponseRegister;
import io.okkio.dto.response.ResponseTokenRefresh;
import io.okkio.services.RoleServices;
import io.okkio.services.TokenServices;
import io.okkio.services.UserServices;
import io.okkio.util.ResponseUtil;
import io.okkio.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private RoleServices roleServices;

    @Autowired
    private TokenServices tokenServices;

    @PostMapping("/login")
    public ResponseEntity<?> insert(@RequestBody RequestLogin dto) {
        if (StringUtils.isEmpty(dto.getEmail())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getPassword())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        User user = userServices.findUserByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (user == null) {
            return ResponseUtil.ok(Constants.MESSAGE_USER_IS_NOT_EXISTED, null);
        }
        TokenDto result = tokenServices.getTokenInfo(user.getId(), null);
        List<TokenDto> results = new ArrayList<>();
        results.add(result);
        return ResponseUtil.ok(Constants.MESSAGE_LOGIN_SUCCESS, results);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RequestRegister dto) {
        if (StringUtils.isEmpty(dto.getEmail())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getPassword())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getRePassword())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (!dto.getPassword().equals(dto.getRePassword())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        boolean isExisted = userServices.findUserByEmail(dto.getEmail());
        if (isExisted) {
            return ResponseUtil.ok(Constants.MESSAGE_USER_IS_EXISTED, null);
        }
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setEmail(dto.getEmail());
        userDtoTemp.setPassword(dto.getPassword());

        UserDto userDto = userServices.insert(userDtoTemp);
        ResponseRegister result = new ResponseRegister();
        if (userDto == null) {
            throw new RuntimeException();
        } else {
            result.setEmail(userDto.getEmail());
            result.setId(userDto.getId());
            Role role = roleServices.get(userDto.getRoleId());
            if (role != null) {
                result.setRoleName(role.getRoleName());
            }
        }
        List<ResponseRegister> results = new ArrayList<>();
        results.add(result);
        return ResponseUtil.ok(Constants.MESSAGE_REGISTER_SUCCESS, results);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestBody RequestProfile dto) {
        ResponseProfile result = new ResponseProfile();
        if (StringUtils.isEmpty(dto.getEmail())) {
            // Get guest profile
            if (StringUtils.isEmpty(dto.getUsername())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            } else {
                // Return token without email
                UserDto userTemp = userServices.getUserByUsername(dto.getUsername());
                if (userTemp == null) {
                    return ResponseUtil.ok(Constants.MESSAGE_USER_IS_NOT_EXISTED, null);
                } else {
                    TokenDto tokenDto = tokenServices.getTokenInfo(userTemp.getId(), null);
                    if (tokenDto == null) {
                        return ResponseUtil.ok(Constants.MESSAGE_USER_IS_NOT_EXISTED, null);
                    } else {
                        result.setTokenDto(tokenDto);
                        result.setEmail(userTemp.getEmail());
                        result.setUsername(userTemp.getUsername());
                    }
                }
            }
        } else {
            // Return token within email
            if (StringUtil.isEmpty(dto.getPassword())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
            User userTemp = userServices.findUserByEmailAndPassword(dto.getEmail(), dto.getPassword());
            if (userTemp == null) {
                return ResponseUtil.ok(Constants.MESSAGE_USER_IS_NOT_EXISTED, null);
            }
            TokenDto tokenDto = tokenServices.getTokenInfo(userTemp.getId(), null);
            if (tokenDto == null) {
                return ResponseUtil.ok(Constants.MESSAGE_USER_IS_NOT_EXISTED, null);
            } else {
                result.setTokenDto(tokenDto);
                result.setEmail(userTemp.getEmail());
                result.setUsername(userTemp.getUsername());
            }
        }
        List<ResponseProfile> results = new ArrayList<>();
        results.add(result);
        return ResponseUtil.ok(Constants.MESSAGE_PROFILE_SUCCESS, results);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RequestTokenRefresh request) {
        if (StringUtils.isEmpty(request.getRefreshToken())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        RefreshToken refreshToken = tokenServices.findByToken(request.getRefreshToken());
        if (refreshToken == null) {
            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_NOT_EXISTED, null);
        }
        if (!tokenServices.verifyExpiration(refreshToken)) {
            return ResponseUtil.ok(Constants.MESSAGE_TOKEN_EXPIRED, null);
        }
        // TODO handle update expire refresh token
        TokenDto dto = tokenServices.getTokenInfo(refreshToken.getUserId(), refreshToken);
        ResponseTokenRefresh result = new ResponseTokenRefresh();
        result.setRefreshToken(dto.getRefreshToken());
        result.setAccessToken(dto.getAccessToken());
        List<ResponseTokenRefresh> results = new ArrayList<>();
        results.add(result);
        return ResponseUtil.ok(Constants.MESSAGE_REFRESH_TOKEN_SUCCESS, results);
    }
}