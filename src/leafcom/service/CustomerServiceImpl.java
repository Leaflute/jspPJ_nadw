package leafcom.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.CustomerDAO;
import leafcom.dao.CustomerDAOImpl;
import leafcom.vo.CartVO;
import leafcom.vo.ItemVO;
import leafcom.vo.MemberVO;

public class CustomerServiceImpl implements CustomerService, Serializable {
	
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
		
		ItemVO vo = dao.getItemDetail(itemId);
		
		req.setAttribute("dto", vo);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("categoryId", categoryId);
		
	}
	
	// 장바구니 리스트 호출
	@Override
	public void cartList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[cu][service][cartList()]");
		List<CartVO> cartList = null;
		String meId = "";
		// 로그인 세션의 존재유무 판단
		if(req.getSession().getAttribute("member")!=null) {
			MemberVO member = (MemberVO)req.getSession().getAttribute("member");
			meId = member.getId();
			cartList = dao.cartList(meId);
		// 없으면 세션에 저장된 카트를 가져온다
		} else {
			cartList = (ArrayList<CartVO>)req.getSession().getAttribute("cartList");
		}
		
		req.setAttribute("cartList", cartList);
	}
	
	// 카트에 상품 추가
	@Override
	public void addCart(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[cu][service][addCart()]");
		int itId = req.getParameter("itemId")==null ? 0 : Integer.parseInt(req.getParameter("itemId"));
		int amount = req.getParameter("amount")==null ? 0 : Integer.parseInt(req.getParameter("amount"));
		
		System.out.println("itid:" + itId);
		System.out.println("amount:" + amount);
		List<CartVO> sessionCartList = null;
		List<CartVO> memberCartList = null;
		
		// id를 매개변수로 cVo.set에 필요한 상품 정보 가져옴(iVo)
		ItemVO iVo = dao.getItemDetail(itId);
		int price = iVo.getPrice();
		String itName = iVo.getItemName();
		String smallImg = iVo.getSmallImg();
		
		// 세션에 멤버 정보가 존재할때 
		if(req.getSession().getAttribute("member")!=null) {
			System.out.println("로그인 중 DB로감");
			MemberVO me = (MemberVO)req.getSession().getAttribute("member");
			String meId = me.getId();
			
			// 최초 로그인시 장바구니 세션이 존재했을 때 해당 리스트를 가지고 와서 DB에 추가 -> 장바구니 세션 삭제
			if(req.getSession().getAttribute("cartList")!=null) {
				System.out.println("최초 로그인 장바구니 리스트가 존재");
				sessionCartList = (ArrayList<CartVO>)req.getSession().getAttribute("cartList");
				memberCartList = sessionCartList;
			
			// 이미 로그인 중이면 DB에 회원의 장바구니 리스트 추가(장바구니 세션이 존재하지 않음)
			} else {
				System.out.println("로그인 중 DB에 장바구니 리스트");
				
				memberCartList = dao.cartList(meId);
				CartVO cVo = new CartVO();
				cVo.setAmount(amount);
				cVo.setItId(itId);
				cVo.setMeId(meId);
				cVo.setPrice(price);
				cVo.setItName(itName);
				cVo.setSmallimg(smallImg);
				cVo.setRegDate(new Timestamp(System.currentTimeMillis()));
				cVo.setCondition(0);	// 견적서가 아닌 일반 장바구니
				memberCartList.add(cVo);
			}
			int addCnt = dao.addCart(memberCartList,itId);
			req.getSession().removeAttribute("cartList");
			
		// 세션에 멤버 정보가 존재하지 않을 때(세션에 장바구니 리스트 저장)	
		} else {
			// 장바구니 세션이 없으면 장바구니 세션을 추가
			if(req.getSession().getAttribute("cartList")==null) {
				System.out.println("비회원 장바구니");
				sessionCartList = new ArrayList<CartVO>();
				
				// 세션 장바구니 리스트에 CartVO 정보 를 누적
				CartVO cVo = new CartVO();
				cVo.setCaId(0);	// 세션에 들어갔기 때문에 PK일 필요가 없음 전부 0으로 처리
				cVo.setRegDate(new Timestamp(System.currentTimeMillis()));
				cVo.setPrice(price);
				cVo.setItName(itName);
				cVo.setSmallimg(smallImg);
				cVo.setItId(itId);
				cVo.setAmount(amount);
				cVo.setCondition(0);
				sessionCartList.add(cVo);
			
			// 장바구니 세션이 존재할 때
			} else {
				System.out.println("비회원 장바구니 누적");
				sessionCartList = (ArrayList<CartVO>)req.getSession().getAttribute("cartList");
				Iterator<CartVO> itr = sessionCartList.iterator();
				while (itr.hasNext()){
					CartVO cVo = itr.next();
					// itemId가 동일한 것이 있으면 수량 누적
					if (cVo.getItId()==itId) {
						cVo.setAmount(amount+cVo.getAmount());
						System.out.println("세션-상품수량바꿈");
					// 없으면 들어온 수량값을 장바구니 VO에 설정
					} else {
						cVo = new CartVO();
						cVo.setAmount(amount);
						cVo.setRegDate(new Timestamp(System.currentTimeMillis()));
						cVo.setItId(itId);
						cVo.setCondition(0);
						sessionCartList.add(cVo);
						System.out.println("세션-새 상품추가");
					}
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
