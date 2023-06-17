package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.Community;
import com.softwareapplication.remarket.domain.CommunityComment;
import com.softwareapplication.remarket.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class CommunityCommentDto {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class RequestDto {
        private Long id;

        @NotBlank
        private String commentContent;
        private Long parentId;

        private Community community;
        private User user;

        public CommunityComment toEntity() {
            CommunityComment communityComment = CommunityComment.builder()
                    .id(id)
                    .commentContent(commentContent)
                    .user(user)
                    .build();
            return communityComment;
        }
    }
    @Getter
    public static class ResponseDto{
        private Long id;
        @NotBlank
        private String commentContent;
        private CommunityComment parent;
        private List<CommunityCommentDto.ResponseDto> children;
        private Long communityId;
        private User user;
        private String name;

        public ResponseDto(CommunityComment communityComment){
            this.id = communityComment.getId();
            this.commentContent = communityComment.getCommentContent();
            this.parent = communityComment.getParent();
            if(communityComment.getChildren() != null) {
                this.children = communityComment.getChildren().stream().map(c-> new CommunityCommentDto.ResponseDto(c)).collect(Collectors.toList());
            }
            this.communityId = communityComment.getCommunity().getId();
            this.user = communityComment.getUser();
            this.name = communityComment.getUser().getName();
        }
    }
}
