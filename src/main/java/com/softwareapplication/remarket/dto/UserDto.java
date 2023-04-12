package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SaveRequest {
        private Long id;
        private String email;
        private String password;
        private String password2;
        private String name;
        private String phone;
        private String nickName;
        private String address;
        private String imageUrl;
        public User toEntity() {
            return User.builder()
                    .id(id)
                    .address(address)
                    .name(name)
                    .password(password)
                    .phone(phone)
                    .nickName(nickName)
                    .email(email)
                    .profileImg(imageUrl)
                    .build();
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateRequest {
        private String email;
        private String password;
        private String password2;
        private String name;
        private String phone;
        private String nickName;
        private String address;
        private String imageUrl;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class getUserResponse {
        private Long id;
        private String email;
        private String password;
        private String password2;
        private String name;
        private String phone;
        private String nickName;
        private String address;
        private String imageUrl;
    }

}
