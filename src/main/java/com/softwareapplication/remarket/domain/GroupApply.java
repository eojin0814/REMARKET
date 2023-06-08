package com.softwareapplication.remarket.domain;

import com.softwareapplication.remarket.dto.GroupApplyDto;
import com.softwareapplication.remarket.dto.GroupPostDto;
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

    @Column(name = "group_id", nullable = false)
    private Long groupId; //공동구매 게시글 id

    @Column(name = "user_id", nullable = false)
    private Long userId; //구매자(신청자) id

    public GroupApplyDto toDto(){
        GroupApplyDto build = GroupApplyDto.builder()
                .id(id)
                .name(name)
                .address(address)
                .count(count)
                .phone(phone)
                .groupId(groupId)
                .userId(userId)
                .build();
        return build;
    }

}