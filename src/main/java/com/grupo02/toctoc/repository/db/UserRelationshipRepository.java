package com.grupo02.toctoc.repository.db;

import com.grupo02.toctoc.models.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserRelationshipRepository extends JpaRepository<UserRelationship, UUID> {
    List<UserRelationship> findByRequesterIdAndStatusOrReceiverIdAndStatus(UUID requesterId, UserRelationship.RelationshipStatus status1, UUID receiverId, UserRelationship.RelationshipStatus status2);

    List<UserRelationship> findByReceiverIdAndStatus(UUID receiverId, UserRelationship.RelationshipStatus status);

    List<UserRelationship> findByRequesterIdAndStatus(UUID requesterId, UserRelationship.RelationshipStatus status);

    Optional<UserRelationship> findByReceiverIdAndRequesterIdAndStatus(UUID receiverId, UUID requesterId, UserRelationship.RelationshipStatus status);
}
