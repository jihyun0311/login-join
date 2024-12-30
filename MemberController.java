package com.myspring.test.member;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/member")
@Controller
public class MemberController {
	@Autowired(required = true)
	MemberDAO memberDAO;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> login(@RequestParam String id, @RequestParam String pw){
		Map<String, String> response = new LinkedHashMap<>();
		response.put("id", id);
		boolean check = memberDAO.login(id,  pw);
		if(check) {
			response.put("로그인", "성공");
		}else {
			response.put("로그인", "실패");
		}
		return response;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> insert(@RequestParam String id, String pw, String pwch, String name, String email){
		Map<String, String> response = new LinkedHashMap<>();
		response.put("id", id);
		boolean idCheck = memberDAO.idCheck(id);
		boolean emailCheck = memberDAO.emailCheck(email);
		if(!idCheck) {
			response.put("실패", "아이디중복");
			return response;
		}else if(pw.equals(pwch)) {
			response.put("실패", "비밀번호 불일치");
			return response;
		}else if(name==null || name.trim().isEmpty()) {
			response.put("실패", "이름 입력 오류");
			return response;
		}else if(!emailCheck) {
			response.put("실패", "이메일중복");
			return response;
		}else {
			response.put("성공", "회원가입 완료");
			memberDAO.insert(id, pw, name, email);
			return response;
		}
	}
}
