package com.grupo02.toctoc.repository.cloudinary.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.grupo02.toctoc.repository.cloudinary.CloudinaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryRepositoryImpl implements CloudinaryRepository {

    private static final Logger log = LoggerFactory.getLogger(CloudinaryRepositoryImpl.class);
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String savePhoto(String fileName, MultipartFile file) {
        Map uploadResult = null;
/*
        log.info(file.getContentType());
        if(file.getContentType().contains("video")){
            Map params = ObjectUtils.asMap(
                    "public_id", "myfolder/mysubfolder/my_dog",
                    "overwrite", true,
                    "resource_type", "video"
            );
            try {
                uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
*/

        Map params = ObjectUtils.asMap(
                "public_id", "myfolder/mysubfolder/my_dog",
                "overwrite", true,
                "resource_type", "image"
        );

        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadResult.get("url").toString();
    }

    @Override
    public void delete() {

    }
}
