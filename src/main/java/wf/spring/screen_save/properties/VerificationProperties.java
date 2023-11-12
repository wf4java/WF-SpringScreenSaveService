package wf.spring.screen_save.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.security.verification")
@Getter
@Setter
public class VerificationProperties {

    private Integer codeExpiredMinutes = 3;


}
