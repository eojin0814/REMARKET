package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter @Setter
@DynamicInsert
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GroupPost{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private Long id; //게시글 id

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created; //게시글 최초 작성

    @LastModifiedDate
    @Column
    private LocalDateTime updated; //게시글 수정 날짜

    @Column(nullable = false)
    private String title; //게시글 제목

    @Column(name="due_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate; //공동구매 마감일

    @Column(nullable = false)
    private String product; //물품명

    private String content; //기타사항

    @Column(name="num_people", nullable = false)
    private int numPeople; //목표 인원수

    @Column(nullable = false)
    private int price; //물품 1개당 가격

    @OneToOne
    @JoinColumn(name="img_id")
    private Image  image; //이미지 첨부

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc") // 댓글 정렬
    private List<GroupComment> groupComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String status;

    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? "신청 중" : this.status;
    }
    public void updateGroupPost(LocalDateTime updated, String title, LocalDateTime dueDate, String product, String content, int numPeople, int price){
        this.updated = updated;
        this.title = title;
        this.dueDate = dueDate;
        this.product = product;
        this.content = content;
        this.numPeople = numPeople;
        this.price = price;
    }
    public void updatePostImg(Image image) {
        this.image = image;
    }

    public void updatePostStatus(String status){
        this.status = status;
    }
}