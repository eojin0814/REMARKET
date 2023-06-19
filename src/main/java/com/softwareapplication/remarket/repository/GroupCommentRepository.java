package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.GroupComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupCommentRepository extends JpaRepository<GroupComment, Long> {
    GroupComment findGroupCommentById(Long id);

}
