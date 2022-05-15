package io.okkio.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JpaDatasourceConfig {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryataSource() {
        HikariDataSource ds = new HikariDataSource();
        return ds;
    }
}