package com.softwareapplication.remarket.service;


import com.softwareapplication.remarket.domain.SharePost;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.SharePostDto;
import com.softwareapplication.remarket.repository.SharePostRepository;
import com.softwareapplication.remarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@Slf4j
@RequiredArgsConstructor // final이 붙은 필드 생성
@Service
public class SharePostServiceImpl implements SharePostService {
	private final SharePostRepository sharePostRepository;
	private final UserRepository userRepository;

	//게시글 등록
	@Override
	@Transactional
	public void addPost(SharePostDto.Request dto) {
		User author = userRepository.findById(dto.getAuthorId()).orElseThrow();
		dto.setAuthor(author);
		sharePostRepository.save(dto.toEntity());
	}

	
	//게시글 상세정보 조회
	@Override
	@Transactional(readOnly = true)
    public SharePostDto.DetailResponse getPost(Long postId) {
        SharePost entity = sharePostRepository.findById(postId).orElseThrow();
        return new SharePostDto.DetailResponse(entity);
    }
	
	//게시글 상세정보(update용)
	@Override
	@Transactional(readOnly = true)
    public SharePostDto.Request getPostForModify(Long postId) {
		SharePost entity = sharePostRepository.findById(postId).orElseThrow();
        return new SharePostDto.Request(entity);
    }
	
	//사용자가 작성한 포스트리스트 조회
	@Transactional
	public List<SharePostDto.MyPageInfo> getPostByUserId(Long userId) {
		User author = userRepository.findById(userId).orElseThrow();
		
		List<SharePost> postEntityList = sharePostRepository.findAllByAuthor(author);
		
		List<SharePostDto.MyPageInfo> postList = postEntityList.stream().map(post -> new SharePostDto.MyPageInfo(
				post.getPostId(),
				post.getImage() == null ? "" : post.getImage().getUrl(),
				post.getTitle(),
				post.getProgress().equals("Y") ? "나눔 완료" : "진행중",
				post.getCreatedDate())
		).collect(Collectors.toList());
		
		return postList;
	}
		
	//게시글 리스트 조회
	@Override
	@Transactional(readOnly = true)
    public Page<SharePostDto.CardResponse> getAllPost(int page) {
		Pageable pageable = PageRequest.of(page, 8, Sort.by(Direction.DESC, "createdDate"));
		Page<SharePost> postList = sharePostRepository.findAll(pageable);
		
        return postList.map(SharePostDto.CardResponse::new);
    }
    
    //게시글 수정 
    @Override
    @Transactional
    public void modifyPost(Long postIdx, SharePostDto.Request dto) {
    	SharePost entity = sharePostRepository.findById(postIdx).orElseThrow();
    	
    	if(!dto.getFile().getOriginalFilename().equals(""))
    		entity.updatePostImg(dto.getImage());
    	entity.updatePost(dto.getTitle(), dto.getDescr(), dto.getAddress());
    }
    
    //게시글 삭제  
    @Override
    @Transactional
    public void removePost(Long postIdx) {
    	sharePostRepository.deleteById(postIdx);
    }
    
    //나눔 유무 변경
    @Override
    @Transactional
    public void modifyProgress(Long postIdx, boolean prog) {
    	SharePost entity = sharePostRepository.findById(postIdx).orElseThrow(); 
    	entity.updateProgress(prog);
    }
    
    //나눔 포스트 검색 - 제목, 주소
    @Override
 	@Transactional
	public Page<SharePostDto.CardResponse> getAllPostByKeyword(String keyword, int page, String option, String type) {
    	Pageable pageable = PageRequest.of(page, 8, Sort.by(Direction.DESC, "created_date") );
  
    	if(option.equals("title")) {
    		if(type.equals("key")) {
	    		Page<SharePost> postList = sharePostRepository.findByKeyword(keyword, pageable);
	    		return postList.map(SharePostDto.CardResponse::new);
    		}
    		else {
    			Page<SharePost> postList = sharePostRepository.findByAddress(keyword, pageable);
    			return postList.map(SharePostDto.CardResponse::new);
    		}
    	}
		return null;
    }
    

}
