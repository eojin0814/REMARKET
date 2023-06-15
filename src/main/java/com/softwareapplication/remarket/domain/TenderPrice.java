package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="TenderPrice")
@EntityListeners(AuditingEntityListener.class)
public class TenderPrice {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "tender_price_id")
    private Long tenderPriceId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    @Column(name = "application_auction_price")
    private Long applicationauctionPrice;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

}
