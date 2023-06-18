package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.repository.GroupPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RequiredArgsConstructor
@Service
public class GroupPostService {
    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private TaskScheduler scheduler;

    @Transactional
    public Long savePost(GroupPostDto groupPostDto){
        return groupPostRepository.save(groupPostDto.toEntity()).getId();
    }
    @Transactional
    public void testScheduler(@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date dueDate, GroupPostDto groupPostDto, Long groupPostId){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("스케줄 마감됨");
                groupPostDto.setStatus("마감");
                updateStatus(groupPostDto);
            }
        };

        groupPostDto.setId(groupPostId);
        scheduler.schedule(r, dueDate);
        System.out.println("스케줄 생성됨");
    }
    @Transactional
    public void updateStatus(GroupPostDto groupPostDto){
        GroupPost groupPost = groupPostRepository.findGroupPostById(groupPostDto.getId());
        groupPost.setStatus(groupPostDto.getStatus());
        groupPostRepository.save(groupPost);
    }

    @Transactional
    public GroupPostDto findPost(Long id){
        GroupPost groupPost = groupPostRepository.findGroupPostById(id);
        GroupPostDto groupPostDto = new GroupPostDto(groupPost);
        return groupPostDto;
    }

    @Transactional
    public void updatePost(GroupPostDto groupPostDto){
        GroupPost groupPost = groupPostRepository.findGroupPostById(groupPostDto.getId());
        if(!groupPostDto.getFile().getOriginalFilename().equals(""))
            groupPost.updatePostImg(groupPostDto.getImage());
        groupPost.updateGroupPost(groupPostDto.getUpdated(), groupPostDto.getTitle(), groupPostDto.getDueDate(), groupPostDto.getProduct(), groupPostDto.getContent(), groupPostDto.getNumPeople(), groupPostDto.getPrice());
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
            GroupPostDto groupPostDto = new GroupPostDto(groupPost);
            groupPostDtoList.add(groupPostDto);
        }
        return groupPostDtoList;
    }


}
