package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Builder
@Getter @Setter
@DynamicInsert
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private Long id; //게시글 id

    @Column(nullable = false)
    private String title; //게시글 제목

    @Column(name="due_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate; //공동구매 마감일

    @Column(nullable = false)
    private String product; //물품명

    private String content; //기타사항

    @Column(name="num_people", nullable = false)
    private Integer numPeople; //목표 인원수

    @Column(nullable = false)
    private Integer price; //물품 1개당 가격

    private String  image; //이미지 첨부

    @Column(name = "user_id") //fk 공동구매 작성자(user_id)
    private Long userId;
}