package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.dto.MemberDTO;
import com.example.demo.security.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {
	
	/* 로그인 로그아웃에 대한 로직은 x */
	
	private MemberService memberService;
	
	@GetMapping("/user/signup")
	public String dispSignup() {
		return "/signup";	
	}
	
	@PostMapping("/user/signup")
	public String execSignup(MemberDTO memberDTO) {
		memberService.joinUser(memberDTO);
		return "redirect:/user/login";
	}
	
	@GetMapping("/user/login")
	public String dispLogin() {
		return "/login";
	}
	
	@GetMapping("/user/login/result")
	public String dispLoginResult() {
		return "/loginSuccess";
	}
	
	@GetMapping("/user/denied")
	public String dispDenied() {
		return "/denied";
	}
	
	@GetMapping("/user/info")
	public String dispMyInfo() {
		return "/myinfo";
	}
	
	@GetMapping("/admin")
	public String dispAdmin() {
		return "/admin";
	}
}
