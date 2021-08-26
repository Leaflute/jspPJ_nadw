package leafcom.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.CustomerDAO;
import leafcom.dao.CustomerDAOImpl;
import leafcom.vo.CartVO;
import leafcom.vo.ItemVO;
import leafcom.vo.MemberVO;

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
	
	// 장바구니 리스트 호출
	@Override
	public void cartList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[cu][service][cartList()]");
		String meId = req.getParameter("meId");
		List<CartVO> cartList = dao.cartList(meId);
		
		req.setAttribute("cartList", cartList);
	}
	
	// 카트에 상품 추가
	@Override
	public void addCart(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[cu][service][addCart()]");
		int itId = Integer.parseInt(req.getParameter("itemId"));
		int amount = Integer.parseInt(req.getParameter("amount"));
		
		List<CartVO> sessionCartList = null;
		List<CartVO> memberCartList = null;
		
		// 세션에 멤버 정보가 존재할때 
		if(req.getSession().getAttribute("member")!=null) {
			MemberVO me = (MemberVO)req.getSession().getAttribute("member");
			String meId = me.getId();
			
			// 최초 로그인시 장바구니 세션이 존재했을 때 해당 리스트를 가지고 와서 DB에 추가 -> 장바구니 세션 삭제
			if(req.getSession().getAttribute("cartList")!=null) {
				sessionCartList = (List<CartVO>)req.getSession().getAttribute("cartList");
				memberCartList = sessionCartList;
		
				req.getSession().removeAttribute("cartList");
			
			// 이미 로그인 중이면 DB에 회원의 장바구니 리스트 추가(장바구니 세션이 존재하지 않음)
			} else {
				memberCartList = new ArrayList<CartVO>();
				for (CartVO vo : memberCartList) {
					vo.setAmount(amount);
					vo.setItId(itId);
					vo.setMeId(meId);
					vo.setRegDate(new Timestamp(System.currentTimeMillis()));
					vo.setCondition(0);
					memberCartList.add(vo);
				}	
			}
			int addCnt = dao.addCart(memberCartList,itId);
			
		// 세션에 멤버 정보가 존재하지 않을 때(세션에 장바구니 리스트 저장)	
		} else {
			// 장바구니 세션이 없으면 장바구니 세션을 추가
			if(req.getSession().getAttribute("cartList")==null) {
				sessionCartList = new ArrayList<CartVO>();
				
				// 세션 장바구니 리스트에 CartVO 정보 를 누적
				for (CartVO vo : sessionCartList) {
					vo.setRegDate(new Timestamp(System.currentTimeMillis()));
					vo.setItId(itId);
					vo.setAmount(amount);
					vo.setCondition(0);
					sessionCartList.add(vo);
				}
			
			// 장바구니 세션이 존재할 때
			} else {
				sessionCartList = (ArrayList<CartVO>)req.getSession().getAttribute("cartList");
				for (CartVO vo:sessionCartList) {
					// itemId가 동일한 것이 있으면 수량 누적
					if (vo.getItId()==itId) {
						vo.setAmount(amount+vo.getAmount());
					// 없으면 들어온 수량값을 장바구니 VO에 설정
					} else {
						vo.setAmount(amount);
					}
					vo.setRegDate(new Timestamp(System.currentTimeMillis()));
					vo.setItId(itId);
					vo.setCondition(0);
					sessionCartList.add(vo);
				}
			}
			req.getSession().setAttribute("cartList", sessionCartList);
		}
		
	}
	
	// 수량 조정
	@Override
	public void updateCart(HttpServletRequest req, HttpServletResponse res) {
		int caId = Integer.parseInt(req.getParameter("caId"));
		int amount = Integer.parseInt(req.getParameter("amount"));
		
		dao.updateCart(caId, amount);
	}
	
	// 삭제 요청
	@Override
	public void deleteCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}
	
	// 장바구니 구매
	@Override
	public void buyInCart(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	// 바로구매
	@Override
	public void buyNow(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	// 주소록
	@Override
	public void AddressList(HttpServletRequest req, HttpServletResponse res) {
	
	}
	
	// 주소록 추가
	@Override
	public void addAddress(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	// 주소록 수정
	@Override
	public void updateAddress(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	// 주소록 삭제
	@Override
	public void deleteAddress(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	// 주문목록
	@Override
	public void orderList(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	// 주문상태 변경(구매확인, 환불요청, 구매확정)
	@Override
	public void updateOrder(HttpServletRequest req, HttpServletResponse res) {
		
	}


}
