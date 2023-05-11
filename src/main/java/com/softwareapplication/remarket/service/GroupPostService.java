package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.repository.GroupPostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupPostService {

    private GroupPostRepository groupPostRepository;

    public GroupPostService(GroupPostRepository groupPostRepository){
        this.groupPostRepository = groupPostRepository;
    }
    @Transactional
    public Long savePost(GroupPostDto groupPostDto){
        return groupPostRepository.save(groupPostDto.toEntity()).getId();
    }

    @Transactional
    public GroupPostDto findPost(Long id){
        GroupPost groupPost = groupPostRepository.findGroupPostById(id);
        return groupPost.toDto();
    }

    @Transactional
    public Long updatePost(GroupPostDto groupPostDto){
        return groupPostRepository.save(groupPostDto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id){
        groupPostRepository.deleteById(id);
    }

    @Transactional
    public List<GroupPostDto> getGroupPostList(){
        List<GroupPost> groupPostList = groupPostRepository.findAll();
        List<GroupPostDto> groupPostDtoList = new ArrayList<>();
        for(GroupPost groupPost : groupPostList){
            GroupPostDto groupPostDto = GroupPostDto.builder()
                    .id(groupPost.getId())
                    .title(groupPost.getTitle())
                    .dueDate(groupPost.getDueDate())
                    .product(groupPost.getProduct())
                    .content(groupPost.getContent())
                    .numPeople(groupPost.getNumPeople())
                    .price(groupPost.getPrice())
                    .image(groupPost.getImage())
                    .userId(groupPost.getUserId())
                    .build();
            groupPostDtoList.add(groupPostDto);
        }
        return groupPostDtoList;
    }
}
