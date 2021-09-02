package leafcom.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.vo.ReportVO;

public interface AdminService {
	
	// 상품 리스트
	public void itemList(HttpServletRequest req, HttpServletResponse res);
	
	// 카테고리 맵
	public void categoryMap(HttpServletRequest req, HttpServletResponse res);
	
	// 상품 세부 정보
	public void itemDetail(HttpServletRequest req, HttpServletResponse res);
	
	// 상품 추가
	public void addItem(HttpServletRequest req, HttpServletResponse res); 
	
	// 상품 정보 수정
	public void updateItem(HttpServletRequest req, HttpServletResponse res);
	
	// 상품 삭제
	public void deleteItem(HttpServletRequest req, HttpServletResponse res);
	
	// 주문 리스트
	public void orderList(HttpServletRequest req, HttpServletResponse res);
	
	// 주문 상태 변경(주문승인, 주문취소, 취소승인, 배송중, 환불승인) 
	public void updateOrder(HttpServletRequest req, HttpServletResponse res);
	
	// 5일 결산
	public void fiveDayReport(HttpServletRequest req, HttpServletResponse res);
	
}
