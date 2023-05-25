package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.MessageInfo;
import com.softwareapplication.remarket.domain.MessageRoom;
import com.softwareapplication.remarket.domain.SharePost;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.MessageDto;
import com.softwareapplication.remarket.dto.SharePostDto;
import com.softwareapplication.remarket.repository.MessageInfoRepository;
import com.softwareapplication.remarket.repository.MessageRoomRepository;
import com.softwareapplication.remarket.repository.SharePostRepository;
import com.softwareapplication.remarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

//@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl<T> implements MessageService {
	private final SharePostRepository sharePostRepository;
	private final MessageInfoRepository messageInfoRepository;
	private final MessageRoomRepository messageRoomRepository;
	private final UserRepository userRepository;

	//쪽지함 생성
	@Override
	@Transactional
	public Long addRoom(Long postId, Long senderId) {
		SharePost post = sharePostRepository.findById(postId).orElseThrow();
		User author = userRepository.findById(post.getAuthor().getUserId()).orElseThrow();
		User sender = userRepository.findById(senderId).orElseThrow();
		MessageRoom room = MessageRoom.builder().author(author).post(post).sender(sender).build();
		Long roomId = messageRoomRepository.saveAndFlush(room).getRoomId();
		return roomId;
	}

	//쪽지함 정보 조회
	@Override
	@Transactional
	public Long getRoom(Long postId, Long senderId) {
		return messageRoomRepository.findRoomIdxByPostAndSender(postId, senderId);
	}

	//쪽지 보내기
	@Override
	@Transactional
	public void sendMessage(MessageDto.Request req, Long senderId, Long roomId) {
		MessageRoom room = messageRoomRepository.findById(roomId).orElseThrow();
		User sender = userRepository.findById(senderId).orElseThrow();
		MessageInfo msgInfo = MessageInfo.builder().content(req.getContent()).room(room).sender(sender).build();
		messageInfoRepository.saveAndFlush(msgInfo);
	}

	//쪽지 내용 리스트 조회
	@Override
	@Transactional(readOnly = true)
    public List<MessageDto.MessageResponse> getAllMessage(Long roomId) {
		List<MessageInfo> messageList = messageInfoRepository.findAllByRoomId(roomId);
        return messageList.stream().map(MessageDto.MessageResponse::new).collect(Collectors.toList());
    }

	//쪽지함 리스트 조회
	@Override
	@Transactional(readOnly = true)
	public List<MessageDto.Info> getAllRoom(Long userId) {

		User author = userRepository.findById(userId).orElseThrow();
		System.out.println(author);
		User sender = userRepository.findById(userId).orElseThrow();
		System.out.println(sender);

		List<MessageRoom> list = messageRoomRepository.findByAuthorOrSenderOrderByCreatedDateDesc(author, sender);
		System.out.println("messageRoom List : " + list.size());

		List<MessageDto.Info> roomList = new ArrayList<MessageDto.Info>();

		for(MessageRoom room : list) {
			MessageInfo m = messageInfoRepository.findTop1ByRoomOrderByCreatedDateDesc(room);
			System.out.println(m);
			boolean isRead;
			if(Objects.equals(m.getSender().getUserId(), userId)) {
				isRead = true;
			} else {
				isRead = !m.getIsRead().equals("N");
			}

			MessageDto.Info info = new MessageDto.Info(
					room.getRoomId(),
					room.getPost().getPostId(),
					room.getPost().getAuthor().getUserId(),
					room.getPost().getAuthor().getName(),
					room.getPost().getAuthor().getImage() == null ? "" : "/upload/" + room.getPost().getAuthor().getImage().getUrl(),
					room.getPost().getTitle(),
					room.getPost().getImage() == null ? "" : "/upload/" + room.getPost().getImage().getUrl(),
					room.getSender().getUserId(),
					room.getSender().getName(),
					room.getSender().getImage() == null ? "" : "/upload/" + room.getSender().getImage().getUrl(),
					m.getContent(),
					isRead,
					m.getCreatedDate());
			roomList.add(info);
		}

		Collections.sort(roomList, new Comparator<MessageDto.Info>() {
			@Override
			public int compare(MessageDto.Info i1, MessageDto.Info i2) {
				return i2.getSendTime().compareTo(i1.getSendTime());
			}
		});

		return roomList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SharePostDto.MyPageInfo> getAllMessageBySender(Long userIdx) {
		User sender = userRepository.findById(userIdx).orElseThrow();
		List<MessageRoom> roomList = messageRoomRepository.findAllMessageBySender(sender);

		List<SharePostDto.MyPageInfo> postList = roomList.stream().map(msg -> new SharePostDto.MyPageInfo(
						msg.getPost().getPostId(),
						msg.getPost().getImage() == null ? "" : msg.getPost().getImage().getUrl(),
						msg.getPost().getTitle(),
						msg.getPost().getProgress().equals("Y") ? "나눔 완료" : "진행중",
						msg.getPost().getCreatedDate(),
						msg.getRoomId()))
				.collect(Collectors.toList());

		return postList;
	}

	//쪽지 읽음 처리
	@Override
	@Transactional
	public void updateIsRead(Long senderIdx, Long roomIdx) {
		messageInfoRepository.updateIsRead(senderIdx, roomIdx);
	}

}
