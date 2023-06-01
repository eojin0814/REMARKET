package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.MessageInfo;
import com.softwareapplication.remarket.domain.MessageRoom;
import com.softwareapplication.remarket.domain.SharePost;
import com.softwareapplication.remarket.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class MessageDto {
	public static String getUploadDirPath(String imageUrl) {
		return "/upload/" + imageUrl;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class Info{
		private Long roomId;
		private Long postId;
		private Long authorId;
		private String authorName;
		private String authorImgUrl;
		private String title;
		private String postImgUrl;
		private Long senderId;
		private String senderName;
		private String senderImgUrl;
		private String content;
		private boolean isRead;
		private LocalDateTime sendTime;
	}

	@NoArgsConstructor
	@Data
	public static class Request{
		 
		private String content;
		private User sender;
		private Long senderId;
		private SharePost post;
		private MessageRoom room;
		private User author;
		private Long authorId;
		private Long roomId;
		
		public MessageRoom toRoomEntity() {
			return MessageRoom.builder()
					.post(post)
					.author(author)
					.sender(sender)
					.build();
		}
		
		public MessageInfo toMsgEntity() {
			return MessageInfo.builder()
					.room(room)
					.content(content)
					.sender(sender)
					.build();
		}
	}
	
	@NoArgsConstructor
	@Data
	public static class MessageResponse{
		
		private Long messageId;
		private String content;
		private LocalDateTime sendDate;
		private boolean isRead;
		private Long senderId;
		private String senderName;
		private String senderImgUrl;
		private Long authorId;
		private String authorName;
		private String authorImgUrl;
		
		public MessageResponse(MessageInfo message) {
			this.messageId = message.getMessageId();
			this.content = message.getContent();
			this.sendDate = message.getCreatedDate();
			
			boolean isRead;
			if(message.getIsRead().equals("Y"))
				isRead = true;
			else
				isRead = false;
			this.isRead = isRead;
		
			this.senderId = message.getSender().getUserId();
			this.senderName = message.getSender().getName();
			
			try {
				this.senderImgUrl = getUploadDirPath(message.getSender().getImage().getUrl());
			}catch (Exception e ) {	           
				this.senderImgUrl = "";
			}
			this.authorId = message.getRoom().getPost().getAuthor().getUserId();
			this.authorName = message.getRoom().getPost().getAuthor().getName();
			try {
				this.authorImgUrl = getUploadDirPath(message.getRoom().getPost().getAuthor().getImage().getUrl());
			}catch (Exception e ) {	           
				this.authorImgUrl = "";
			}
		}
	}

}
