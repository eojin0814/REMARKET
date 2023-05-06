package com.softwareapplication.remarket.domain;

import com.softwareapplication.remarket.dto.GroupPostDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.Date;


@Builder
@Getter @Setter
@DynamicInsert
@Entity
@Table(name = "GroupPost")
@NoArgsConstructor
@AllArgsConstructor
public class GroupPost{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private Long id; //게시글 id

    @Column(nullable = false)
    private String title; //게시글 제목

    @Column(name="due_date", nullable = false)
    private LocalDateTime dueDate; //공동구매 마감일

    @Column(nullable = false)
    private String product; //물품명

    private String content; //기타사항

    @Column(name="num_people", nullable = false)
    private int numPeople; //목표 인원수

    @Column(nullable = false)
    private int price; //물품 1개당 가격

    private String  image; //이미지 첨부

    @Column(name = "user_id", nullable = false) //fk 공동구매 작성자(user_id)
    private Long userId;

    public GroupPostDto toDto(){
        GroupPostDto build = GroupPostDto.builder()
                .id(id)
                .title(title)
                .dueDate(dueDate)
                .product(product)
                .content(content)
                .numPeople(numPeople)
                .price(price)
                .image(image)
                .userId(userId)
                .build();
        return build;
    }


}