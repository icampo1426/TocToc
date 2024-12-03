package com.grupo02.toctoc.repository.db;

import com.grupo02.toctoc.models.FileEntity;
import com.grupo02.toctoc.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeRepository  extends JpaRepository<Like, UUID> {
}
