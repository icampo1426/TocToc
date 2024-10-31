package com.grupo02.toctoc.repository.db;

import com.grupo02.toctoc.models.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRelationshipRepository extends JpaRepository<UserRelationship, UUID> {
    List<UserRelationship> findByRequesterIdAndStatusOrReceiverIdAndStatus(UUID requesterId, UserRelationship.RelationshipStatus status1, UUID receiverId, UserRelationship.RelationshipStatus status2);
}
