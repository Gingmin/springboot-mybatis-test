package com.example.demo.model.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

public class MemberDTO implements UserDetails{

	private long no;
	private String id;
	private String pwd;
	private String role;
	private java.util.Date enrollDate;
	private Collection<? extends GrantedAuthority> authorities;
	 
	//유저가 갖고 있는 권한 목록
	@Override
	public Collection <? extends GrantedAuthority> getAuthorities() {
		
//		ArrayList<GrantedAuthority> authList = new ArrayList<GrantedAuthority> ();
//		authList.add(new SimpleGrantedAuthority(role));
		return this.authorities;
	}
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
	@Override
	public String getPassword() {
		return this.pwd;
	}
	@Override
	public String getUsername() {
		return this.id;
	}
	// 유저 아이디 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	// 유저아이디 lock 여부
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	//비밀번호 만료 여부
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	// 계정 활성화 여부
	@Override
	public boolean isEnabled() {
		return true;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public java.util.Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(java.util.Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	@Override
	public String toString() {
		return "MemberDTO [no=" + no + ", id=" + id + ", pwd=" + pwd + ", role=" + role + ", enrollDate=" + enrollDate
				+ ", authorities=" + authorities + "]";
	}
	
	
	
//	@Builder
//	public MemberDTO(Long no, String id, String pwd) {
//		this.no = no;
//		this.id = id;
//		this.pwd = pwd;
//	}
}
