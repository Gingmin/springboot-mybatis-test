package com.example.demo.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	
	@Column(length = 20, nullable = false)
	private String id;
	
	@Column(length = 100, nullable = false)
	private String pwd;
	
    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .no(no)
                .id(id)
                .pwd(pwd)
                .build();
    }
	
	@Builder
	public MemberEntity(Long no, String id, String pwd) {
		this.no = no;
		this.id = id;
		this.pwd = pwd;
	}
}
