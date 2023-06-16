package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.Auction;
import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.TenderPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SchedulerRepository extends JpaRepository<Auction,Long> {
    List<TenderPrice> findAuctionsByAuctionId(Long auctionId);
}
