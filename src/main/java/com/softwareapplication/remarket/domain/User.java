package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

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

    @Column(unique = true, nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 16)
    private String password;

    @Column(unique = true, nullable = false, length = 10)
    private String name;

    @Column(length = 11)
    private String phone;

    @Column(length = 50)
    private String address;

    @OneToOne
    @JoinColumn(name="image_id")
    private Image image;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SharePost> shareList = new ArrayList<SharePost>();

    @OneToMany(mappedBy = "roomId")
    private List<MessageRoom> messageRoomList = new ArrayList<MessageRoom>();

    @OneToMany(mappedBy = "sender", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MessageRoom> participatemessageRoomList = new ArrayList<MessageRoom>();

    public boolean matchPassword(String newPassword) {
        return newPassword.equals(password);
    }

    public void updateUser(String name, String phone,
                           String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
    public void updateProfileImage(Image image) {
        this.image = image;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
