package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.SharePost;
import com.softwareapplication.remarket.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharePostRepository extends JpaRepository<SharePost, Long> {
	public List<SharePost> findAllByAuthor(User author);
	@Query(value = "SELECT * FROM SharePost p "
	   		+ "WHERE (p.title LIKE %:keyword%) ", nativeQuery = true)
	Page<SharePost> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query(value = "SELECT * FROM SharePost p "
	   		+ "WHERE (p.address LIKE %:address%) ", nativeQuery = true)
	Page<SharePost> findByAddress(@Param("address") String keyword, Pageable pageable);
}