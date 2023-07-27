package com.facebook.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
abstract class BaseEntity {
	
	@CreationTimestamp
	@Column(name = "regDate", updatable = false)
	private LocalDateTime regDate;
	
}