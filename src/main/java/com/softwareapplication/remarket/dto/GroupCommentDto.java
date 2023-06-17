package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.GroupComment;
import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

public class GroupCommentDto {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class RequestDto {
        private Long id;

        @NotBlank
        private String commentContent;
        private Long parentId;

        private GroupPost groupPost;
        private User user;

        public GroupComment toEntity() {
            GroupComment groupComment = GroupComment.builder()
                    .id(id)
                    .commentContent(commentContent)
                    .user(user)
                    .build();
            return groupComment;
        }
    }
    @Getter
    public static class ResponseDto{
        private Long id;
        @NotBlank
        private String commentContent;
        private GroupComment parent;
        private List<GroupCommentDto.ResponseDto> children;
        private Long groupPostId;
        private User user;
        private String name;

        public ResponseDto(GroupComment groupComment){
            this.id = groupComment.getId();
            this.commentContent = groupComment.getCommentContent();
            this.parent = groupComment.getParent();
            if(groupComment.getChildren() != null) {
                this.children = groupComment.getChildren().stream().map(c-> new GroupCommentDto.ResponseDto(c)).collect(Collectors.toList());
            }
            this.groupPostId = groupComment.getGroupPost().getId();
            this.user = groupComment.getUser();
            this.name = groupComment.getUser().getName();
        }
    }


}
