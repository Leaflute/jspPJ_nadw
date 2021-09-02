package leafcom.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.CustomerDAO;
import leafcom.dao.CustomerDAOImpl;
import leafcom.util.Code;
import leafcom.vo.AddressVO;
import leafcom.vo.CartVO;
import leafcom.vo.ItemVO;
import leafcom.vo.MemberVO;
import leafcom.vo.OrderVO;

public class CustomerServiceImpl implements CustomerService {
	
	CustomerDAO dao = CustomerDAOImpl.getInstance();

	// 상품 리스트 
	@Override
	public void itemList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[cu][service][itemList()]");
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
		int categoryId = req.getParameter("categoryId")==null ? 1 : Integer.parseInt(req.getParameter("categoryId"));
		int itemId = Integer.parseInt(req.getParameter("itemId"));
		int pageNum = req.getParameter("pageNum")==null ? 1 : Integer.parseInt(req.getParameter("pageNum"));
		
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
			System.out.println("id:" + member.getId());
			cartList = dao.cartList(member.getId());
			
		// 없으면 세션에 저장된 카트를 가져온다
		} else {
			cartList = (ArrayList<CartVO>)req.getSession().getAttribute("cartList");
			System.out.println(cartList);
		}
		
		req.setAttribute("cartList", cartList);
	}
	
	// 최초 로그인시 세션에 있는 장바구니 리스트를 DB에 추가
	@Override
	public void loginAddCart(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[cu][service][loginAddCart()]");
		List<CartVO> sCartList = (ArrayList<CartVO>)req.getSession().getAttribute("cartList");
		List<Integer> itemIdList = null;
		
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		String meId = mVo.getId();
		int insertCnt = 0; 
		int updateCnt = 0;
		int amount = 0;
		int caId = 0;

		Iterator<CartVO> itr = sCartList.iterator();
		
		// 장바구니 리스트에 들어간 상품 리스트 목록이 있다면
		if (dao.cartList(meId)!=null) {
			// 회원의 장바구니 상품 목록을 가져와서
			itemIdList = dao.getItIdList(meId);
			System.out.println("itemIdList: " + itemIdList.toString());
		
			while (itr.hasNext()) {
				CartVO cVo = itr.next();
				cVo.setMeId(meId);
				// 해당 상품 id가 있는지 여부 판단
				if (itemIdList.contains(cVo.getItId())) {
					// DB에 저장되어있던 itId와 meId에 해당하는 수량
					amount = dao.getCartInfo2(cVo.getItId(),meId).getAmount();
					caId = dao.getCartInfo2(cVo.getItId(),meId).getCaId();
					// DB에 저장되어 있던 상품의 수량에 누적
					updateCnt += dao.updateCart(caId, amount+cVo.getAmount());
				} else {
					insertCnt += dao.insertCart(cVo);
				}
			}
			// 장바구니 리스트 세션 제거
			req.getSession().removeAttribute("cartList");
		
		// 회원 장바구니가 비어있을 때
		} else {
			while(itr.hasNext()) {
				CartVO cVo = itr.next();
				cVo.setMeId(meId);
				insertCnt += dao.insertCart(cVo);
			}
		}
		System.out.println("addCnt: " + insertCnt);
		System.out.println("updateCnt: " + updateCnt);
	}
	
	// 상세페이지나 리스트 페이지에서 버튼을 눌렀을 때 카트에 상품 추가
	@Override
	public void addCart(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[cu][service][addCart()]");
		int itId = req.getParameter("itemId")==null ? 0 : Integer.parseInt(req.getParameter("itemId"));
		int amount = req.getParameter("amount")==null ? 0 : Integer.parseInt(req.getParameter("amount"));
		
		int insertCnt=0;
		
		System.out.println("itid:" + itId);
		System.out.println("amount:" + amount);
 		List<CartVO> sessionCartList = null;
		
		itId = Integer.parseInt(req.getParameter("itemId"));
		// id를 매개변수로 cVo.set에 필요한 상품 정보 가져옴(iVo)
		ItemVO iVo = dao.getItemDetail(itId);
		int price = iVo.getPrice();
		String itName = iVo.getItemName();
		String smallImg = iVo.getSmallImg();
		
		// 세션 또는 DB에 올라갈 작은바구니 생성
		CartVO cVo = new CartVO();
		cVo.setRegDate(new Timestamp(System.currentTimeMillis()));
		cVo.setPrice(price);
		cVo.setItName(itName);
		cVo.setMeId("");
		cVo.setSmallImg(smallImg);
		cVo.setItId(itId);
		cVo.setAmount(amount);
		cVo.setCondition(0);	// 0: 일반 장바구니, 1: 견적서(미구현)
		System.out.println(cVo);
		
		// 세션에 멤버 정보가 존재할때 
		if(req.getSession().getAttribute("member")!=null) {
			System.out.println("로그인 중 - DB로감");
			MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
			String meId = mVo.getId();
			cVo.setMeId(meId);
			
			// 장바구니 리스트가 있을 때
			if (dao.cartList(meId)!=null) {
				List<CartVO> memberCartList = dao.cartList(meId);
				Iterator<CartVO> itr = memberCartList.iterator();
				while (itr.hasNext()) {
					CartVO icVo = itr.next();
					// itemId가 동일한 것이 있으면 수량 누적
					if (icVo.getItId()==itId) {
						dao.updateCart(icVo.getCaId(), icVo.getAmount()+amount);
						System.out.println("DB-상품수량바꿈");
						break;
					// 없으면 들어온 DB에 새 상품 추가
					} else {
						insertCnt = dao.insertCart(cVo);
						System.out.println("DB-새 상품추가");
						break;
					}
				}
			// 없을 때
			} else {
				insertCnt = dao.insertCart(cVo);
				System.out.println("DB-새 상품추가");
			}
			
		// 세션에 멤버 정보가 존재하지 않을 때(세션에 장바구니 리스트 저장)	
		} else {
			cVo.setCaId(dao.cartSeq());
			// 장바구니 세션이 없으면 장바구니 세션을 추가
			if(req.getSession().getAttribute("cartList")==null) {
				System.out.println("비회원 장바구니");
				sessionCartList = new ArrayList<CartVO>();
				
				// 세션 장바구니 리스트에 CartVO 정보를 누적
				sessionCartList.add(cVo);
				System.out.println(sessionCartList);
			
			// 장바구니 세션이 존재할 때
			} else {
				System.out.println("비회원 장바구니 누적");
				sessionCartList = (ArrayList<CartVO>)req.getSession().getAttribute("cartList");
				Iterator<CartVO> itr = sessionCartList.iterator();
				while (itr.hasNext()) {
					CartVO icVo = itr.next();
					// itemId가 동일한 것이 있으면 수량 누적
					if (icVo.getItId()==itId) {
						icVo.setAmount(amount+icVo.getAmount());
						System.out.println("세션-상품수량바꿈");
						break;
					// 없으면 들어온 수량값을 장바구니 VO에 설정
					} else {
						sessionCartList.add(cVo);
						System.out.println("세션-새 상품추가");
						break;
					}
				}
			}
			req.getSession().setAttribute("cartList", sessionCartList);
		}
	}
	
	// 수량 변경시
	@Override
	public void updateCart(HttpServletRequest req, HttpServletResponse res) {
		int caId = Integer.parseInt(req.getParameter("caId"));
		int amount = Integer.parseInt(req.getParameter("amount"));
		
		// 로그인 했을 때
		if (req.getSession().getAttribute("member")!=null) {
			MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
			String meId = mVo.getId();
			dao.updateCart(caId, amount);
		// 로그인 안 했을 때
		} else {
			List<CartVO> sessionCartList = (List<CartVO>)req.getSession().getAttribute("cartList");
			Iterator<CartVO> itr = sessionCartList.iterator();
			int index = 0;
			while (itr.hasNext()) {
				CartVO cVo = itr.next();
				if (cVo.getCaId()==caId) {
					cVo.setAmount(amount+cVo.getAmount());
					sessionCartList.set(index, cVo);
					break;
				}
				index++;
			}
		req.getSession().setAttribute("cartList", sessionCartList);	
		}
	}
	
	// 장바구니 삭제(개별 삭제)
	public void deleteCart(HttpServletRequest req, HttpServletResponse res) {
		int caId = Integer.parseInt(req.getParameter("caId"));
		
		if (req.getSession().getAttribute("member")!=null) {
			System.out.println("DB에서 삭제");
			dao.deleteCart(caId);
		} else {
			List<CartVO> sessionCartList = (List<CartVO>)req.getSession().getAttribute("cartList");
			Iterator<CartVO> itr = sessionCartList.iterator();
			while (itr.hasNext()) {
				CartVO cVo = itr.next();
				if(cVo.getCaId()==caId) {
					sessionCartList.remove(cVo);
					break;
				}
			}
		
			// 장바구니가 비었을 때 세션 삭제처리
			if (sessionCartList.isEmpty()) {
				req.getSession().removeAttribute("cartList");
			} else {
				req.getSession().setAttribute("cartList", sessionCartList);
			}
		
		}
	}
	
	// 장바구니 삭제(리스트 삭제)
	@Override
	public void deleteCartList(HttpServletRequest req, HttpServletResponse res) {
		String[] strArrCaId = req.getParameterValues("caIdArray")==null ? new String[1] : req.getParameterValues("caIdArray") ;
		System.out.println("strArrCaId" + Arrays.toString(strArrCaId));
		// 무조건 공백을 반영하는 0번 index의 값을 0으로 바꾸고
		strArrCaId[0] = "0";
		// 0번 인덱스의 값을 제외한 채 String배열을 int배열로 바꾸는 스트림
		int[] arrCaId = Arrays.stream(strArrCaId).skip(1).mapToInt(Integer::parseInt).toArray(); 
		System.out.println("arrCaId");
		
		// 로그인 했을 때
		if (req.getSession().getAttribute("member")!=null) {
			System.out.println("DB 상품 삭제");
			for(int caId : arrCaId) {
				dao.deleteCart(caId);
			}
			
		// 로그인 안 했을 때
		} else {
			System.out.println("세션 상품 삭제");
			List<CartVO> sessionCartList = (List<CartVO>)req.getSession().getAttribute("cartList");
			Iterator<CartVO> itr = sessionCartList.iterator();
			while (itr.hasNext()) {
				CartVO cVo = itr.next();
				if (Arrays.asList(arrCaId).contains(cVo.getCaId())) {
					sessionCartList.remove(cVo);
				}
			}
			
			if (sessionCartList.isEmpty()) {
				req.getSession().removeAttribute("cartList");
			} else {

				req.getSession().setAttribute("cartList", sessionCartList);
			}
		}
		
	}
	
	// 장바구니 구매 페이지 리스트 요청
	@Override
	public void buyInCartInfo (HttpServletRequest req, HttpServletResponse res) {
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		String meId = mVo.getId();
		String[] strArrCaId = req.getParameterValues("caIdArray")==null 
				? new String[1] : req.getParameterValues("caIdArray");
		System.out.println("strArrCaId" + Arrays.toString(strArrCaId));
		// 무조건 공백을 반영하는 0번 index의 값을 0으로 바꾸고
		strArrCaId[0] = "0";
		// 0번 인덱스의 값을 제외한 채 String배열을 int배열로 바꾸는 스트림
		int[] arrCaId = Arrays.stream(strArrCaId).skip(1).mapToInt(Integer::parseInt).toArray();
		System.out.println("arrCaId" + Arrays.toString(arrCaId));
		
		AddressVO aVo = dao.getPrimaryAddressInfo(meId);
		List<CartVO> coList = new ArrayList<CartVO>();
		
		for (int caId: arrCaId) {
			// 각각의 ca_id에 해당하는 장바구니 정보를 가져옴
			CartVO cVo = dao.getCartInfo(caId);
			ItemVO iVo = dao.getItemDetail(cVo.getItId());
			cVo.setItName(iVo.getItemName());
			cVo.setSmallImg(iVo.getSmallImg());
			cVo.setPrice(iVo.getPrice());
			coList.add(cVo);
		}
		
		req.setAttribute("coList", coList);
		req.setAttribute("aVo", aVo);
	}
	
	// 장바구니 구매 처리
	@Override
	public void buyInCart(HttpServletRequest req, HttpServletResponse res) {
		String[] strArrCaId = req.getParameterValues("caIdArray")==null 
				? new String[1] : req.getParameterValues("caIdArray") ;
		System.out.println("strArrCaId" + Arrays.toString(strArrCaId));
		int[] arrCaId = Arrays.stream(strArrCaId).mapToInt(Integer::parseInt).toArray(); 
		System.out.println("arrCaId" + Arrays.toString(arrCaId));
		int adId = Integer.parseInt(req.getParameter("adId"));
		int insertCnt = 0;
		int deleteCnt = 0;
		int reduceCnt = 0;
		
		for (int caId: arrCaId) {
			// 각각의 ca_id에 해당하는 장바구니 정보를 가져옴
			CartVO cVo = dao.getCartInfo(caId);
			ItemVO iVo = dao.getItemDetail(cVo.getItId());
			if (iVo.getStock()>cVo.getAmount()) {
				// 주문VO에 대한 새 인스턴스 생성, 장바구니 정보 이식
				OrderVO oVo = new OrderVO();
				oVo.setItId(cVo.getItId());
				oVo.setMeId(cVo.getMeId());
				oVo.setAdId(adId);
				oVo.setRegDate(new Timestamp(System.currentTimeMillis()));
				oVo.setQuantity(cVo.getAmount());
				oVo.setCondition(Code.PURCHASE_REQUEST);
				insertCnt += dao.insertOrder(oVo);
				deleteCnt += dao.deleteCart(caId);
			} else {
				System.out.println("상품번호  " + cVo.getItId() + "재고부족");
			}
		}
		req.setAttribute("insertCnt", insertCnt);
	}

	// 바로구매 페이지 요청
	@Override
	public void buyNowInfo(HttpServletRequest req, HttpServletResponse res) {
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		String meId = mVo.getId();
		int itId = Integer.parseInt(req.getParameter("itemId"));
		int quantity = Integer.parseInt(req.getParameter("amount"));
		
		AddressVO aVo = dao.getPrimaryAddressInfo(meId);
		
		ItemVO iVo = dao.getItemDetail(itId);
		
		req.setAttribute("iVo", iVo);
		req.setAttribute("quantity", quantity);
		req.setAttribute("aVo", aVo);
	}
	
	// 바로구매 처리
	@Override
	public void buyNow(HttpServletRequest req, HttpServletResponse res) {
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		String meId = mVo.getId();
		int adId = Integer.parseInt(req.getParameter("adId"));
		int amount = Integer.parseInt(req.getParameter("amount"));
		int itId = Integer.parseInt(req.getParameter("itId"));
		ItemVO iVo = dao.getItemDetail(itId);
		int insertCnt = 0;
		int reduceCnt = 0;
		
		if (iVo.getStock()>amount) {
			// 주문VO에 대한 새 인스턴스 생성, 장바구니 정보 이식
			OrderVO oVo = new OrderVO();
			oVo.setItId(iVo.getItemId());
			oVo.setMeId(meId);
			oVo.setAdId(adId);
			oVo.setRegDate(new Timestamp(System.currentTimeMillis()));
			oVo.setQuantity(amount);
			oVo.setCondition(Code.PURCHASE_REQUEST);
			insertCnt = dao.insertOrder(oVo);
		} else {
			System.out.println("상품번호  " + itId + "재고부족");
		}
		System.out.println("수량 변경 여부:" + reduceCnt);
		req.setAttribute("insertCnt", insertCnt);
	}
	
	// 배송지 가져오기
	@Override
	public void addressList(HttpServletRequest req, HttpServletResponse res) {
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		String meId = mVo.getId();
		
	 	List<AddressVO> addressList = dao.addressList(meId);
	 	req.setAttribute("addressList", addressList);
	}
	
	// 배송지 추가
	@Override
	public void addAddress(HttpServletRequest req, HttpServletResponse res) {
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		String meId = mVo.getId();
		String recipient = req.getParameter("recipient");
		String tel = req.getParameter("tel");
		int zipcode = Integer.parseInt(req.getParameter("zipcode"));
		String main = req.getParameter("main");
		String detail = req.getParameter("detail");
		int condition = Integer.parseInt(req.getParameter("condition"));
		
		AddressVO aVo = new AddressVO();
		aVo.setAdId(dao.addressSeq());
		aVo.setMeId(meId);
		aVo.setRecipient(recipient);
		aVo.setTel(tel);
		aVo.setZipcode(zipcode);
		aVo.setMain(main);
		aVo.setDetail(detail);
		aVo.setCondition(condition);
		
		int updateCnt = 0;
		
		int insertCnt = dao.insertAddress(aVo);
		 
		if (dao.addressList(meId)!=null) {
			// 입력한 상태가 메인 주소일 경우 나머지 주소들 일반 주소로 변경
			if (condition==Code.MAIN_ADDRESS) {
	
				List<AddressVO> addressList = dao.addressList(meId);
				Iterator<AddressVO> itr = addressList.iterator();
				while (itr.hasNext()) {
					AddressVO iaVo = itr.next();
					if (iaVo.getAdId()!=aVo.getAdId())
					iaVo.setCondition(Code.NORMAL_ADDRESS);
					updateCnt += dao.updateAddress(iaVo);
				}
			}
		}
		System.out.println("갱신된 항목: " + updateCnt);
		req.setAttribute("insertCnt", insertCnt);
	}
	
	// 배송지 상세정보
	@Override
	public void addressInfo(HttpServletRequest req, HttpServletResponse res) {
		int adId = Integer.parseInt(req.getParameter("adId"));
		
		AddressVO aVo = dao.getAddressInfo(adId);
		
		req.setAttribute("dto", aVo);
	}
	
	// 배송지 수정
	@Override
	public void updateAddress(HttpServletRequest req, HttpServletResponse res) {
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		String meId = mVo.getId();
		int adId = Integer.parseInt(req.getParameter("adId"));
		String recipient = req.getParameter("recipient");
		String tel = req.getParameter("tel");
		int zipcode = Integer.parseInt(req.getParameter("zipcode"));
		String main = req.getParameter("main");
		String detail = req.getParameter("detail");
		int condition = req.getParameter("condition")==null ? 0 : Integer.parseInt(req.getParameter("condition"));
		
		AddressVO aVo = new AddressVO();
		aVo.setAdId(adId);
		aVo.setMeId(meId);
		aVo.setRecipient(recipient);
		aVo.setTel(tel);
		aVo.setZipcode(zipcode);
		aVo.setMain(main);
		aVo.setDetail(detail);
		aVo.setCondition(condition);
		
		int conCnt = 0;
		
		int updateCnt = dao.updateAddress(aVo);
		
		if (dao.addressList(meId)!=null) {
			if (condition==Code.MAIN_ADDRESS) {
				List<AddressVO> addressList = dao.addressList(meId);
				Iterator<AddressVO> itr = addressList.iterator();
				while (itr.hasNext()) {
					AddressVO iaVo = itr.next();
					if (iaVo.getAdId()!=adId)
					iaVo.setCondition(Code.NORMAL_ADDRESS);
					conCnt += dao.updateAddress(iaVo);
				}
			}
		}	
		System.out.println("상태 변경된 주소 수: " + conCnt);
		req.setAttribute("updateCnt", updateCnt);
	}
	
	// 주소록 삭제
	@Override
	public void deleteAddress(HttpServletRequest req, HttpServletResponse res) {
		int adId = Integer.parseInt(req.getParameter("adId"));
		int deleteCnt = dao.deleteAddress(adId);
		
		req.setAttribute("deleteCnt", deleteCnt);
	}
	
	// 주문 목록
	@Override
	public void orderList(HttpServletRequest req, HttpServletResponse res) {
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		String meId = mVo.getId();
		
		OrderVO oVo = new OrderVO();
		Map<Integer,String> odConMap = oVo.getOdConMap();
		List<OrderVO> orderList = dao.orderList(meId);
		req.setAttribute("orderList", orderList);
		req.setAttribute("odConMap", odConMap);
	}
	
	// 주문상태 변경(교환요청, 환불요청, 구매확정)
	@Override
	public void updateOrder(HttpServletRequest req, HttpServletResponse res) {
		int odId = Integer.parseInt(req.getParameter("odId"));
		int condition = Integer.parseInt(req.getParameter("condition"));
		
		int increaseCnt = dao.updateOrder(odId, condition);
		
		System.out.println("수량 변경: " + increaseCnt);
	}

}
