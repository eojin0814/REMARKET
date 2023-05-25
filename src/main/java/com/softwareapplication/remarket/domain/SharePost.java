package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "share_post")
public class SharePost extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true)
	private Long postId;
	
	@Column(nullable = false)
	private String title;

	@Column(length = 1000)
	private String descr;
	
	@Column(length = 50)
	private String address;
	
	@Column(length = 1)
	private String progress;
	
	@OneToOne
	@JoinColumn(name="image_id")
	private Image image;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private User author;

	@OneToMany(mappedBy="post", cascade = CascadeType.ALL)
	private List<MessageRoom> rooms = new ArrayList<MessageRoom>();
	
	@PrePersist
	public void prePersist(){
		this.progress = this.progress == null ? "N" : this.progress;
	}

	public void updatePost(String title, String descr, String address) {
        this.title = title;
        this.descr = descr;
        this.address = address;
    }
	
	public void updatePostImg(Image image) {
		this.image = image;
	}
	
	public void updateProgress(boolean prog) {
		if(prog)
			this.progress = "N";
		else
			this.progress = "Y";
	}
}
