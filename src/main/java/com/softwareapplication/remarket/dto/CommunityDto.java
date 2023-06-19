package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.Community;
import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Setter
@Getter
public class CommunityDto {
    public static String getUploadDirPath(String imageUrl) {
        return "/upload/" + imageUrl;
    }
    private Long id;

    @NotBlank
    private String title;

    @CreatedDate
    private LocalDateTime created; //게시글 최초 작성

    @LastModifiedDate
    private LocalDateTime updated; //게시글 수정 날짜

    @NotBlank
    private String contentCommunity;

    private Image image;
    private String imgUrl;
    private MultipartFile file;

    private List<CommunityCommentDto.ResponseDto> communityComments;

    private User user;


    public Community toEntity(){
        Community build = Community.builder()
                .id(id)
                .title(title)
                .created(created)
                .updated(updated)
                .contentCommunity(contentCommunity)
                .image(image)
                .user(user)
                .build();
        return build;
    }

    @Builder
    public CommunityDto (Community community){
        this.id = community.getId();
        this.title = community.getTitle();
        this.created = community.getCreated();
        this.updated = community.getUpdated();
        this.contentCommunity = community.getContentCommunity();
        this.image = community.getImage();
        try {
            this.imgUrl = getUploadDirPath(image.getUrl());
        }catch (Exception e ) {
            this.imgUrl = "";
        }
        if(community.getCommunityComments() != null) {
            this.communityComments = community.getCommunityComments().stream().map(c -> new CommunityCommentDto.ResponseDto(c)).collect(Collectors.toList());
        }
        this.user = community.getUser();
    }
}
