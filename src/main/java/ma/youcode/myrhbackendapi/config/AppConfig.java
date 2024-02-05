package ma.youcode.myrhbackendapi.config;

import com.cloudinary.Cloudinary;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.utils.Env;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", Env.get("CLOUD_NAME"));
        config.put("api_key", Env.get("CLOUD_API_KEY"));
        config.put("api_secret", Env.get("CLOUD_API_SECRET"));
        return new Cloudinary(config);
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
