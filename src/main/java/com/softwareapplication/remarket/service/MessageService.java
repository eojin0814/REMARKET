package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.dto.MessageDto;
import com.softwareapplication.remarket.dto.SharePostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

	//쪽지함 생성
	public Long addRoom(Long postIdx, Long senderIdx);
	
	//쪽지 리스트 조회 
	public List<MessageDto.MessageResponse> getAllMessage(Long roomIdx);

	//쪽지함 조회
	public List<MessageDto.Info> getAllRoom(Long userIdx);
	
	//쪽지함 상세 조회
	public Long getRoom(Long postIdx, Long senderIdx);
	
	//참여한 쪽지의 post 조회
	public List<SharePostDto.MyPageInfo> getAllMessageBySender(Long userIdx);

	//쪽지 보내기 
	public void sendMessage(MessageDto.Request req, Long senderIdx, Long roomIdx);
	
	//쪽지 읽음 처리
	public void updateIsRead(Long senderId, Long roomId);
}
