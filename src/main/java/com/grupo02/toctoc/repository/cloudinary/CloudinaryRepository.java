package com.grupo02.toctoc.repository.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryRepository {
    String savePhoto(String fileName, MultipartFile file );
    void delete();
}
