package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuctionDto {

    private Long auctionId;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    private String title;


    private Long auctionPrice; //제시가

    private Date dueDate;


    private String content;


    private String bidPrice; //낙찰가


    private String status;//낙찰 완료, /기한마감, /경매중


    private Image image;


    private Long userId;


    private List<TenderPrice> tenderPriceList;

    public Auction toEntity(User user){
        return Auction.builder()
                .auctionId(auctionId)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .title(title)
                .auctionPrice(auctionPrice)
                .dueDate(dueDate)
                .content(content)
                .bidPrice(bidPrice)
                .status(status)
                .image(image)
                .user(user)
                .build();
    }
}
