package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.MessageInfo;
import com.softwareapplication.remarket.domain.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageInfoRepository extends JpaRepository<MessageInfo, Long> {
	//쪽지 내용 불러오기
	@Query(value = "SELECT m "
			+ "FROM MessageInfo m "
			+ "WHERE m.room.roomId = ?1 "
			+ "ORDER BY m.createdDate ASC")
	List<MessageInfo> findAllByRoomId(Long roomId);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE MessageInfo m "
			+ "SET m.is_read='Y' "
			+ "WHERE m.sender_id != :sender_id AND m.room_id = :room_id", nativeQuery = true)
	public void updateIsRead(@Param("sender_id")Long senderId
			, @Param("room_id")Long roomId);
	
	
	public MessageInfo findFirstByRoomOrderByCreatedDateDesc(MessageRoom room);
}
