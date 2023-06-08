package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.Community;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.CommunityDto;
import com.softwareapplication.remarket.repository.CommunityRepository;
import com.softwareapplication.remarket.repository.GroupPostRepository;
import com.softwareapplication.remarket.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService {
    private CommunityRepository communityRepository;
    private UserRepository userRepository;

    public CommunityService(CommunityRepository communityRepository){
        this.communityRepository = communityRepository;
    }

    @Transactional
    public Long saveCommunity(CommunityDto communityDto){
        return communityRepository.save(communityDto.toEntity()).getId();
    }

    @Transactional
    public CommunityDto findCommunity(Long id){
        Community community = communityRepository.findCommunityById(id);
        return community.toDto();
    }
    @Transactional
    public Long updateCommunity(CommunityDto communityDto){
        return communityRepository.save(communityDto.toEntity()).getId();
    }

    @Transactional
    public void deleteCommunity(Long id){
        communityRepository.deleteById(id);
    }

    @Transactional
    public List<CommunityDto> getCommunityList(){
        List<Community> communityList = communityRepository.findAll();
        List<CommunityDto> communityDtoList = new ArrayList<>();
        for(Community community : communityList){
            CommunityDto communityDto = CommunityDto.builder()
                    .id(community.getId())
                    .title(community.getTitle())
                    .created(community.getCreated())
                    .updated(community.getUpdated())
                    .contentCommunity(community.getContentCommunity())
                    .image(community.getImage())
                    .user(community.getUser())
                    .build();
            communityDtoList.add(communityDto);
        }
        return communityDtoList;
    }

}