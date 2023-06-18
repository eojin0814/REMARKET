package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Builder
@Getter
@Setter
@DynamicInsert
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class GroupApply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "groupApply_id")
    private Long id; //공동 구매 신청 id
    
    @Column(nullable = false)
    private String name; //이름
    
    @Column(nullable = false)
    private String address; //주소

    @Column(nullable = false)
    private int count; //신청 개수

    @Column(nullable = false)
    private String phone; //전화번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupPost groupPost; //공동구매 게시글 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //구매자(신청자)

    public void updateGroupApply(String name, String address, int count, String phone){
        this.name = name;
        this.address = address;
        this.count = count;
        this.phone = phone;
    }

}