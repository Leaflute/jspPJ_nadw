package leafcom.board.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.board.dao.BoardDAO;
import leafcom.board.dao.BoardDAOImpl;
import leafcom.board.vo.BoardVO;

public class BoardServiceImpl implements BoardService {
	
	// 게시글 목록
	@Override
	public void boardList(HttpServletRequest req, HttpServletResponse res) {
		
		// 3단계. 화면에서 입력받은 값을 가져옴
		// 페이징
		int pageSize = 5; 		// 한 페이지당 출력할 글 개수
		int pageBlock = 3;		// 한 블럭당 페이지 개수
		
		int cnt = 0;			// 게시글 개수
		int start = 0;			// 현재 페이지 시작 게시글
		int end = 0;			// 현제 페이지 마지막 게시글
		int number = 0; 		// 출력용 글 번호
		String pageNum = ""; 	// 페이지 번호
		int currentPage = 0;	// 현재 페이지

		int pageCount = 0;		// 페이지 개수
		int startPage = 0; 		// 시작페이지
		int endPage = 0;		// 마지막 페이지
		
		// 4단계. 다형성 적용해 싱글톤 방식으로 dao객체 생성
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		// 5-1단계. 게시글 개수 조회
		cnt = dao.getBoardCount();
		System.out.println("[Board][Service][cnt = " + cnt + " ]");
		
		
		// 5-2단계. 게시글 목록 조회
		pageNum = req.getParameter("pageNum");
		
		if(pageNum == null) {
			pageNum = "1";		// 첫 페이지를 1페이지로 지정
		}
		
		// 글 30건 기준
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
		System.out.println("==============================");
		
		ArrayList<BoardVO> dtos = null;
		
		if(cnt > 0) {
			dtos = dao.getBoardList(start, end);
		}
		
		// 6단계. request나 session에 처리결과를 저장 -> jsp로 전달
		req.setAttribute("dtos", dtos);		//게시글 목록
		req.setAttribute("cnt", cnt);		//글 개수
		req.setAttribute("number", number);	//글 번호
		req.setAttribute("pageNum", pageNum);//페이지 번호
		
		if (cnt > 0) {
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
			req.setAttribute("pageBlock", pageBlock);
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("currentPage", currentPage);
		}
	}
	
	// 게시글 상세 페이지 조회
	@Override
	public void boardDetail(HttpServletRequest req, HttpServletResponse res) {
		
		// 3단계. 화면에서 입력받은 값을 받아온다.]
		// http://localhost/jsp_mvcBoard/boardDetail.bo?num=62&pageNum=1&number=30
		
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int number = Integer.parseInt(req.getParameter("number"));
		
		// 4단계. 다형성 적용, 싱글톤 방식으로 dao 개체 생성
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		// 5-1단계. 조회수 증가
		dao.addReadCount(num);
		
		// 5-2단계. 게시글 상세페이지 조회
		BoardVO vo = dao.getBoardDetail(num);
		
		// 6단계. jsp로 전달하기 위해 request나 session에 처리결과를 저장 
		req.setAttribute("dto", vo);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("number", number);
	}

	// 게시글 작성 - 처리
	@Override
	public void boardWriteAction(HttpServletRequest req, HttpServletResponse res) {
		BoardVO vo = new BoardVO();
		
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		vo.setNum(Integer.parseInt(req.getParameter("num")));
		vo.setRef(Integer.parseInt(req.getParameter("ref")));
		vo.setRefStep(Integer.parseInt(req.getParameter("refStep")));
		vo.setRefLevel(Integer.parseInt(req.getParameter("refLevel")));
		
		vo.setWriter(req.getParameter("writer"));
		vo.setPw(req.getParameter("pw"));
		vo.setSubject(req.getParameter("subject"));
		vo.setContent(req.getParameter("content"));
		
		vo.setRegDate(new Timestamp(System.currentTimeMillis()));
		// 화면 실행시 url의 localhost 대신 본인 IP를 넣으면 그 ip가 db에 insert된다. 
		vo.setIp(req.getRemoteAddr());

		BoardDAO dao = BoardDAOImpl.getInstance();
		int insertCnt = dao.insertBoard(vo);
		
		req.setAttribute("insertCnt", insertCnt);
		req.setAttribute("pageNum", pageNum);
	}
	
	// 게시글 수정 - 조회
	@Override
	public void boardModifyDetailAction(HttpServletRequest req, HttpServletResponse res) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String pw = req.getParameter("pw");
		
		BoardDAO dao = BoardDAOImpl.getInstance();
		BoardVO vo = null;
		
		int selectCnt = dao.numPasswordCheck(num, pw);
		if (selectCnt==1) {
			vo = new BoardVO();
			vo = dao.getBoardDetail(num);
		}
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("dto", vo);
		req.setAttribute("selectCnt", selectCnt);
	}
	
	// 게시글 수정 - 처리
	@Override
	public void boardModifyAction(HttpServletRequest req, HttpServletResponse res) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		BoardVO vo = new BoardVO();
		vo.setNum(num);
		vo.setWriter(req.getParameter("writer"));
		vo.setPw(req.getParameter("pw"));
		vo.setSubject(req.getParameter("subject"));
		vo.setContent(req.getParameter("content"));
		
		BoardDAO dao = BoardDAOImpl.getInstance();
		int updateCnt = dao.updateBoard(vo);
		System.out.println("updateCnt" + updateCnt);
		
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("updateCnt", updateCnt);
	}
	
	// 게시글 삭제 - 처리
	@Override
	public void boardDeleteAction(HttpServletRequest req, HttpServletResponse res) {
		int num = Integer.parseInt(req.getParameter("num"));
		
		String pw = req.getParameter("pw");
		int deleteCnt = 0; 
		
		BoardDAO dao = BoardDAOImpl.getInstance();
		int selectCnt = dao.numPasswordCheck(num, pw);
		if (selectCnt==1) {
			deleteCnt = dao.deleteBoard(num);
			System.out.println("num: " + num);
		}
		System.out.println("selectCnt: " + selectCnt);
		System.out.println("deleteCnt: " + deleteCnt);
		req.setAttribute("deleteCnt", deleteCnt);
		req.setAttribute("selectCnt", selectCnt);
	}

}
