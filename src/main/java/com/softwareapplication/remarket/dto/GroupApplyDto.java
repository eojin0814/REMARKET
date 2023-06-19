package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.GroupApply;
import com.softwareapplication.remarket.domain.GroupPost;
import com.softwareapplication.remarket.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Data
@NoArgsConstructor
@Setter
@Getter
public class GroupApplyDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotNull
    @Positive
    private int count;

    @NotEmpty //필수 조건으로 지정
    @Pattern(regexp="^01\\d{1}-\\d{3,4}-\\d{4}", message = "전화번호 형식은 01x-xxx-xxxx(또는 01x-xxxx-xxxx)이어야 합니다.")//전화번호 pattern 지정
    private String phone; //전화번호 추가 및 필수항목 설정

    private Long groupPostId;

    private GroupPost groupPost;
    private User user;

    public GroupApply toEntity(){
        GroupApply build = GroupApply.builder()
                .id(id)
                .name(name)
                .address(address)
                .count(count)
                .phone(phone)
                .groupPost(groupPost)
                .user(user)
                .build();
        return build;
    }

    @Builder
    public GroupApplyDto(GroupApply groupApply){
        this.id = groupApply.getId();
        this.name = groupApply.getName();
        this.address = groupApply.getAddress();
        this.count = groupApply.getCount();
        this.phone = groupApply.getPhone();
        this.groupPostId = groupApply.getGroupPost().getId();
        this.user = groupApply.getUser();
    }
}
