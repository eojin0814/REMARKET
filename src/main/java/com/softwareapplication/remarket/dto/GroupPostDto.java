package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.GroupPost;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Setter
@Getter
public class GroupPostDto {
    private Long id;

    @NotBlank
    private String title;

    @FutureOrPresent
    private LocalDateTime dueDate;

    @NotBlank
    private String product;

    private String content;

    @NotNull
    @Positive
    private int numPeople;

    @NotNull
    @Positive
    private int price;

    private String image;

    @NotNull
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
    public GroupPostDto(Long id, String title, LocalDateTime dueDate, String product, String content, int numPeople, int price, String image, Long userId){
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
