package io.okkio.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class AppProperties {
	private String projectVersion;
	private String corsExposedHeaders;
	private Long expiresIn;
	private String secret;
	private Long refreshTokenDurationMs;
	private int redisPort;
	private String redisHost;
}