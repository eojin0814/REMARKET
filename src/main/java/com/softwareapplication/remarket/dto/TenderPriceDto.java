package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.Auction;
import com.softwareapplication.remarket.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TenderPriceDto {

    private Long tenderPriceId;

    private Date createdDate;

    private Long applicationPrice;

    private Long userId;

    private Long auctionId;

}
