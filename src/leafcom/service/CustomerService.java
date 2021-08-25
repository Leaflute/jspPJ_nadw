package leafcom.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomerService {
	
	// 상품 리스트 조회
	public void itemList(HttpServletRequest req, HttpServletResponse res);
	
	// 카테고리 맵
	public void categoryMap(HttpServletRequest req, HttpServletResponse res);
		
	// 상품 상세 페이지 조회
	public void itemDetail(HttpServletRequest req, HttpServletResponse res);
	
	// 장바구니 리스트
	public void cartList(HttpServletRequest req, HttpServletResponse res);

	// 장바구니 추가
	public void addCart(HttpServletRequest req, HttpServletResponse res);
	
	// 장바구니 정보 수정
	public void updateCart(HttpServletRequest req, HttpServletResponse res);
	
	// 장바구니 삭제
	public void deleteCart(HttpServletRequest req, HttpServletResponse res);
	
	// 장바구니 구매
	public void buyInCart(HttpServletRequest req, HttpServletResponse res);
	
	// 바로 구매
	public void buyNow(HttpServletRequest req, HttpServletResponse res); 
	
	// 주문 리스트
	public void orderList(HttpServletRequest req, HttpServletResponse res); 
	
	// 주문 수정(구매요청, 반품요청, 구매확정, 환불요청)
	public void updateOrder(HttpServletRequest req, HttpServletResponse res);

	void AddressList(HttpServletRequest req, HttpServletResponse res);

	void addAddress(HttpServletRequest req, HttpServletResponse res);

	void updateAddress(HttpServletRequest req, HttpServletResponse res);

	void deleteAddress(HttpServletRequest req, HttpServletResponse res); 
	
}
