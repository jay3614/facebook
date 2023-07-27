package com.facebook.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplyEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	private String replyer;
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PostEntity post;
	
}
