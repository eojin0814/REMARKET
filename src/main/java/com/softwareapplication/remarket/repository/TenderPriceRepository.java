package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.Auction;
import com.softwareapplication.remarket.domain.TenderPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenderPriceRepository extends JpaRepository<TenderPrice,Long> {
    @Query(value = "SELECT * FROM TENDER_PRICE WHERE AUCTION_ID = ? ORDER BY CREATED_DATE DESC", nativeQuery = true)
    List<TenderPrice> findTenderPriceByauctionId(Long auction);
}
