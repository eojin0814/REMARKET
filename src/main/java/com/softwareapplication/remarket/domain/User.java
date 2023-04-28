package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Builder
@Getter
@Setter
@DynamicInsert
@Entity(name = "Member")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(unique = true, nullable = false, length = 20)
    private String email;

    @Column(nullable = false, length = 16)
    private String password;

    @Column(unique = true, nullable = false, length = 10)
    private String name;

    @Column(length = 11)
    private String phone;

    @Column(length = 50)
    private String address;

    private String imageUrl;

    public boolean matchPassword(String newPassword) {
        return newPassword.equals(password);
    }

    public void updateUser(String name, String phone,
                           String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
    public void updateProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
