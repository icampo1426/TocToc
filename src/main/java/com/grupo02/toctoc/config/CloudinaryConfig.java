package com.grupo02.toctoc.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

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

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(50));
        factory.setMaxRequestSize(DataSize.ofMegabytes(50));
        return factory.createMultipartConfig();
    }
}
