package leafcom.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommonService {
	
	// 로그인 처리
	public void loginAction(HttpServletRequest req, HttpServletResponse res);
	
	// 아이디 중복확인
	public void idDupChk(HttpServletRequest req, HttpServletResponse res);
	
	// 회원가입 처리
	public void signInAction(HttpServletRequest req, HttpServletResponse res);
	
	// 회원탈퇴 처리
	public void withdrawMemAction(HttpServletRequest req, HttpServletResponse res);
	
	// 회원정보 출력
	public void viewMemInfoAction(HttpServletRequest req, HttpServletResponse res);
	
	// 회원정보 수정
	public void updateMemInfoAction(HttpServletRequest req, HttpServletResponse res);
	
	// 상품 리스트 조회
	public void itemList(HttpServletRequest req, HttpServletResponse res);
	
	// 상품 상세 페이지 조회
	public void itemDetail(HttpServletRequest req, HttpServletResponse res);

}
