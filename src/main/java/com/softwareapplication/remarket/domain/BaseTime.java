package com.softwareapplication.remarket.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter

@MappedSuperclass //BaseTimeEntity를 상속한 엔티티들은 아래 필드들을 컬럼으로 인식하게 된다.
@EntityListeners(AuditingEntityListener.class) //Auditing(자동으로 값 매핑) 기능 추가
public abstract class BaseTime {
	
	@CreatedDate
	private LocalDateTime createdDate;
}