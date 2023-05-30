package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SecondHandDto {
    private Long secondHandId;
    private String content;
    private Date createdDate;
    private String image;
    private Long price;
    private String title;

    private Long userId;

//    @Builder
//    public SecondHandDto(Long secondHandId, String title, Long price, String image, Date createdDate, String content, User user){
//        this.secondHandId=secondHandId;
//        this.title=title;
//        this.price=price;
//        this.image=image;
//        this.createdDate=createdDate;
//        this.content=content;
//        this.user=user;
//    }

    public SecondHand toEntity(User user){
        return SecondHand.builder()
                .secondHandId(secondHandId)
                .title(title)
                .price(price)
                .image(image)
                .createdDate(createdDate)
                .content(content)
                .user(user)
                .build();
    }
}
