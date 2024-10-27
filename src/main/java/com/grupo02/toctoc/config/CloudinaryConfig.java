package com.grupo02.toctoc.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(@Value("cloudinary.name") String name,
                                 @Value("cloudinary.api.key") String apiKey,
                                 @Value("cloudinary.api.secret") String apiSecret) {
        Map config = ObjectUtils.asMap(
                "cloud_name", name,
                "api_key", apiKey,
                "api_secret", apiSecret);
        return new Cloudinary(config);
    }
}
