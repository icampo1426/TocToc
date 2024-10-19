package com.grupo02.toctoc;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dllot4c8t",
                "api_key", "799498632461572",
                "api_secret", "oOsIGoBVWPhQUS74GFOQpxvLxjc"
        );
        return new Cloudinary(config);
    }
}
