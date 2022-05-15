package io.okkio.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.okkio.common.Constants;
import io.okkio.configs.AppProperties;
import io.okkio.domain.RefreshToken;
import io.okkio.dto.TokenDto;
import io.okkio.dto.UserDto;
import io.okkio.repository.RefreshTokenRepository;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.TokenServices;
import io.okkio.services.UserServices;
import io.okkio.util.RedisUtil;
import io.okkio.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * UserServicesImpl
 */
@Slf4j
@Service
public class TokenServicesImpl implements TokenServices {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private RedisUtil<String> redisUtil;

    @Autowired
    private UserServices userServices;

    private ObjectMapper objectMapper;

    @Override
    public TokenDto getTokenInfo(Long userId, RefreshToken refreshToken) {
        boolean isExpired = true;
        UserDto dto = userServices.get(userId);
        if (dto.getUsername() != null && dto.getUsername().equals("guest")) {
            isExpired = false;
        }
        String jwt = tokenProvider.generateToken(userId, appProperties.getExpiresIn(), isExpired);
        if (jwt == null) {
            log.warn("TokenServicesImpl: User ID is null or not existed! ");
            return null;
        }
        TokenDto result = new TokenDto();
        result.setExpiresIn(appProperties.getExpiresIn());
        result.setTokenType(Constants.TOKEN_TYPE);
        result.setAccessToken(jwt);
        if (refreshToken != null) {
            result.setRefreshToken(refreshToken.getToken());
        } else {
            RefreshToken token = tokenServices.createRefreshToken(userId);
            result.setRefreshToken(token.getToken());
        }
        return result;
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String key = redisUtil.getValue(Constants.REDIS_REFRESH_TOKEN + userId);
        if (key == null || StringUtil.isEmpty(key)) {
            refreshToken.setExpiryDate(Instant.now().plusMillis(appProperties.getRefreshTokenDurationMs()));
            refreshToken.setToken(UUID.randomUUID() + "-" + System.currentTimeMillis());
            refreshToken.setUserId(userId);
            try {
                redisUtil.putValue(Constants.REDIS_REFRESH_TOKEN + userId, objectMapper.writeValueAsString(refreshToken));
            } catch (JsonProcessingException e) {
                log.warn("JsonProcessingException - Can not convert Object to Json String!" + e.getMessage());
            }
            redisUtil.setExpire(Constants.REDIS_REFRESH_TOKEN + userId, 13, TimeUnit.MINUTES);
            refreshToken = refreshTokenRepository.save(refreshToken);
        } else {
            try {
                refreshToken = objectMapper.readValue(key, RefreshToken.class);
            } catch (JsonProcessingException e) {
                log.warn("JsonProcessingException - Can not convert String to Object! ");
            }
        }
        return refreshToken;
    }

    @Override
    public boolean verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) >= 0) {
            return true;
        }
        refreshTokenRepository.delete(token);
        return false;
    }
}