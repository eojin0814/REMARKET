package com.softwareapplication.remarket.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="image")
public class Image {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "image_id",unique = true)
	 private Long id;
	 
	 @Column(nullable = false)
	 private String url;
	 
	 public void updateImage(String url) {
		 this.url = url;
	 }
	
}
