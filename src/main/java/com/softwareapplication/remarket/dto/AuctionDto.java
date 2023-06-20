package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuctionDto {
    public static String getUploadDirPath(String imageUrl) {
        return "/upload/" + imageUrl;
    }

    private Long auctionId;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    private String title;


    private int auctionPrice;//제시가
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;


    private String content;


    private String bidPrice; //낙찰가


    private String status;//낙찰 완료, /기한마감, /경매중


    private Image image;


    private Long userId;

    private String imgUrl;
    private MultipartFile file;


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
                .status("판매중")
                .image(image)
                .user(user)
                .build();
    }

    public AuctionDto(Auction auction) {
        this.auctionId=auction.getAuctionId();
        this.createdDate=auction.getCreatedDate();
        this.updatedDate=auction.getUpdatedDate();
        this.title=auction.getTitle();
        this.auctionPrice=auction.getAuctionPrice();
        this.dueDate=auction.getDueDate();
        this.content=auction.getContent();
        this.bidPrice=auction.getBidPrice();
        this.status=auction.getStatus();
        this.image=auction.getImage();
        this.userId=auction.getUser().getUserId();
        try {
            this.imgUrl = getUploadDirPath(auction.getImage().getUrl());
        }catch (Exception e ) {
            this.imgUrl = "";
        }
    }
}
