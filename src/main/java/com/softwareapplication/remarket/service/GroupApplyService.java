package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.GroupApply;
import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.dto.GroupApplyDto;
import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.repository.GroupApplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupApplyService {
    private GroupApplyRepository groupApplyRepository;

    public GroupApplyService(GroupApplyRepository groupApplyRepository){
        this.groupApplyRepository = groupApplyRepository;
    }
    @Transactional
    public Long saveApply(GroupApplyDto groupApplyDto){
        return groupApplyRepository.save(groupApplyDto.toEntity()).getId();
    }

    @Transactional
    public GroupApplyDto findApply(Long id){
        GroupApply groupApply = groupApplyRepository.findGroupApplyById(id);
        return groupApply.toDto();
    }

    @Transactional
    public GroupApplyDto findUserApply(Long groupId, Long userId){
        GroupApply groupApply = groupApplyRepository.findGroupApplyByGroupIdAndUserId(groupId, userId);
        if(groupApply == null)
            return null;
        else
            return groupApply.toDto();
    }

    @Transactional
    public Long updateApply(GroupApplyDto groupApplyDto){
        return groupApplyRepository.save(groupApplyDto.toEntity()).getId();
    }

    @Transactional
    public void deleteApply(Long id){
        groupApplyRepository.deleteById(id);
    }

    @Transactional
    public List<GroupApplyDto> getGroupApplyListByGroupId(Long groupId){
        List<GroupApply> groupApplyList = groupApplyRepository.findAllByGroupId(groupId);
        List<GroupApplyDto> groupApplyDtoList = new ArrayList<>();
        for(GroupApply groupApply : groupApplyList){
            GroupApplyDto groupApplyDto = GroupApplyDto.builder()
                    .id(groupApply.getId())
                    .name(groupApply.getName())
                    .address(groupApply.getAddress())
                    .count(groupApply.getCount())
                    .phone(groupApply.getPhone())
                    .groupId(groupApply.getGroupId())
                    .userId(groupApply.getUserId())
                    .build();
            groupApplyDtoList.add(groupApplyDto);
        }
        return groupApplyDtoList;
    }

}
