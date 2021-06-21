package com.example.demo.model.dao;

import java.util.List;

import com.example.demo.model.dto.MemberDTO;

public interface MemberMapper {

	MemberDTO findByEmail(String username);
	
	public List<String> readAuthority(String username);

}
