package leafcom.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.service.CustomerService;
import leafcom.service.CustomerServiceImpl;

//http://localhost/jsp_pj_ndw/*.co
@WebServlet("*.cu")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CustomerController() {
        super();
    }
    
    CustomerService service = new CustomerServiceImpl();
    
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
		
		// 상품 카테고리 
		if(url.equals("/itemList.cu")) {
			System.out.println("[cu][cnt][url ==> /itemList.cu]");
			
			service.itemList(req, res);
			
			viewPage = "/customer/item/list.jsp";
		
		// 상품 상세
		} else if(url.equals("/itemDetail.cu")) {
			System.out.println("[cu][cnt][url ==> /itemDetail.cu]");
			
			service.itemDetail(req, res);
			
			viewPage = "/customer/item/detail.jsp";
		
		// 장바구니 추가 후 리스트 페이지 로드 여부 확인 팝업창
		} else if(url.equals("/cartPop.cu")) {
			System.out.println("[cu][cnt][url ==> /addcart.cu]");
			
			service.addCart(req, res);
			
			viewPage = "/customer/item/cartPop.jsp";
		
		// 장바구니 리스트
		} else if(url.equals("/cartList.cu")) {
			System.out.println("[cu][cnt][url ==> /cartList.cu]");
			
			service.cartList(req, res);
			
			viewPage = "/customer/cart/list.jsp";
			
		// 장바구니 추가
		} else if(url.equals("/addcart.cu")) {
			System.out.println("[cu][cnt][url ==> /addcart.cu]");
			
			service.itemDetail(req, res);
			
			viewPage = "/customer/cart/list.jsp";
		
		// 장바구니 수량 변경
		} else if(url.equals("/addcart.cu")) {
			System.out.println("[cu][cnt][url ==> /addcart.cu]");
			
			service.updateCart(req, res);
			
			viewPage = "/customer/cart/list.jsp";
			
		// 장바구니 삭제	
		} else if(url.equals("/deleteCart.cu")) {
			System.out.println("[cu][cnt][url ==> /addcart.cu]");
			
			service.deleteCart(req, res);
			
			viewPage = "/customer/item/cartList.jsp";
		
		// 구매 페이지 로드
		} else if(url.equals("/buyNow.cu")) {
			System.out.println("[cu][cnt][url ==> /buyNow.cu]");
			
			if(req.getSession().getAttribute("member")==null) {
				viewPage = "/common/login/login.jsp";
			} else {
				viewPage = "/customer/item/buyNow.jsp";
			}
		}
			
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
		
	}
}