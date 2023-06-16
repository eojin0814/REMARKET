package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.TenderPrice;
import com.softwareapplication.remarket.repository.SchedulerRepository;
import com.softwareapplication.remarket.repository.SecondHandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SchedulerService {

    @Autowired
    private final SchedulerRepository schedulerRepository;
    public List<TenderPrice> findByAuctionList(Long auctionId){
        return schedulerRepository.findAuctionsByAuctionId(auctionId);

    }
}
