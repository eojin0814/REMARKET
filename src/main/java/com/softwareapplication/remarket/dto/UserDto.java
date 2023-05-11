package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class UserDto {


    public static String getUploadDirPath(String imageUrl) {
        return "/upload/" + imageUrl;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Info{
        private Long userId;
        private String email;
        private String password;
        private String name;
        private String phone;
        private String address;
        private Image imageIdx;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Request{

        private Long userId;

        @Email
        @NotBlank(message = "이메일은 필수로 입력해주세요")
        private String email;

        @Size(min=5,message="비밀번호는 5자 이상이어야 합니다")
        @Size(max=16,message="비밀번호는 16자 이하여야 합니다")
        private String password;

        @NotBlank(message="비밀번호를 입력해주세요")
        private String repeatedPassword;

        @NotBlank(message="닉네임을 입력해주세요")
        @Size(max=10,message="닉네임은 10자 이하여야 합니다")
        private String name;

        private String phone1;

        @NotBlank(message="휴대폰번호를 입력해주세요")
        private String phone2;

        @NotBlank(message="휴대폰번호를 입력해주세요")
        private String phone3;
        private String phone;
        private String address;
        private Image image;
        private String imgUrl;
        private MultipartFile file;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .userId(userId)
                    .password(password)
                    .name(name)
                    .address(address)
                    .phone(phone1+phone2+phone3)
                    .image(image)
                    .build();
        }
        public Request(User user) {
            this.userId=user.getUserId();
            this.email=user.getEmail();
            this.password=user.getPassword();
            this.name=user.getName();
            this.phone=user.getPhone();
            this.address=user.getAddress();
            try {
                this.imgUrl=getUploadDirPath(user.getImage().getUrl());
            } catch (Exception e ) {
                this.imgUrl = "";
            }
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response{
        private Long userId;
        private String email;
        private String password;
        private String name;
        private String phone1;
        private String phone2;
        private String phone3;
        private String address;
        private String imageUrl;
        private MultipartFile file;

        public Response(User user) {
            this.userId = user.getUserId();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.name = user.getName();
            setPhoneNumber(user.getPhone());
            if(user.getImage() != null)
                this.imageUrl = getUploadDirPath(user.getImage().getUrl());
            this.address = user.getAddress();
        }

        public void setPhoneNumber(String phone) {
            this.phone1 = phone.substring(0, 3);
            this.phone2 = phone.substring(3, 7);
            this.phone3 = phone.substring(7, 11);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginRequest{
        private String email;
        private String password;
    }
}