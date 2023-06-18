package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.Community;
import com.softwareapplication.remarket.domain.CommunityComment;
import com.softwareapplication.remarket.dto.CommunityCommentDto;

import com.softwareapplication.remarket.repository.CommunityCommentRepository;
import com.softwareapplication.remarket.repository.CommunityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommunityCommentService {
    private CommunityCommentRepository communityCommentRepository;
    private CommunityRepository communityRepository;

    public CommunityCommentService(CommunityCommentRepository communityCommentRepository, CommunityRepository communityRepository){
        this.communityCommentRepository = communityCommentRepository;
        this.communityRepository = communityRepository;
    }

    @Transactional
    public Long saveCommunityComment(CommunityCommentDto.RequestDto communityCommentDto, Long communityId){
        CommunityComment communityComment = communityCommentDto.toEntity();
        //대댓글인 경우
        if(communityCommentDto.getParentId() != null) {
            CommunityComment parent = communityCommentRepository.findCommunityCommentById(communityCommentDto.getParentId());
            communityComment.updateParent(parent);
        }
        if(communityId != null){
            Community community = communityRepository.findCommunityById(communityId);
            communityComment.setCommunity(community);
        }
        return communityCommentRepository.save(communityComment).getId();
    }

    @Transactional
    public void updateCommunityComment(CommunityCommentDto.RequestDto communityCommentDto){
        CommunityComment communityComment = communityCommentRepository.findCommunityCommentById(communityCommentDto.getId());
        communityComment.updateCommentContent(communityCommentDto.getCommentContent());
    }

    @Transactional
    public void deleteCommunityComment(Long id){
        communityCommentRepository.deleteById(id);
    }



}
