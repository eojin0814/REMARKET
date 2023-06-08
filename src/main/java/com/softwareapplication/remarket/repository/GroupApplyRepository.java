package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.GroupApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupApplyRepository extends JpaRepository<GroupApply, Long> {
    List<GroupApply> findAllByGroupId(Long groupId);
    GroupApply findGroupApplyById(Long id);
    GroupApply findGroupApplyByGroupIdAndUserId(Long groupId, Long UserId);
}
