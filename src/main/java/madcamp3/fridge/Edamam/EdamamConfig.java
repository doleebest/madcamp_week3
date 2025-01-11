package madcamp3.fridge.Edamam;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "edamam")
@Getter
@Setter
public class EdamamConfig {
    private String appId;
    private String appKey;
    private String baseUrl = "https://api.edamam.com/api/nutrition-data";
}