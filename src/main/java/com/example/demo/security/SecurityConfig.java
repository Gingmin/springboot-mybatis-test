package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //Spring Security 설정 클래스 정의
/* WebSecurityConfigurerAdapter 클래스를 상속받아 메서드를 구현하는 것인 일반적인 방법 
 * 이 클래스는 WebSecurityConfigurer 인스턴스를 편리하게 생성하기 위한 클래스
 * */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private MemberService memberService;
	
	@Autowired
	public SecurityConfig(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		/* BCryptPasswordEncoder는 service에서 비밀번호를 암호화할 수 있도록 bean으로 등록 
		 * new BCryptPasswordEncoder();*/
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

// 실제 인증을 한 이후에 인증이 완료되면 Authentication객체를 반환을 위한 bean등록 - 필요한게 맞는지?
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider(MemberService memberService) {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(memberService);
//		authenticationProvider.setPasswordEncoder(passwordEncoder());
//		return authenticationProvider;
//	}
	
	/* configure 메소드 오버라이딩 */
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		/* WebSecurity는 FilterChainProxy를 생성하는 필터
		 * 해당 경로의 파일들은 SpringSecurity가 무시할 수 있도록 설정 
		 * 즉, 이 파일들은 무조건 통과하며, 파일 기준은 resource/static 디렉터리 */
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* HttpSecurity를 통해 HTTP 요청에 대한 웹 기반 보안을 구성할 수 있다.
		 * authorizeRequests() HttpServletRequest에 따라 접근(access)을 제한
		 * antMatchers() 메서드로 특정 경로를 지정하며, permitAll(), hasRole() 메서드로 
		 * 역할(role)에 따른 접근 설정을 잡아준다. 여기서 role은 권한을 의미, 즉 어떤 페이지는
		 * 관리자만 접근해야 하고, 어떤 페이지는 회원만 접근해야할 때 그 권한을 부여하기 위해 역할을
		 * 설정하는 것 */
		http.authorizeRequests()
			/* /admin으로 시작하는 경로는 ADMIN롤을 가진 사용자만 접근 가능 */
			.antMatchers("/admin/**").hasRole("ADMIN")
			/* /user/myinfo 경로는 MEMBER롤을 가진 사용자만 접근 가능 */
			.antMatchers("/user/myinfo").hasRole("MEMBER")
			/* 모든 경로에 대해서는 권한없이 접근 가능 */
			.antMatchers("/**").permitAll()
			/* .anyRequest().authenticated() 모든 요청에 대해 인증된 사용자만 접근하도록 설정할 수도 있다. */
		.and()
			/* form 기반 인증 - 로그인 정보는 기본적으로 HttpSession을 이용 */
			.formLogin()
			/* /login 경로로 접근하면, Spring Security에서 제공하는 로그인 form을 사용가능 
			 * 기본 제공되는 form말고 커스텀 로그인 폼을 사용하고 싶으면 loginPage()메서드를 사용
			 * 이때 커스텀 로그인 form의 action경로와 loginPage()의 파라미터 경로가 일치해야
			 * 인증을 처리할 수 있다. */
			.loginPage("/user/login")
			/* 로그인이 성공했을 때 이동되는 페이지이며, 컨트롤러에서 URL 매핑이 되어있어야 한다.*/
			.defaultSuccessUrl("/user/login/result")
			.permitAll()
		.and()
			/* WebSecurityConfigurerAdapter를 사용할 때 자동으로 적용, 기본적으로 /logout에 접근하면
			 * HTTP 세션을 제거 */
			.logout()
			/* 로그아웃의 기본 URL이 아닌 다른 URL로 재정의 */
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
			.logoutSuccessUrl("/user/logout/result")
			/* HTTP 세션을 초기화하는 작업 */
			.invalidateHttpSession(true)
			/* deleteCokkies("KEY명") 로그아웃이 특정 쿠키를 제거할 때 사용 */
		.and()
			/* 예외가 발생했을 때 사용 - 여기서는 접근권한이 없을 때 로그인 페이지로 이동 */
			.exceptionHandling().accessDeniedPage("/user/denied");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* Spring Security에서 모든 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManager
		 * 를 생성하기 위해서는 AuthenticationManagerBuilder를 사용
		 * 로그인 처리, 인증을 위해서는 UserDetailService를 통해서 필요한 정보들을 가져오는데 - 여기서는 memberService
		 * service 클래스에서는 UserDetailsService인터페이스를 implements하여, loadUserByUsername()메서드 구현하면 된다.
		 * 비밀번호 암호화에는 passwordEncoder를 사용 */
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
		//auth.authenticationProvider(authenticationProvider(memberService));
	}
	
}
