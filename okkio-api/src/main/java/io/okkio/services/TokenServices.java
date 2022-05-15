package io.okkio.services;


import io.okkio.domain.RefreshToken;
import io.okkio.dto.TokenDto;

public interface TokenServices {
    TokenDto getTokenInfo(Long userId, RefreshToken refreshToken);
    RefreshToken findByToken(String token);
    RefreshToken createRefreshToken(Long userId);
    boolean verifyExpiration(RefreshToken token);
}