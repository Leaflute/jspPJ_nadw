package leafcom.common.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.common.service.CommonService;
import leafcom.common.service.CommonServiceImpl;

//http://localhost/jsp_pj_ndw/*.co
@WebServlet("*.co")
public class CommonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CommonController() {
        super();
    }
    
    CommonService service = new CommonServiceImpl();
    
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
		
		String uri = req.getRequestURI();	// /jsp_pj_nadw/*.co
		String contextPath = req.getContextPath();	// jsp_pj_nadw
		String url = uri.substring(contextPath.length()); // /?.co
		
		// 2단계. 요청 분석
		// 시작 페이지
		if (url.equals("/*.co") || url.equals("/index.co")) {
			System.out.println("[Member][cnt][url ==> /*.co]");
			
			viewPage = "/index.jsp";
			
			
		// 로그인 - 입력
		} else if(url.equals("/logIn.co")) {
			System.out.println("[Member][cnt][url ==> /logIn.co]");
			
			viewPage = "/common/logIn/logIn.jsp";
		
		// 로그인 - 처리
		} else if(url.equals("/logInAction.co")) {
			System.out.println("[Member][cnt][url ==> /logInAction.co]");
			service.loginAction(req, res);
			
			viewPage = "/common/logIn/logInAction.jsp";
			
			
		// 로그아웃
		} else if(url.equals("/logOut.co")) {
			System.out.println("[Member][cnt][url ==> /logOut.co]");
			
			// 세션 초기화
			req.getSession().invalidate();
			
			viewPage = "/index.jsp";
		
			
		// 회원가입 - 입력
		} else if(url.equals("/signIn.co")) {
			System.out.println("[Member][cnt][url ==> /signIn.co]");
			
			viewPage = "/common/signIn/signIn.jsp";
			           
		// 회원가입 - 이메일 중복 확인
		} else if(url.equals("/emailDupChk.co")) {
			System.out.println("[Member][cnt][url ==> /emailDupChk.co]");
			service.emailDupChk(req, res);
			
			viewPage = "/common/signIn/emailDupChk.jsp";
			
		// 회원가입 - 처리
		} else if(url.equals("/signInAction.co")) {
			System.out.println("[Member][cnt][url ==> /signInAction.co]");
			service.signInAction(req, res);
			
			viewPage = "/common/signIn/signInAction.jsp";
		
		// 마이페이지 이동
		} else if(url.equals("/myPageMain.co")) {
			System.out.println("[Member][cnt][url ==> /myPageMain.co]");
			
			viewPage = "/customer/myPageMain.jsp";	
			
		// 회원정보 조회 - 비밀번호 인증 페이지 로드
		} else if(url.equals("/viewInfo.co")) {
			System.out.println("[Member][cnt][url ==> /viewInfo.co]");
			
			viewPage = "/customer/myInfo/viewInfo.jsp";
		
		// 회원정보 조회 - 비밀번호 인증 처리
		} else if(url.equals("/viewInfoAction.co")) {
			System.out.println("[Member][cnt][url ==> /viewInfoAction.co]");
			service.viewMemInfoAction(req, res);
			
			viewPage = "/customer/myInfo/viewInfoAction.jsp";
			
		// 회원탈퇴 - 비밀번호 인증
		} else if(url.equals("/withrawMem.co")) {
			System.out.println("[Member][cnt][url ==> /withrawMem.co]");
		
			viewPage = "/customer/myInfo/withrawMem.jsp";
		
		// 회원탈퇴 - 처리
		} else if(url.equals("/withrawMemAction.co")) {
			System.out.println("[Member][cnt][url ==> /withrawMemAction.co]");
			service.withdrawMemAction(req, res);
			
			viewPage = "/customer/myInfo/withrawMemAction.jsp";
			
		// 회원정보 수정 - 처리
		} else if(url.equals("/updateMemInfoAction.co")) {
			System.out.println("[Member][cnt][url ==> /updateMemInfoAction.co]");
			service.updateMemInfoAction(req, res);
			
			viewPage = "/customer/myInfo/updateMemInfoAction.jsp";
		}	
			
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
	}
}