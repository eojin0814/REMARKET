package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Builder
@Getter
@Setter
@DynamicInsert
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GroupApply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "groupApply_id")
    private Long id; //공동 구매 신청 id

    @Column(nullable = false)
    private String address; //주소

    @Column(nullable = false)
    private Integer count; //신청 개수

    @Column(nullable = false)
    private String phone; //전화번호

    @Column(name = "group_id")
    private Long groupId; //공동구매 게시글 id

    @Column(name = "user_id")
    private Long userId; //구매자(신청자) id

}