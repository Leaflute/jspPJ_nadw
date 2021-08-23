package leafcom.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.AdminDAO;
import leafcom.dao.AdminDAOImpl;
import leafcom.vo.ItemVO;

public class AdminServiceImpl implements AdminService {
	
	AdminDAO dao = AdminDAOImpl.getInstance();

	// 상품 리스트
	@Override
	public void itemList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[ad][service][itemList()]");
		int categoryId = 0;
		
		if (req.getParameter("categoryId")!=null) {
			categoryId = Integer.parseInt(req.getParameter("categoryId"));
		}
		System.out.println("categoryId: " + categoryId);
			
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
		HashMap<Integer, String> categoryMap = dao.getCategoryName();
		
		if(cnt > 0) {
			itemDtos = dao.getItemList(start, end, categoryId);
		}
		
		req.setAttribute("itemDtos", itemDtos);
		req.setAttribute("categoryMap", categoryMap);
		req.setAttribute("cnt", cnt);		
		req.setAttribute("number", number);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("categoryId", categoryId);
		
		if (cnt > 0) {
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
			req.setAttribute("pageBlock", pageBlock);
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("currentPage", currentPage);
		}
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
	
	// 카테고리 맵 가져오기
	@Override
	public void categoryMap(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[ad][service][categoryMap()]");
		HashMap<Integer, String> categoryMap = dao.getCategoryName();
		req.setAttribute("categoryMap", categoryMap);
	}
	
	// 상품 추가 처리
	@Override
	public void addItem(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[ad][service][addItem()]");
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));
		
		HashMap<Integer, String> categoryMap = dao.getCategoryName();
		String categoryName = categoryMap.get(categoryId);
		
		String itemName = req.getParameter("itemName");
		String company = req.getParameter("company");
		// sql에 저장할 이미지 경로 = "/플젝명/updload/" + 이미지 핸들러에서 보낸 속성값("fileName");
		String smallImg = "/jsp_pj_ndw/asset/uploaded/" + (String) req.getAttribute("fileName");
		String largeImg = "/jsp_pj_ndw/asset/uploaded/" + (String) req.getAttribute("fileName");
		String detailImg = "/jsp_pj_ndw/asset/uploaded/" + (String) req.getAttribute("fileName");
		String info = req.getParameter("info");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		int cost = Integer.parseInt(req.getParameter("cost"));
		int price = Integer.parseInt(req.getParameter("price"));
		
		ItemVO vo = new ItemVO();
		vo.setCategoryId(categoryId);
		vo.setCategoryName(categoryName);
		vo.setItemName(itemName);
		vo.setCompany(company);
		vo.setSmallImg(smallImg);
		vo.setLargeImg(largeImg);
		vo.setDetailImg(detailImg);
		vo.setRegDate(new Timestamp(System.currentTimeMillis()));
		vo.setInfo(info);
		vo.setQuantity(quantity);
		vo.setCost(cost);
		vo.setPrice(price);
		vo.setGrade(0);
		
		int insertCnt = dao.insertItem(vo);
		
		req.setAttribute("insertCnt",insertCnt);
	}
	
	// 상품 수정 처리
	@Override
	public void updateItem(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[ad][service][updateItem()]");
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		int itemId = Integer.parseInt(req.getParameter("itemId"));
		
		HashMap<Integer, String> categoryMap = dao.getCategoryName();
		String categoryName = categoryMap.get(categoryId);
		
		String itemName = req.getParameter("itemName");
		String company = req.getParameter("company");
		
		String smallImg = "";
		String largeImg = "";
		String detailImg = "";
		if((String)req.getAttribute("fileName")==null) {
			smallImg = req.getParameter("originalSmallImg");
		} else {
			smallImg = "/jsp_pj_ndw/asset/uploaded/" + (String)req.getAttribute("fileName");
		}
		
		if((String)req.getAttribute("fileName")==null) {
			largeImg = req.getParameter("originalLargeImg");
		} else {
			largeImg = "/jsp_pj_ndw/asset/uploaded/" + (String)req.getAttribute("fileName");
		}
		
		if((String)req.getAttribute("fileName")==null) {
			largeImg = req.getParameter("originalLargeImg");
		} else {
			largeImg = "/jsp_pj_ndw/asset/uploaded/" + (String)req.getAttribute("fileName");
		}
		
		String info = req.getParameter("info");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		int cost = Integer.parseInt(req.getParameter("cost"));
		int price = Integer.parseInt(req.getParameter("price"));
		double grade = Double.parseDouble(req.getParameter("grade"));
		Timestamp regdate = Timestamp.valueOf(req.getParameter("regDate"));
		
		ItemVO vo = new ItemVO();
		vo.setItemId(itemId);
		vo.setCategoryId(categoryId);
		vo.setCategoryName(categoryName);
		vo.setItemName(itemName);
		vo.setCompany(company);
		vo.setSmallImg(smallImg);
		vo.setLargeImg(largeImg);
		vo.setDetailImg(detailImg);
		vo.setRegDate(regdate);
		vo.setInfo(info);
		vo.setQuantity(quantity);
		vo.setCost(cost);
		vo.setPrice(price);
		vo.setGrade(grade);
		
		int updateCnt = dao.updateItem(vo);
		
		req.setAttribute("updateCnt",updateCnt);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("categoryId", categoryId);
	}
	
	// 상품 삭제 처리
	@Override
	public void deleteItem(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[ad][service][deleteItem()]");
		int itemId = Integer.parseInt(req.getParameter("itemId"));
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		dao.deleteItem(itemId);
		
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("categoryId", categoryId);
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

	@Override
	public void accountReport(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}
	
}
