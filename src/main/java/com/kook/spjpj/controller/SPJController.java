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

// �Ѽ�
@Controller
public class SPJController {

//	bean �����Ͽ� Constant�� ����
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

	private ICommand com; // command�� �������̽� ��ü�� �����Ͽ� �������� ���� ���� Ŭ���� ��ü�� ���� ���

	
// ===== ȸ������ ȭ�� =====
	@RequestMapping("/spj_join")
	public String spj_join() {
		return "spj_join";
	}

// ===== ȸ������ ȭ�鿡 �Է��� ������ command�� ���ؼ� ó�� 
	@RequestMapping(value = "/join", produces = "application/text; charset=UTF8")
	@ResponseBody // jsp�� �ƴ� ���ڿ��̳� ��ü�� ������ ������ �ʿ�, ����: https://memostack.tistory.com/243
	public String join(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("join"); // consoleâ ���ؼ� ���࿩�� Ȯ�ο� �ڵ�

		com = new JoinCommand(); // join��û�� ���� commandŬ������ MiniCommand�������̽� ���� Ŭ����
		com.execute(model, request);
		String result = (String) request.getAttribute("result"); // JoinCommand���� ������ ������� String result�� �Է�
		System.out.println(result); // consoleâ ���ؼ� ���࿩�� Ȯ�ο� �ڵ�
		if (result.equals("success"))
			return "join-success";
		else
			return "join-failed";
	}

//	========== menu ================
//	===== main ȭ�� =====
	@RequestMapping("/spj_main")
	public String spj_main() {
		return "spj_main";
	}	
	
//	===== board ȭ�� =====
	@RequestMapping("/spj_boardList")
	public String spj_boardList() {
		return "spj_boardList";
	}	
	
//	===== main ȭ�� =====
	@RequestMapping("/spj_about")
	public String spj_about() {
		return "spj_about";
	}	
	
//	===== �α��� ȭ�� =====
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
			// security form�� �ƴ� ��(a href="processLogin?log=1)���� �α��� â ��û��
			model.addObject("log", "before login!");
		}
		if (error != null && error != "") { //�α��ν� �����߻��ϸ� security���� ��û(���� 1)
			model.addObject("error", "Invalid username or password!");
		} 
		if (logout != null && logout != "") { //�α׾ƿ� ������ security���� ��û(���� 1)
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
	

	//======about �ӽ�======
	@RequestMapping("/spj_boardlist") 
	public String product(HttpServletRequest request,HttpServletResponse response,
			Model model,Authentication authentication) {
		System.out.println("boardlist");
		
		//�츮������������ �α��� ������ �������̹Ƿ� id�� role������ ���� ���
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Constant.username = userDetails.getUsername();
		System.out.println(userDetails.getUsername());//hsk5410@naver.com (�α����� ���̵�)
		Collection<? extends GrantedAuthority>  authorities = authentication.getAuthorities();
		String auth = authorities.toString(); //role�� �� ���ڿ��� ��ȯ
		System.out.println(auth); //[ROLE_USER]����
		return "spj_boardlist";
	}			
}
