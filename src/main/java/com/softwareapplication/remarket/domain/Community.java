package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@DynamicInsert
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "community_id")
    private Long id; //게시글 id

    @Column(nullable = false)
    private String title; //게시글 제목

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created; //게시글 최초 작성

    @LastModifiedDate
    @Column
    private LocalDateTime updated; //게시글 수정 날짜

    @Column(nullable = false)
    private String contentCommunity; //게시글 내용

    @OneToOne
    @JoinColumn(name="image_id")
    private Image image; //이미지 첨부

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc") // 댓글 정렬
    private List<CommunityComment> communityComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
