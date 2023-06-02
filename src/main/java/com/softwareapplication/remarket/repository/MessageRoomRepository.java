package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.MessageRoom;
import com.softwareapplication.remarket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {

	//Room ID 찾기 
	@Query(value = "SELECT r.roomId "
			+ "FROM MessageRoom r "
			+ "WHERE r.post.postId = ?1 AND r.sender.userId =?2")
	public Long findRoomIdxByPostAndSender(Long postIdx, Long senderIdx);

	//쪽지함들 불러오기 
	public List<MessageRoom> findByAuthorOrSenderOrderByCreatedDateDesc(User author, User sender);
	
	// 참여한 쪽지함
	List<MessageRoom> findAllMessageBySender(User sender);
}
