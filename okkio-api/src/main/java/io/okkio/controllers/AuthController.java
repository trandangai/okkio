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
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (StringUtils.isEmpty(dto.getFirstName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getLastName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        UserDto userDtoTemp = new UserDto();
        if (StringUtils.isEmpty(dto.getAccountType())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            String accountType = dto.getAccountType().toUpperCase();
            if ("FACEBOOK".equals(accountType)
                    || "GOOGLE".equals(accountType)
                    || "LOCAL".equals(accountType)) {
                userDtoTemp.setAccountType(accountType);
            } else {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        boolean isExisted = userServices.findUserByEmail(dto.getEmail());
        if (isExisted) {
            return ResponseUtil.ok(Constants.MESSAGE_USER_IS_EXISTED, null);
        }
        userDtoTemp.setEmail(dto.getEmail());
        userDtoTemp.setPassword(dto.getPassword());
        userDtoTemp.setFirstName(dto.getFirstName());
        userDtoTemp.setLastName(dto.getLastName());
        if (!StringUtils.isEmpty(dto.getSocialId())) {
            userDtoTemp.setSocialId(dto.getSocialId());
        }

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

    @GetMapping("/forget-password")
    public ResponseEntity<?> refreshToken(@Param("email") String email) {
        if (StringUtils.isEmpty(email)) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        boolean isExisted = userServices.findUserByEmail(email);
        if (!isExisted) {
            return ResponseUtil.notFound(Constants.MESSAGE_EMAIL_IS_NOT_EXISTED);
        }
        if (userServices.resetPassword(email)) {
            return ResponseUtil.ok(Constants.MESSAGE_SEND_EMAIL_SUCCESS, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_SEND_EMAIL_FAILED, null);
    }
}