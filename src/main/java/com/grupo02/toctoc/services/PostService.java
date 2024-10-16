package com.grupo02.toctoc.services;

import com.grupo02.toctoc.DTOs.PostCreate;
//import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.repository.UserRepository;
//import com.grupo02.toctoc.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository UserRepository) {
        this.postRepository = postRepository;
        this.userRepository = UserRepository;
    }

    public Map<String, Object> getPosts(String title, String author, String location, Integer limit, Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        Page<Post> page = postRepository.findByTitleAuthorLocation(title, author, location, pageRequest);

        List<Post> posts = page.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("total", page.getTotalElements());
        response.put("limit", limit);
        response.put("offset", offset);
        response.put("posts", posts);

        return response;
    }
    public Post createPost(PostCreate postCreate) {
        Long authorId = postCreate.getAuthorId();
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author ID"));

        Post post = new Post();
        post.setTitle(postCreate.getTitle());
        post.setContent(postCreate.getContent());
        post.setAuthor(author);
        post.setLocation(postCreate.getLocation());
        post.setCreationDate(java.time.LocalDateTime.now().toString());

        return postRepository.save(post);
    }
}


 */
