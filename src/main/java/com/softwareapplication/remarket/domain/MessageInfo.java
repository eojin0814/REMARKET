package com.softwareapplication.remarket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name="messageinfo")
public class MessageInfo extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true)
	private Long messageId;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	private MessageRoom room;
	
	@Column(nullable = false)
	private String content;
	
	@Column(length = 1)
	private String isRead;
	
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;

	@PrePersist
	public void prePersist(){
		this.isRead = this.isRead == null ? "N" : this.isRead;
	}
}
