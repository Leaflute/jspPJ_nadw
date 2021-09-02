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
@MultipartConfig(
		location = "D:\\Dev88\\workspace\\jsp_pj_ndw\\WebContent\\asset\\uploaded", 
		fileSizeThreshold = 1024 * 1024, 
		maxFileSize = 1024 * 1024 * 5 * 5, 
		maxRequestSize = 1024 * 1024 * 5 * 5)
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMG_UPLOAD_DIR = "D:\\\\Dev88\\\\workspace\\\\jsp_pj_ndw\\\\WebContent\\\\asset\\\\uploaded";
	
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
			System.out.println("[ad][cnt][url ==> /itemDetail.ad]");
			
			service.itemDetail(req, res);
			
			viewPage = "/admin/item/itemDetail.jsp";
			
		// 상품 추가 입력
		} else if (url.equals("/addItem.ad")) {
			System.out.println("[ad][cnt][url ==> /itemManagement.ad]");
			
			service.categoryMap(req, res);
			
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
		
		// 상품 수정 입력
		} else if (url.equals("/updateItem.ad")) {
			System.out.println("[ad][cnt][url ==> /updateItem.ad]");
			service.categoryMap(req, res);
			service.itemDetail(req, res);
			
			viewPage = "/admin/item/updateItem.jsp";	
		
		// 상품 수정 처리	
		} else if (url.equals("/updateItemAction.ad")) {
		System.out.println("[ad][cnt][url ==> /updateItemAction.ad]");
		
			// 이미지 업로드 설정 시작
			String contentType = req.getContentType();
			if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
				 uploader = new ImageUploaderHandler(); // image uploader 핸들러 호출
				 uploader.setUploadPath(IMG_UPLOAD_DIR); // img 경로
				 uploader.imageUpload(req, res);
			}
			// 이미지 업로드 설정 끝
		
			service.updateItem(req, res);
		
			viewPage = "/admin/item/updateItemAction.jsp";
		
		// 상품 삭제 확인 페이지 로드
		} else if (url.equals("/deleteItem.ad")) {
			System.out.println("[ad][cnt][url ==> /deleteItem.ad]");
			
			req.setAttribute("itemId", Integer.parseInt(req.getParameter("itemId")));
			req.setAttribute("pageNum", Integer.parseInt(req.getParameter("pageNum")));
			req.setAttribute("categoryId", Integer.parseInt(req.getParameter("categoryId")));
			
			service.itemDetail(req, res);
			
			viewPage = "/admin/item/deleteItem.jsp";
		
		// 상품 삭제 처리
		} else if (url.equals("/deleteItemAction.ad")) {
			System.out.println("[ad][cnt][url ==> /deleteItemAction.ad]");
			
			service.deleteItem(req, res);
			
			viewPage = "/admin/item/deleteItemAction.jsp";
		
		// 주문 리스트 확인
		} else if (url.equals("/orderList.ad")) {
			System.out.println("[ad][cnt][url ==> /orderList.ad]");
			
			service.orderList(req, res);
			
			viewPage = "/admin/order/list.jsp";
		
		// 주문 상태 변경
		} else if (url.equals("/updateOrder.ad")) {
			System.out.println("[ad][cnt][url ==> /updateOrder.ad]");
			
			service.updateOrder(req, res);
			service.orderList(req, res);
			
			viewPage = "/admin/order/updateAction.jsp";
		
		// 결산 페이지 로드
		} else if (url.equals("/report.ad")) {
			System.out.println("[ad][cnt][url ==> /report.ad]");
			
			service.fiveDayReport(req, res);
			
			viewPage = "/admin/report/report.jsp";	
		}
			
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
		
	}
}