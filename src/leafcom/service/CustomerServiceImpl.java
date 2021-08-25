package leafcom.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.CustomerDAO;
import leafcom.dao.CustomerDAOImpl;
import leafcom.vo.CartVO;
import leafcom.vo.ItemVO;

public class CustomerServiceImpl implements CustomerService {
	
	CustomerDAO dao = CustomerDAOImpl.getInstance();

	// 상품 리스트 
	@Override
	public void itemList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[ad][service][itemList()]");
		int categoryId = 0;
		
		if (req.getParameter("categoryId")!=null) {
			categoryId = Integer.parseInt(req.getParameter("categoryId"));
		}
		System.out.println("categoryId: " + categoryId);
		
		String categoryName = "";
			
		int pageSize = 5; 		// 한 페이지당 출력할 글 개수
		int pageBlock = 5;		// 한 블럭당 페이지 개수
		
		int cnt = 0;			// 게시글 개수
		int start = 0;			// 현재 페이지 시작 게시글
		int end = 0;			// 현제 페이지 마지막 게시글
		int number = 0; 		// 출력용 글 번호
		String pageNum = ""; 	// 페이지 번호
		int currentPage = 0;	// 현재 페이지
 
		int pageCount = 0;		// 페이지 개수
		int startPage = 0; 		// 시작페이지
		int endPage = 0;		// 마지막 페이지
		
		cnt = dao.getItemCnt(categoryId);
		
		pageNum = req.getParameter("pageNum");
		
		if(pageNum == null) {
			pageNum = "1";		// 첫 페이지를 1페이지로 지정
		}
		
		// 상품 30건 기준
		currentPage = Integer.parseInt(pageNum);
		System.out.println("currentPage: " + currentPage);
		
		// 페이지 개수 6 = (30 / 5) + (0) : 나머지가 있으면 1페이지 추가
		pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
		
		// 현재 페이지의 시작 글 번호(페이지별)
		// start = (currentPage - 1) * pageSize + 1;
		start = (currentPage - 1) * pageSize + 1;
		
		// 현재 페이지의 마지막 글 번호(페이지별)
		// end = start + pageSize - 1;
		end = start + pageSize - 1;
		System.out.println("start: " + start);
		System.out.println("end: " + end);
		
		// 출력용 글 번호
		// number = cnt - (currentPage - 1) * pageSize;
		number = cnt - (currentPage - 1) * pageSize;
		
		System.out.println("number:" + number);
		
		// 시작 페이지
		// startPage = (currentPage / pageBlock) * pageBlock + 1;
		startPage = (currentPage / pageBlock) * pageBlock + 1;
		if (currentPage % pageBlock == 0) startPage -= pageBlock;
		System.out.println("startPage: " + startPage);
		
		// 마지막 페이지
		endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) endPage = pageCount;
		
		System.out.println("endPage: " + endPage);
		System.out.println("===================");
		
		ArrayList<ItemVO> itemDtos= null;
		HashMap<Integer, String> categoryMap = dao.getCategoryMap();
		categoryName = dao.getCategoryName(categoryId);
		
		if(cnt > 0) {
			itemDtos = dao.getItemList(start, end, categoryId);
		}
		
		req.setAttribute("itemDtos", itemDtos);
		req.setAttribute("categoryMap", categoryMap);
		req.setAttribute("cnt", cnt);		
		req.setAttribute("number", number);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("categoryId", categoryId);
		req.setAttribute("categoryName", categoryName);
		
		if (cnt > 0) {
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
			req.setAttribute("pageBlock", pageBlock);
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("currentPage", currentPage);
		}
	}

	// 카테고리 맵 가져오기
	@Override
	public void categoryMap(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[cu][service][categoryMap()]");
		HashMap<Integer, String> categoryMap = dao.getCategoryMap();
		req.setAttribute("categoryMap", categoryMap);
	}
	
	// 상품 상세 정보
	@Override
	public void itemDetail(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[ad][service][itemDetail()]");
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));
		int itemId = Integer.parseInt(req.getParameter("itemId"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		ItemVO vo = dao.getItemDetail(itemId,categoryId);
		
		req.setAttribute("dto", vo);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("categoryId", categoryId);
		
	}

	@Override
	public void cartList(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}
	
	// 카트에 상품 추가
	@Override
	public void addCart(HttpServletRequest req, HttpServletResponse res) {
		
		List<CartVO> sessionCartList = null;
		List<CartVO> memberCartList = null;
		// 세션에 멤버 정보가 존재하면 그대로 장바구니에 추가
		// 세션에 멤버정보가 존재하지 않으면 장바구니 세션을 추가
		if(req.getSession().getAttribute("cartList")==null) {
			sessionCartList = new ArrayList<CartVO>();
		}
		
	}

	@Override
	public void updateCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyInCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyNow(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDestination(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDestination(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDestination(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderList(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOrder(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refundList(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}


}
