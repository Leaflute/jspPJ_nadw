package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.MemberService;
import member.service.MemberServiceImpl;

//http://localhost/jsp_pj_ndw/*.mem
@WebServlet("*.me")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberController() {
        super();
    }
    
    MemberService service = new MemberServiceImpl();
    
    // 1단계. HTTP로부터 요청 받음
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		action(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doGet(req, res);
	}
	
	protected void action(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		// UTF-8 인코딩
		req.setCharacterEncoding("UTF-8");
		
		String viewPage= "";
		
		String uri = req.getRequestURI();	// /jsp_pj_nadw/*.me
		String contextPath = req.getContextPath();	// jsp_pj_nadw
		String url = uri.substring(contextPath.length()); // /?.me
		
		// 2단계. 요청 분석
		// 시작 페이지
		if (url.equals("/*.me") || url.equals("/index.me")) {
			System.out.println("[Member][cnt] url ==> /*.me]");
			
			viewPage = "/index.jsp";
			
			
		// 로그인 - 입력
		} else if(url.equals("/logIn.me")) {
			System.out.println("[Member][cnt] url ==> /logIn.me]");
			
			viewPage = "/member/logIn.jsp";
		
		// 로그인 - 처리
		} else if(url.equals("/logInAction.me")) {
			System.out.println("[Member][cnt] url ==> /logInAction.me]");
			service.loginAction(req, res);
			
			viewPage = "/member/logInAction.jsp";
			
			
		// 로그아웃
		} else if(url.equals("/logOut.me")) {
			
			viewPage = "/index.jsp";
		
			
		// 회원가입 - 입력
		} else if(url.equals("/signIn.me")) {
			System.out.println("[Member][cnt] url ==> /signIn.me]");
			
			viewPage = "/member/signIn.jsp";
			           
		// 회원가입 - 이메일 중복 확인
		} else if(url.equals("/emailDupChk.me")) {
			System.out.println("[Member][cnt] url ==> /emailDupChk.me]");
			service.confirmEmail(req, res);
			
			viewPage = "/member/emailDupChk.jsp";
			
		// 회원가입 - 처리
		} else if(url.equals("/signInAction.me")) {
			System.out.println("[Member][cnt] url ==> /signInAction.me]");
			service.signInAction(req, res);
			
			viewPage = "/member/signInAction.jsp";
		
			
		// 회원탈퇴 - 비밀번호 인증
		} else if(url.equals("/.me")) {
			viewPage = "";
		
		// 회원탈퇴 - 처리
		} else if(url.equals("/.me")) {
			viewPage = "";
		
		
		// 회원정보 수정 - 인증 	
		} else if(url.equals("/.me")) {
			viewPage = "";
			
		// 회원정보 - 입력
		} else if(url.equals("/.me")) {
			viewPage = "";
			
		// 회원정보 - 입력
		} else if(url.equals("/.me")) {
			viewPage = "";
		}
			
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
	}
}