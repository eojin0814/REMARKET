package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.domain.Image;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Setter
@Getter
public class GroupPostDto {
    private Long id;

    @CreatedDate
    private LocalDateTime created; //게시글 최초 작성

    @LastModifiedDate
    private LocalDateTime updated; //게시글 수정 날짜

    @NotBlank
    private String title;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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

    private Image image;

    @NotNull
    private Long userId;

    public GroupPost toEntity(){
        GroupPost build = GroupPost.builder()
                .id(id)
                .created(created)
                .updated(updated)
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
    public GroupPostDto(Long id, LocalDateTime created, LocalDateTime updated, String title, LocalDateTime dueDate, String product, String content, int numPeople, int price, Image image, Long userId){
        this.id = id;
        this.created = created;
        this.updated = updated;
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
