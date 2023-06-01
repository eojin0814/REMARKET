package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.dto.SharePostDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SharePostService {

	//포스트 등록 
	public void addPost(SharePostDto.Request dto);
	
	//포스트 상세조회
	public SharePostDto.DetailResponse getPost(Long postId);
	
	//포스트 상세조회(update)
	public SharePostDto.Request getPostForModify(Long postId);
	
	//사용자가 작성한 포스트리스트 조회
	public List<SharePostDto.MyPageInfo> getPostByUserId(Long userId);
	
	//전체 포스트리스트 조회 
	public Page<SharePostDto.CardResponse> getAllPost(int page);

	//포스트 업데이트 
	public void modifyPost(Long postId, SharePostDto.Request dto);
	
	//포스트 삭제
	public void removePost(Long postId);

	//나눔 상태 업데이트 
	public void modifyProgress(Long postId, boolean prog);

	//나눔 포스트리스트 검색 조회 
	public Page<SharePostDto.CardResponse> getAllPostByKeyword(String keyword, int page, String option, String type);
}
