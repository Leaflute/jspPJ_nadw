package leafcom.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.service.AdminService;
import leafcom.service.AdminServiceImpl;
import leafcom.service.CommonService;
import leafcom.service.CommonServiceImpl;
import leafcom.util.ImageUploaderHandler;

//http://localhost/jsp_pj_ndw/*.co
@WebServlet("*.ad")
@MultipartConfig(location = "D:\\Dev88\\workspace\\jsp_88_ndw\\WebContent\\upload", fileSizeThreshold = 1024
* 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5)
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMG_UPLOAD_DIR = "D:\\\\Dev88\\\\workspace\\\\jsp_88_ndw\\\\WebContent\\\\upload";
	
	private ImageUploaderHandler uploader;
	
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
		
		String uri = req.getRequestURI();	
		String contextPath = req.getContextPath();	
		String url = uri.substring(contextPath.length());
		AdminService service = new AdminServiceImpl(); 
		
		// 2단계. 요청 분석
		// 시작 페이지
		if (url.equals("/*.ad") || url.equals("/dashboardMain.ad")) {
			System.out.println("[ad][cnt][url ==> /*.ad]");
			
			viewPage = "/admin/dashboardMain.jsp";
		
		// 관리자 상품 리스트
		} else if (url.equals("/itemManagement.ad")) {
			System.out.println("[ad][cnt][url ==> /itemManagement.ad]");
			
			service.itemList(req, res);
			
			viewPage = "/admin/item/itemManagement.jsp";
		
		// 상품 상세 페이지
		} else if (url.equals("/itemDetail.ad")) {
			System.out.println("[ad][cnt][url ==> /itemManagement.ad]");
			
			service.itemDetail(req, res);
			
			viewPage = "/admin/item/itemDetail.jsp";
			
		// 상품 추가 입력
		} else if (url.equals("/addItem.ad")) {
			System.out.println("[ad][cnt][url ==> /itemManagement.ad]");
			
			viewPage = "/admin/item/addItem.jsp";
	
		// 상품 추가 처리
		} else if (url.equals("/addItemAction.ad")) {
			System.out.println("[ad][cnt][url ==> /addItemAction.ad]");
			
			// 이미지 업로드 설정 시작
			String contentType = req.getContentType();
			if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
				 uploader = new ImageUploaderHandler(); // image uploader 핸들러 호출
				 uploader.setUploadPath(IMG_UPLOAD_DIR); // img 경로
				 uploader.imageUpload(req, res);
			}
			// 이미지 업로드 설정 끝
			
			service.addItem(req, res);
			
			viewPage = "/admin/item/addItemAction.jsp";
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
		
	}
}