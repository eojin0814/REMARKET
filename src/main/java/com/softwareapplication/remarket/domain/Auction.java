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
@Table(name="Auction")
@EntityListeners(AuditingEntityListener.class)
public class Auction {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "auction_Id")
    private Long auctionId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedDate;

    @Column(name = "title")
    private String title;

    @Column(name = "auction_price")
    private Long auctionPrice;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "content")
    private String content;

    @Column(name = "bid_price")
    private String bidPrice;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
