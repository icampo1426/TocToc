package com.grupo02.toctoc.services;

import com.grupo02.toctoc.models.DTOs.PostCreate;
import com.grupo02.toctoc.models.FileEntity;
import com.grupo02.toctoc.models.FileType;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.repository.cloudinary.CloudinaryRepository;
import com.grupo02.toctoc.repository.db.FileEntityRepository;
import com.grupo02.toctoc.repository.db.PostRepository;
import com.grupo02.toctoc.repository.db.UserRepository;
//import com.grupo02.toctoc.repository.db.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudinaryRepository cloudinaryRepository;

    @Autowired
    private FileEntityRepository fileEntityRepository;

    public List<Post> getPosts() {
        //PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        //Page<Post> page = postRepository.findByTitleAuthorLocation(title, author, location, pageRequest);

        //List<Post> posts = page.getContent();
        //Map<String, Object> response = new HashMap<>();
        //response.put("total", page.getTotalElements());
        //response.put("limit", limit);
        //response.put("offset", offset);
        //response.put("posts", posts);

        return postRepository.findAll();
    }

    public Post createPost(User user, PostCreate postCreate) {

        Post post = new Post();
        post.setTitle(postCreate.getTitle());
        post.setContent(postCreate.getContent());
        post.setAuthor(user);
        post.setLocation(postCreate.getLocation());
        post.setCreationDate(java.time.LocalDateTime.now().toString());

        return postRepository.save(post);
    }

    @Transactional
    public Post createPost(User user, PostCreate postCreate, List<MultipartFile> files) {

        Post post = new Post();
        post.setTitle(postCreate.getTitle());
        post.setContent(postCreate.getContent());
        post.setAuthor(user);
        post.setLocation(postCreate.getLocation());
        post.setCreationDate(java.time.LocalDateTime.now().toString());

        List<FileEntity> filesUpload = files.stream().map(f->{
            String url = cloudinaryRepository.savePhoto(user.getId().toString(), f);
            FileEntity filePost= new FileEntity();
            filePost.setName(user.getId().toString());
            filePost.setType(FileType.IMAGE);
            filePost.setUri(url);
            fileEntityRepository.save(filePost);
            return filePost;
        }).toList();

        post.setFile(filesUpload);

        postRepository.save(post);

        return post;
    }

}