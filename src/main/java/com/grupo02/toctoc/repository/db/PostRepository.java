package com.grupo02.toctoc.repository.db;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    //Page<Post> findByTitleAuthorLocation(String title, String author, String location, Pageable pageable);
    List<Post> findByAuthorIdIn(List<UUID> authorIds);
    int countByAuthor(User author);
}

