package wf.spring.screen_save.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.Collection;


@ConfigurationProperties("spring.application.storage.image")
@Getter
@Setter
public class ImageStorageProperties {

    private String path = "./storage";

    private Collection<String> extensions = Arrays.asList("jpg", "jpeg", "png");


}
