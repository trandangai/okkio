package io.okkio.security;

import io.okkio.domain.Role;
import io.okkio.domain.User;
import io.okkio.services.RoleServices;
import io.okkio.services.UserServices;
import io.okkio.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider tokenProvider;

    private final UserServices customUserDetailsService;

    private final RoleServices roleServices;

    private final RedisUtil<String> redisUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                                   UserServices customUserDetailsService, RoleServices roleServices,
                                   RedisUtil<String> redisUtil) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
        this.roleServices = roleServices;
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(httpServletRequest);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                User userDetails = customUserDetailsService.findUserById(userId);
                if(userDetails != null) {
                    Role role = roleServices.get(userDetails.getRoleId());
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, getAuthorities(role.getRoleName()));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            log.error("failed on set user authentication" + ex.getMessage());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
