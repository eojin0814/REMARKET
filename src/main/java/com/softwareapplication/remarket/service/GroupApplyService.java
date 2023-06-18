package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.GroupApply;
import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.dto.GroupApplyDto;
import com.softwareapplication.remarket.repository.GroupApplyRepository;
import com.softwareapplication.remarket.repository.GroupPostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupApplyService {
    private GroupApplyRepository groupApplyRepository;
    private GroupPostRepository groupPostRepository;

    public GroupApplyService(GroupApplyRepository groupApplyRepository, GroupPostRepository groupPostRepository){
        this.groupApplyRepository = groupApplyRepository;
        this.groupPostRepository = groupPostRepository;
    }
    @Transactional
    public Long saveApply(GroupApplyDto groupApplyDto){
        if(groupApplyDto.getGroupPostId() != null) {
            GroupPost groupPost = groupPostRepository.findGroupPostById(groupApplyDto.getGroupPostId());
            groupApplyDto.setGroupPost(groupPost);
        }
        return groupApplyRepository.save(groupApplyDto.toEntity()).getId();
    }

    @Transactional
    public GroupApplyDto findApply(Long id){
        GroupApply groupApply = groupApplyRepository.findGroupApplyById(id);
        if(groupApply == null)
            return null;
        else {
            GroupApplyDto groupApplyDto = new GroupApplyDto(groupApply);
            return groupApplyDto;
        }
    }

    @Transactional
    public GroupApplyDto findUserApply(Long groupPostId, Long userId){
        GroupApply groupApply = groupApplyRepository.findGroupApplyByGroupPostIdAndUser_UserId(groupPostId, userId);
        if(groupApply == null)
            return null;
        else {
            GroupApplyDto groupApplyDto = new GroupApplyDto(groupApply);
            return groupApplyDto;
        }
    }

    @Transactional
    public void updateApply(GroupApplyDto groupApplyDto){
        GroupApply groupApply = groupApplyRepository.findGroupApplyById(groupApplyDto.getId());
        groupApply.updateGroupApply(groupApplyDto.getName(), groupApplyDto.getAddress(), groupApplyDto.getCount(), groupApplyDto.getPhone());
    }

    @Transactional
    public void deleteApply(Long id){
        groupApplyRepository.deleteById(id);
    }

    @Transactional
    public List<GroupApplyDto> getGroupApplyListByGroupId(Long groupPostId){
        List<GroupApply> groupApplyList = groupApplyRepository.findAllByGroupPostId(groupPostId);
        List<GroupApplyDto> groupApplyDtoList = new ArrayList<>();
        for(GroupApply groupApply : groupApplyList){
            GroupApplyDto groupApplyDto = new GroupApplyDto(groupApply);
            groupApplyDtoList.add(groupApplyDto);
        }
        return groupApplyDtoList;
    }

    @Transactional
    public long countGroupApplyList(Long groupPostId){
        long sum = 0;
        List<GroupApply> lists = groupApplyRepository.findAllByGroupPostId(groupPostId);
        for(GroupApply list:lists){
            sum += list.getCount();
        }
        return sum;
    }
}
