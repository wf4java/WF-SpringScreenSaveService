package wf.spring.screen_save.configs;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import wf.spring.screen_save.properties.ImageStorageProperties;
import wf.spring.screen_save.properties.JwtProperties;
import wf.spring.screen_save.properties.VerificationProperties;


@Configuration
@EnableConfigurationProperties({JwtProperties.class, ImageStorageProperties.class, VerificationProperties.class})
@EnableScheduling
@RequiredArgsConstructor
public class WebApplicationConfig {


    @Bean
    public Validator defaultValidator() {
        return new LocalValidatorFactoryBean();
    }


}
