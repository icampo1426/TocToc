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
    public Cloudinary cloudinaryInit(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "duveiypiy",
                "api_key", "288534564715758",
                "api_secret", "3U0DCsxHmHHtsuDDK12O4ki9IRQ",
                "secure", true));
    }
}
