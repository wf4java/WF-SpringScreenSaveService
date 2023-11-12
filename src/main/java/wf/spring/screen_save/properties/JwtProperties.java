package wf.spring.screen_save.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.security.jwt")
@Getter
@Setter
public class JwtProperties {

    private String secretKey = "secret_key_EAqdocOSZUis00ttNGwy3kSta0YMz6Gy";

    private Integer expirationDays = 7;

    private String issuer = "spring-app";

}
