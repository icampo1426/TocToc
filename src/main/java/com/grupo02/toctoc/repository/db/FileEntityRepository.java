package com.grupo02.toctoc.repository.db;

import com.grupo02.toctoc.models.Comment;
import com.grupo02.toctoc.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, UUID> {

}
