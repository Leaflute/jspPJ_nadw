package leafcom.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.service.CommonService;
import leafcom.service.CommonServiceImpl;

//http://localhost/jsp_pj_ndw/*.co
@WebServlet("*.ad")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminController() {
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
		if (url.equals("/*.ad") || url.equals("/dashboardMain.ad")) {
			System.out.println("[Member][cnt][url ==> /*.ad]");
			
			viewPage = "/admin/dashboardMain.jsp";
			
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
		}
	}
}