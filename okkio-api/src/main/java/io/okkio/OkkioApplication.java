package io.okkio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "io.okkio.mybatis.**")
public class OkkioApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkkioApplication.class, args);
    }

}
