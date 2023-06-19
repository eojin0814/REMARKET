package com.softwareapplication.remarket.domain;


import jakarta.persistence.*;
import lombok.*;
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
@Table(name="SecondHand")
@EntityListeners(AuditingEntityListener.class)
public class SecondHand {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "second_hand_id")
    private Long secondHandId;

    @Column
    private String title;

    @Column
    private Long price;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column
    private LocalDateTime updatedDate;
    @Column
    private String content;

    @Column(columnDefinition = "VARCHAR(255) default '판매중'")
    private String status;

    @OneToOne
    @JoinColumn(name="img_id")
    private Image  image; //이미지 첨부

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String title, Image image, String content){
        this.title=title;
        this.image=image;
        this.content=content;
    }

}
//ㄷㅏ음주까지 엔티티 서비스 컨트롤러 할 수 있는 곳까지, 엔티티 다해오기
// 다음주에는 시스템 설계서 제출하는걸ㄹ로 ppt 만들기