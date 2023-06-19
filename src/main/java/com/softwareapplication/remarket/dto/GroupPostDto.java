package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.User;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Setter
@Getter
public class GroupPostDto {
    public static String getUploadDirPath(String imageUrl) {
        return "/upload/" + imageUrl;
    }

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
    private String imgUrl;
    private MultipartFile file;

    private List<GroupCommentDto.ResponseDto> groupComments;
    private List<GroupApplyDto> groupApplies;

    private User user;

    private String status;
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
                .user(user)
                .status(status)
                .build();
        return build;
    }

    public GroupPostDto(GroupPost groupPost){
        this.id = groupPost.getId();
        this.created = groupPost.getCreated();
        this.updated = groupPost.getUpdated();
        this.title = groupPost.getTitle();
        this.dueDate = groupPost.getDueDate();
        this.product = groupPost.getProduct();
        this.content = groupPost.getContent();
        this.numPeople = groupPost.getNumPeople();
        this.price = groupPost.getPrice();
        this.image = groupPost.getImage();
        try {
            this.imgUrl = getUploadDirPath(image.getUrl());
        }catch (Exception e ) {
            this.imgUrl = "";
        }
        if(groupPost.getGroupComments() != null) {
            this.groupComments = groupPost.getGroupComments().stream().map(c -> new GroupCommentDto.ResponseDto(c)).collect(Collectors.toList());
        }
        if(groupPost.getGroupApplies() != null) {
            this.groupApplies = groupPost.getGroupApplies().stream().map(a -> new GroupApplyDto(a)).collect(Collectors.toList());
        }
        this.user = groupPost.getUser();
        this.status = groupPost.getStatus();
    }
}
