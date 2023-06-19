package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.GroupComment;
import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.dto.GroupCommentDto;
import com.softwareapplication.remarket.repository.GroupCommentRepository;
import com.softwareapplication.remarket.repository.GroupPostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GroupCommentService {
    private GroupCommentRepository groupCommentRepository;
    private GroupPostRepository groupPostRepository;

    public GroupCommentService(GroupCommentRepository groupCommentRepository, GroupPostRepository groupPostRepository){
        this.groupCommentRepository = groupCommentRepository;
        this.groupPostRepository = groupPostRepository;
    }

    @Transactional
    public Long saveGroupComment(GroupCommentDto.RequestDto groupCommentDto, Long groupPostId){
        GroupComment groupComment = groupCommentDto.toEntity();
        //대댓글인 경우
        if(groupCommentDto.getParentId() != null) {
            GroupComment parent = groupCommentRepository.findGroupCommentById(groupCommentDto.getParentId());
            groupComment.updateParent(parent);
        }
        if(groupPostId != null){
            GroupPost groupPost = groupPostRepository.findGroupPostById(groupPostId);
            groupComment.setGroupPost(groupPost);
        }
        return groupCommentRepository.save(groupComment).getId();
    }

    @Transactional
    public void updateGroupComment(GroupCommentDto.RequestDto groupCommentDto){
        GroupComment groupComment = groupCommentRepository.findGroupCommentById(groupCommentDto.getId());
        groupComment.updateCommentContent(groupCommentDto.getCommentContent());
    }

    @Transactional
    public void deleteGroupComment(Long id){
        groupCommentRepository.deleteById(id);
    }



}
