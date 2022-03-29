package com.kook.spjpj.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kook.spjpj.command.ICommand;
import com.kook.spjpj.command.JoinCommand;
import com.kook.spjpj.dao.Dao;
import com.kook.spjpj.util.Constant;

// 한서
@Controller
public class SPJController {

//	bean 주입하여 Constant에 설정
	private Dao dao;

	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
		Constant.dao = dao;
	}

	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		Constant.passwordEncoder = passwordEncoder;
	}

	private ICommand com; // command의 인터페이스 객체를 선언하여 다형성에 의해 구현 클래스 객체를 대입 사용

	
// ===== 회원가입 화면 =====
	@RequestMapping("/spj_join")
	public String spj_join() {
		return "spj_join";
	}

// ===== 회원가입 화면에 입력한 내용을 command를 통해서 처리 
	@RequestMapping(value = "/join", produces = "application/text; charset=UTF8")
	@ResponseBody // jsp가 아닌 문자열이나 객체을 보내기 때문에 필요, 참고: https://memostack.tistory.com/243
	public String join(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("join"); // console창 통해서 실행여부 확인용 코드

		com = new JoinCommand(); // join요청에 따른 command클래스로 MiniCommand인터페이스 구현 클래스
		com.execute(model, request);
		String result = (String) request.getAttribute("result"); // JoinCommand에서 저장한 결과값을 String result에 입력
		System.out.println(result); // console창 통해서 실행여부 확인용 코드
		if (result.equals("success"))
			return "join-success";
		else
			return "join-failed";
	}

//	========== menu ================
//	===== main 화면 =====
	@RequestMapping("/spj_main")
	public String spj_main() {
		return "spj_main";
	}	
	
//	===== board 화면 =====
	@RequestMapping("/spj_boardList")
	public String spj_boardList() {
		return "spj_boardList";
	}	
	
//	===== main 화면 =====
	@RequestMapping("/spj_about")
	public String spj_about() {
		return "spj_about";
	}	
	
//	===== 로그인 화면 =====
	@RequestMapping("/spj_login")
	public String spj_login(HttpServletRequest request,HttpServletResponse response,
			HttpSession session,Model model) {
		System.out.println("login");
		return "spj_login";
	}

	@RequestMapping(value = "/processLogin", method = RequestMethod.GET)
	public ModelAndView processLogin(
			@RequestParam(value = "log", required = false) String log,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpSession session
			) {
		System.out.println("processLogin");
		ModelAndView model = new ModelAndView();		
		if(log != null && log !="")  { 
			// security form이 아닌 곳(a href="processLogin?log=1)에서 로그인 창 요청시
			model.addObject("log", "before login!");
		}
		if (error != null && error != "") { //로그인시 에러발생하면 security에서 요청(값은 1)
			model.addObject("error", "Invalid username or password!");
		} 
		if (logout != null && logout != "") { //로그아웃 성공시 security에서 요청(값은 1)
			model.addObject("logout", "You've been logged out successfully.");
		}
		model.setViewName("spj_login");	
		return model;
	}
	
	@RequestMapping("/Login")
	public String Login(HttpServletRequest request,HttpServletResponse response,Model model){
		System.out.println("Login");
		return "spj_login";
	}	
	
	//=======logout========
	@RequestMapping("/spj_logout") 
	public String spj_logout() {
		System.out.println("logout");
		return "spj_logout";
	}
	

	//======about 임시======
	@RequestMapping("/spj_boardlist") 
	public String product(HttpServletRequest request,HttpServletResponse response,
			Model model,Authentication authentication) {
		System.out.println("boardlist");
		
		//우리프로젝에서는 로그인 성공후 페이지이므로 id와 role정보를 얻어내는 방법
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Constant.username = userDetails.getUsername();
		System.out.println(userDetails.getUsername());//hsk5410@naver.com (로그인한 아이디)
		Collection<? extends GrantedAuthority>  authorities = authentication.getAuthorities();
		String auth = authorities.toString(); //role을 얻어서 문자열로 변환
		System.out.println(auth); //[ROLE_USER]형태
		return "spj_boardlist";
	}			
}
