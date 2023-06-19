package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SecondHandDto {




    public static String getUploadDirPath(String imageUrl) {
        return "/upload/" + imageUrl;
    }
    private Long secondHandId;
    private String content;
    @CreatedDate
    private LocalDateTime createdDate;
    private Image image;
    private Long price;
    private String title;

    private Long userId;

    private String status;

    private String imgUrl;
    private MultipartFile file;

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
                .status(status)
                .build();
    }
    public SecondHandDto(SecondHand secondHand) {
        this.secondHandId=secondHand.getSecondHandId();
        this.createdDate=secondHand.getCreatedDate();
        this.title=secondHand.getTitle();
        this.content=secondHand.getContent();
        this.image=secondHand.getImage();
        this.userId=secondHand.getSecondHandId();

        try {
            this.imgUrl = getUploadDirPath(secondHand.getImage().getUrl());
        }catch (Exception e ) {
            this.imgUrl = "";
        }
        this.price=secondHand.getPrice();
    }

}
