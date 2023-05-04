package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.GroupPost;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@Setter
@Getter
public class GroupPostDto {
    private Long id;
    private String title;
    private Date dueDate;
    private String product;
    private String content;
    private int numPeople;
    private int price;
    private String image;
    private Long userId;

    public GroupPost toEntity(){
        GroupPost build = GroupPost.builder()
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

    @Builder
    public GroupPostDto(Long id, String title, Date dueDate, String product, String content, int numPeople, int price, String image, Long userId){
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.product = product;
        this.content = content;
        this.numPeople = numPeople;
        this.price = price;
        this.image = image;
        this.userId = userId;
    }

}
