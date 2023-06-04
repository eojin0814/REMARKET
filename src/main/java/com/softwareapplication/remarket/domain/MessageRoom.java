package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name="messageroom")
public class MessageRoom extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true)
	private Long roomId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private SharePost post;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;
	
	@OneToMany(mappedBy="room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<MessageInfo> messages = new ArrayList<MessageInfo>();
}
