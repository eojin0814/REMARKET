package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.SharePost;
import com.softwareapplication.remarket.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class SharePostDto {
	public static String getUploadDirPath(String imageUrl) {
		return "/upload/" + imageUrl;
	}
	
	@NoArgsConstructor
	@Data
	public static class Request{
		private String uploadDirLocal = "/upload/";
		@NotBlank(message="{notBlank.title}")
		private String title;
		@Length(max=1000, message="{size.descr}")
		private String descr;
		@NotBlank(message="{notBlank.address")
		@Length(max=255, message="{size.address}")
		private String address;
		private Image image;
		private String imgUrl;
		private MultipartFile file;
		private User author;
		private Long authorId;
			
		public SharePost toEntity() {
			return SharePost.builder()
					.title(title)
					.descr(descr)
					.address(address)
					.image(image)
					.author(author)
					.build();
		}
		
		public Request(SharePost sharePost) {
			this.title = sharePost.getTitle();
			this.descr = sharePost.getDescr();
			this.address = sharePost.getAddress();
			try {
				this.imgUrl = getUploadDirPath(sharePost.getImage().getUrl());
			}catch (Exception e ) {	           
				this.imgUrl = "";
			}
			this.authorId = sharePost.getAuthor().getUserId();
		}
	}
	
	@NoArgsConstructor
	@Data
	public static class CardResponse{
		private Long postId;
		private String category;
		private String title;
		private String imgUrl;
		private String address;
		private boolean progress;
		private int msgCount;
		
		public CardResponse(SharePost sharePost) {
			this.postId = sharePost.getPostId();
			this.title = sharePost.getTitle();
			try {
				this.imgUrl = getUploadDirPath(sharePost.getImage().getUrl());
			}catch (Exception e ) {	           
				this.imgUrl = "";
			}
			this.address = sharePost.getAddress();
			boolean prog;
			if(sharePost.getProgress().equals("Y"))
				prog = true;
			else
				prog = false;
			this.progress = prog;
			this.msgCount = sharePost.getRooms().size();
		}
	}
	
	@NoArgsConstructor
	@Data
	public static class DetailResponse{
		private Long postId;
		private Long authorId;
		private String authorName;
		private String authorImgUrl;
		private String address;
		private String category;
		private String title;
		private String imgUrl;
		private String descr;
		private boolean progress;
		private LocalDateTime enrollDate;
		private int msgCount;
		
		public DetailResponse(SharePost sharePost) {
			this.postId = sharePost.getPostId();
			this.authorId = sharePost.getAuthor().getUserId();
			this.authorName = sharePost.getAuthor().getName();
			try {
				this.authorImgUrl = getUploadDirPath(sharePost.getAuthor().getImage().getUrl());
			}catch (Exception e ) {	           
				this.authorImgUrl = "";
			}
			this.address = sharePost.getAddress();
			this.title = sharePost.getTitle();
			try {
				this.imgUrl = getUploadDirPath(sharePost.getImage().getUrl());
			}catch (Exception e ) {	           
				this.imgUrl = "";
			}
			this.descr = sharePost.getDescr();
			boolean prog;
			if(sharePost.getProgress().equals("Y"))
				prog = true;
			else
				prog = false;
			this.progress = prog;
			this.enrollDate = sharePost.getCreatedDate();
			this.msgCount = sharePost.getRooms().size();
		}
	}
	
	@NoArgsConstructor
	@Data
	public static class MyPageInfo {
		private Long postId;
		private String imageUrl;
		private String title;
		private String status;
		private LocalDateTime enrollDate;
		private Long roomId;
		
		public MyPageInfo(Long postId, String imageUrl, String title, String status, LocalDateTime enrollDate) {
			this.postId = postId;
			this.imageUrl = imageUrl;
			if(!imageUrl.equals("")) {
				this.imageUrl = getUploadDirPath(imageUrl);
			}
			this.title = title;
			this.status = status;
			this.enrollDate = enrollDate;
		}
		
		public MyPageInfo(Long postId, String imageUrl, String title, String status, LocalDateTime enrollDate, Long roomId) {
			this.postId = postId;
			this.imageUrl = imageUrl;
			if(!imageUrl.equals("")) {
				this.imageUrl = getUploadDirPath(imageUrl);
			}
			this.title = title;
			this.status = status;
			this.enrollDate = enrollDate;
			this.roomId = roomId;
		}
	}
	
}
