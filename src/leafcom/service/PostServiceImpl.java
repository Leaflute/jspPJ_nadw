package leafcom.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.PostDAO;
import leafcom.dao.PostDAOImpl;
import leafcom.vo.MemberVO;
import leafcom.vo.PostVO;

public class PostServiceImpl implements PostService {
	
	PostDAO dao = PostDAOImpl.getInstance();
	
	// 게시글 목록
	@Override
	public void postList(HttpServletRequest req, HttpServletResponse res) {
		
		// 페이징
		int boardId = Integer.parseInt(req.getParameter("boardId"));	//게시판 id
		boolean fullList = true;		//리스트 전체를 가져올지 여부 0이면 전체,1이면 자기자신 글과 답글만
		if(req.getParameter("fullList")!=null) {
			fullList = Boolean.parseBoolean(req.getParameter("fullList"));
		}
		MemberVO memberVO = (MemberVO) req.getSession().getAttribute("member");
		String writer = memberVO.getName();
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
		  
		cnt = dao.getPostCount(boardId, fullList, writer);
		System.out.println("[bo][Service][cnt = " + cnt + " ]");
		
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
		System.out.println("===================");
		
		ArrayList<PostVO> dtos = null;
		
		// 5-2단계. 게시글 목록 조회
		if(cnt > 0) {
			dtos = dao.getPostList(start, end, boardId, fullList, writer);
		}
		
		// 6단계. request나 session에 처리결과를 저장 -> jsp로 전달
		req.setAttribute("dtos", dtos);		//게시글 목록
		req.setAttribute("cnt", cnt);		//글 개수
		req.setAttribute("number", number);	//글 번호
		req.setAttribute("pageNum", pageNum);//페이지 번호
		req.setAttribute("boardId", boardId);
		req.setAttribute("fullList", fullList);
		
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
	public void postDetail(HttpServletRequest req, HttpServletResponse res) {
		
		int boardId = Integer.parseInt(req.getParameter("boardId"));	//게시판 id
		boolean fullList = Boolean.parseBoolean(req.getParameter("fullList"));	//리스트 전체를 가져올지 여부
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int number = Integer.parseInt(req.getParameter("number"));
		MemberVO memberVO = (MemberVO) req.getSession().getAttribute("member");
		String writer = memberVO.getName();
		
		dao.addPostHit(num);
		int selectCnt = dao.chkWriter(num, writer);
		
		// 5-2단계. 게시글 상세페이지 조회
		PostVO vo = dao.getPostDetail(num);
		
		// 6단계. jsp로 전달하기 위해 request나 session에 처리결과를 저장 
		req.setAttribute("dto", vo);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("number", number);
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("boardId", boardId);
		req.setAttribute("fullList", fullList);
	}

	// 게시글 작성 - 처리
	@Override
	public void postWriteAction(HttpServletRequest req, HttpServletResponse res) {
		PostVO vo = new PostVO();
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int boardId = Integer.parseInt(req.getParameter("boardId"));
		boolean fullList = Boolean.parseBoolean(req.getParameter("fullList"));
		
		vo.setPostNum(Integer.parseInt(req.getParameter("num")));
		vo.setBoardId(boardId);
		vo.setRef(Integer.parseInt(req.getParameter("ref")));
		vo.setRefStep(Integer.parseInt(req.getParameter("refStep")));
		vo.setRefLevel(Integer.parseInt(req.getParameter("refLevel")));
		
		MemberVO mVo = (MemberVO)req.getSession().getAttribute("member");
		vo.setMeId(mVo.getId());
		vo.setWriter(mVo.getName());
		vo.setTitle(req.getParameter("title"));
		vo.setContent(req.getParameter("content"));
		
		vo.setRegDate(new Timestamp(System.currentTimeMillis()));
		// 화면 실행시 url의 localhost 대신 본인 IP를 넣으면 그 ip가 db에 insert된다. 
		vo.setIp(req.getRemoteAddr());
		vo.setCondition(0);

		int insertCnt = dao.insertPost(vo);
		
		req.setAttribute("insertCnt", insertCnt);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("boardId", boardId);
		req.setAttribute("fullList", fullList);
	}
	
	// 게시글 수정
	@Override
	public void postUpdateAction(HttpServletRequest req, HttpServletResponse res) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int boardId = Integer.parseInt(req.getParameter("boardId"));
		boolean fullList = Boolean.parseBoolean(req.getParameter("fullList"));
		
		PostVO vo = new PostVO();
		vo.setPostNum(num);
		vo.setTitle(req.getParameter("title"));
		vo.setContent(req.getParameter("content"));
		
		int updateCnt = dao.updatePost(vo);
		System.out.println("updateCnt" + updateCnt);
		
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("updateCnt", updateCnt);
		req.setAttribute("boardId", boardId);
		req.setAttribute("fullList", fullList);

	}
	
	// 게시글 삭제
	@Override
	public void postDeleteAction(HttpServletRequest req, HttpServletResponse res) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int boardId = Integer.parseInt(req.getParameter("boardId"));	//게시판 id
		boolean fullList = Boolean.parseBoolean(req.getParameter("fullList"));	//리스트 전체를 가져올지 여부
		
		int deleteCnt = 0; 
		
		deleteCnt = dao.deletePost(num);
		
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("deleteCnt", deleteCnt);
		req.setAttribute("boardId", boardId);
		req.setAttribute("fullList", fullList);
	}



}
