package com.example.demo.model.dto;

import com.example.demo.security.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

	private long no;
	private String id;
	private String pwd;
	private java.util.Date enrollDate;
	
	public MemberEntity toEntity() {
		return MemberEntity.builder()
				.no(no)
				.id(id)
				.pwd(pwd)
				.build();
	}
	
	@Builder
	public MemberDTO(Long no, String id, String pwd) {
		this.no = no;
		this.id = id;
		this.pwd = pwd;
	}
}
