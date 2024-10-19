package com.grupo02.toctoc.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.grupo02.toctoc.DTOs.PostCreate;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.repository.UserRepository;
import com.grupo02.toctoc.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    public Post createPost(PostCreate postCreate) throws IOException {
        Post post = new Post();
        post.setTitle(postCreate.getTitle());
        post.setContent(postCreate.getContent());

        // Buscar al autor en la base de datos
        Optional<User> authorOpt = userRepository.findById(postCreate.getAuthorId());
        if (authorOpt.isPresent()) {
            post.setAuthor(authorOpt.get());
        } else {
            throw new IllegalArgumentException("Autor no encontrado");
        }

        // Subir imagen y video si existen
        if (postCreate.getImage() != null && !postCreate.getImage().isEmpty()) {
            String imageUrl = uploadFile(postCreate.getImage());
            post.setImageUrl(imageUrl);
        }

        if (postCreate.getVideo() != null && !postCreate.getVideo().isEmpty()) {
            String videoUrl = uploadFile(postCreate.getVideo());
            post.setVideoUrl(videoUrl);
        }

        // Guardar el post en la base de datos
        return postRepository.save(post);
    }
}



