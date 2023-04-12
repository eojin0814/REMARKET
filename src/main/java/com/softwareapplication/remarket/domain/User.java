package com.softwareapplication.remarket.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Builder
@Getter @Setter
@DynamicInsert
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long id;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  private String name;
  private String phone;
  @Column(name = "nickname")
  private String nickName;
  private String address;
  private String profileImg;
}
