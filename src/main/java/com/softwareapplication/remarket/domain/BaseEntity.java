package com.softwareapplication.remarket.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at",updatable = false, nullable = false) // 최초의 값 유지
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at" ,nullable = false)
    private LocalDateTime updatedAt;
}
