package io.okkio.security;

import io.okkio.common.Constants;
import io.okkio.domain.User;
import io.okkio.services.UserServices;
import io.okkio.util.RedisUtil;
import io.okkio.util.StringUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtTokenProvider {

    private final String JWT_SECRET = "aitdcoinf";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserServices userServices;

    public String generateToken(Long userId, Long expired, boolean isExpired) {
        Date now = new Date();
        if (!isExpired) {
            // Plus 1 year
            expired = 31536000000L;
        }
        Date expiryDate = new Date(now.getTime() + expired);
        String key = (String) redisUtil.getValue(Constants.REDIS_JWT_TOKEN + userId);
        String result;
        if (key == null || StringUtil.isEmpty(key)) {
            result = Jwts.builder()
                    .setSubject(Long.toString(userId))
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                    .compact();
            redisUtil.putValue(Constants.REDIS_JWT_TOKEN + userId, result);
            if (isExpired) {
                redisUtil.setExpire(Constants.REDIS_JWT_TOKEN + userId, 13, TimeUnit.MINUTES);
            } else {
                redisUtil.setExpire(Constants.REDIS_JWT_TOKEN + userId, 30, TimeUnit.DAYS);
            }
        } else {
            result = key;
        }
        return result;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public User getUserFromJWT(String token) {
        Long userId = getUserIdFromJWT(token);
        return userServices.findUserById(userId);
    }

    public Long getUserIdFromBearerToken(String bearerToken) {
        String token;
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        } else {
            log.warn("Not bearer token: " + bearerToken);
            return null;
        }
        return getUserIdFromJWT(token);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token" + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token" + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty." + ex.getMessage());
        }
        return false;
    }

    public String getEmailFromToken(String authorization) {
        String token;
        try {
            if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
            } else {
                log.warn("Not bearer token: " + authorization);
                return null;
            }
            return getUserFromJWT(token).getEmail();
        } catch (Exception e) {
            log.error("Exception - getEmailFromToken with authorization: {}", authorization);
            e.printStackTrace();
        }
        return null;
    }
}
