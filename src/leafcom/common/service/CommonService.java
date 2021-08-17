package leafcom.common.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommonService {
	
	// 로그인 처리
	public void loginAction(HttpServletRequest req, HttpServletResponse res);
	
	// 이메일 중복확인
	public void emailDupChk(HttpServletRequest req, HttpServletResponse res);
	
	// 회원가입 처리
	public void signInAction(HttpServletRequest req, HttpServletResponse res);
	
	// 회원탈퇴 처리
	public void withdrawMemAction(HttpServletRequest req, HttpServletResponse res);
	
	// 회원정보 출력
	public void viewMemInfoAction(HttpServletRequest req, HttpServletResponse res);
	
	// 회원정보 수정
	public void updateMemInfoAction(HttpServletRequest req, HttpServletResponse res);
}
