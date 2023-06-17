package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.Auction;
import com.softwareapplication.remarket.domain.TenderPrice;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.AuctionDto;
import com.softwareapplication.remarket.repository.SchedulerRepository;
import com.softwareapplication.remarket.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SchedulerService {

    @Autowired
    private final SchedulerRepository schedulerRepository;

    private final UserRepository userRepository;

    @Transactional
    public Long save(AuctionDto auctionDto) {
        User user = userRepository.findById(auctionDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + auctionDto.getUserId()));


        return schedulerRepository.save(auctionDto.toEntity(user)).getAuctionId();
    }
    public List<TenderPrice> findByAuctionList(Long auctionId){
        return schedulerRepository.findAuctionsByAuctionId(auctionId);

    }
    public List<Auction> findByList(){
        return schedulerRepository.findAll();

    }
    public Auction findById(Long id){
        return schedulerRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id:"+id));

    }
}
