package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    CommunityComment findCommunityCommentById(Long id);
}
