package com.softwareapplication.remarket.repository;


import com.softwareapplication.remarket.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findAll();
    Community findCommunityById(Long id);
}
