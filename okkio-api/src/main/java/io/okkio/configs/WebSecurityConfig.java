package io.okkio.configs;

import io.okkio.security.JwtAuthenticationFilter;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.RoleServices;
import io.okkio.services.UserServices;
import io.okkio.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserServices customUserDetailsService;
    @Autowired
    private RoleServices roleServices;
    @Autowired
    private RedisUtil<String> redisUtil;

    public WebSecurityConfig() {
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/version").permitAll()
                .antMatchers("/configuration/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/api/auth/register").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/profile").permitAll()
                .antMatchers("/api/auth/refresh-token").permitAll()
                // Un-authorization
                .antMatchers("/api/auth/forget-password").permitAll()
                .antMatchers("/api/categories/get-all").permitAll()
                .antMatchers("/api/location/get-all").permitAll()
                .antMatchers("/api/location/get-by-id").permitAll()
                .antMatchers("/api/product/by-category-id").permitAll()
                .antMatchers("/api/product-detail/get-by-id").permitAll()
                .antMatchers("/api/product/get-by-id").permitAll()
                .antMatchers("/api/product/get-all").permitAll()
                .antMatchers("/api/product-detail/get-all").permitAll()
                .antMatchers("/api/utils/init").permitAll()
                .antMatchers("/api/utils/get-by-name").permitAll()
                .antMatchers("/api/contact/**").permitAll()
                .antMatchers("/api/discovery/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenProvider, customUserDetailsService,
                        roleServices, redisUtil))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration().applyPermitDefaultValues();
        corsConfig.addAllowedMethod(CorsConfiguration.ALL);
        if (!StringUtils.isEmpty(appProperties.getCorsExposedHeaders())) {
            for (String header : appProperties.getCorsExposedHeaders().split(",")) {
                corsConfig.addExposedHeader(header.trim());
            }
        }
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

}
