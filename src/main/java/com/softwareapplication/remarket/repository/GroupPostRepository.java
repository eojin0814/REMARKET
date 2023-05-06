package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {

    List<GroupPost> findAll();

    GroupPost findGroupPostById(Long id);
}
