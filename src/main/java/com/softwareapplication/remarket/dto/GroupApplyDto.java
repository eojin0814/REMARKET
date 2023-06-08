package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.GroupApply;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

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
    @Pattern(regexp="^01\\d{1}-\\d{3,4}-\\d{4}")//전화번호 pattern 지정
    private String phone; //전화번호 추가 및 필수항목 설정

    @NotNull
    private Long groupId;

    @NotNull
    private Long userId;

    public GroupApply toEntity(){
        GroupApply build = GroupApply.builder()
                .id(id)
                .name(name)
                .address(address)
                .count(count)
                .phone(phone)
                .groupId(groupId)
                .userId(userId)
                .build();
        return build;
    }

    @Builder
    public GroupApplyDto(Long id, String name, String address, int count, String phone, Long groupId, Long userId){
        this.id = id;
        this.name = name;
        this.address = address;
        this.count = count;
        this.phone = phone;
        this.groupId = groupId;
        this.userId = userId;
    }
}
