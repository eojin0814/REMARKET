package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="SecondHand")
public class SecondHand {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "second_hand_id")
    private Long secondHandId;

    @Column
    private String title;

    @Column
    private Long price;

    @Column
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    public void update(String title, String image, String content){
        this.title=title;
        this.image=image;
        this.content=content;
    }

}
//ㄷㅏ음주까지 엔티티 서비스 컨트롤러 할 수 있는 곳까지, 엔티티 다해오기
// 다음주에는 시스템 설계서 제출하는걸ㄹ로 ppt 만들기