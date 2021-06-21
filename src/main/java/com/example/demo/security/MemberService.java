package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.dao.MemberMapper;
import com.example.demo.model.dto.MemberDTO;

@Service("memberService")
public class MemberService implements UserDetailsService {
	
	private PasswordEncoder passwordEncoder;
	private MemberMapper memberMapper;
	
	@Autowired
	public MemberService(PasswordEncoder passwordEncoder, MemberMapper memberMapper) {
		this.passwordEncoder = passwordEncoder;
		this.memberMapper = memberMapper;
	}
	
	@Override
	/* 상세정보를 조회, 사용자의 계정정보와 권한을 갖는 UserDetails 인터페이스를 반환해아 한다.
	 * 매개변수는 로그인 시 입력한 아이디, 엔티티 PK가 아니라 유저를 식별할 수 있는 어떤 값을 의미 spring security에서는
	 * username라는 이름으로 사용, 여기서는 id가 id 로그인하는 form에서 name="username" 으로 요청해야 함 
	 * authorities.add(new SimpleGrantedAuthority() 롤 부여하는 코드, 방식에는 여러가지가 있지만
	 * 회원가입할 때 role을 정할 수 있도록 role entity를 만들어서 매핑해주는 것이 좋은 방법
	 * 여기서는 "admin"일 경우에 ADMIN 권한 부여 
	 * new User() 
	 * return은 spring security에서 제공하는 UserDetails를 구현한 User를 반환(org.springframework.security
	 * .core.user.details.User 
	 * 생성자의 매개변수는 순서대로 아이디, 비밀번호, 권한리스트 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberDTO memberDTO = memberMapper.findByEmail(username);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add((GrantedAuthority) memberMapper.readAuthority(username));
		memberDTO.setAuthorities(authorities);
//		authorities.add(new SimpleGrantedAuthority(memberDTO.getAuthorities());
		
		return new User(memberDTO.getId(), memberDTO.getPwd(), memberDTO.getAuthorities());
	}
	
	
	
	
	/* JPA 회원가입을 처리하는 메서드 비밀번호를 암호화하여 저장 */
//	@Transactional
//	public Long joinUser(MemberDTO memberDTO) {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		memberDTO.setPwd(passwordEncoder.encode(memberDTO.getPwd()));
//		
//		return memberRepository.save(memberDTO.toEntity()).getNo();
//	}

	
}
