package leafcom.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.board.service.BoardService;
import leafcom.board.service.BoardServiceImpl;

@WebServlet("*.bo")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
        super();
    }
    
    // 1단계. URL요청 수주
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		action(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doGet(req, res);
	}
	
	public void action(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String viewPage = "";
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
		BoardService service = new BoardServiceImpl();
		
		// 2단계. 요청분석
		// 게시글 목록 조회
		if (url.equals("/boardList.bo")||url.equals("/*.bo")) {
			System.out.println("[Bo][Controller][url=>/boardList.bo]");
			service.boardList(req, res);
			
			viewPage = "/board/boardList.jsp";
			
		// 게시글 상세 보기	
		} else if (url.equals("/boardDetail.bo")) {
			System.out.println("[Bo][Controller][url=>/boardDetail.bo]");
			service.boardDetail(req, res);
			
			viewPage = "/board/boardDetail.jsp";
		
		// 게시글 수정 요청
		} else if (url.equals("/boardModify.bo")) {	
			System.out.println("[Bo][Controller][url=>/boardModify.bo]");
			
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			
			viewPage = "/board/boardModify.jsp";
			
		// 게시글 수정을 위한 인증 -> 수정 상세페이지 이동
		} else if (url.equals("/boardModifyDetail.bo")) {		
			System.out.println("[Bo][Controller][url=>/boardModifyDetail.bo]");
			service.boardModifyDetailAction(req, res);
			
			viewPage = "/board/boardModifyDetail.jsp";
			
		// 수정 상세 -> 게시글 수정 처리	
		} else if (url.equals("/boardModifyAction.bo")) {		
			System.out.println("[Bo][Controller][url=>/boardModifyAction.bo]");			
			service.boardModifyAction(req, res);
			
			viewPage = "/board/boardModifyAction.jsp";
		
		// 게시글 작성 - 화면
		} else if (url.equals("/boardWrite.bo")) {		
			System.out.println("[Bo][Controller][url=>/boardWrite.bo]");			
			
			// 3단계. 화면에서 받은 hidden값을 받아온다
			// 새 글쓰기
			int num = 0;
			int pageNum = 0;
			int ref = 0;
			int refStep = 0;
			int refLevel = 0;
			
			// boardWrite.bo?num=${dto.num}&pageNum=${pageNum}&ref=${dto.ref}&refStep=${dto.refStep}&refLevel=${dto.refLevel}
			// 상세페이지의 답글쓰기 버튼을 클릭한 경우 get방식의 url에서 값을 가져옴
			if(req.getParameter("num")!=null) {
				num = Integer.parseInt(req.getParameter("num"));
				ref = Integer.parseInt(req.getParameter("ref"));
				refStep = Integer.parseInt(req.getParameter("refStep"));
				refLevel = Integer.parseInt(req.getParameter("refLevel"));
			}
			// 새글 답글 포함
			pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			// 4단계. jsp로 전달하기 위해 request나 session에 처리결과 저장
			req.setAttribute("num", num);
			req.setAttribute("ref", ref);
			req.setAttribute("refStep", refStep);
			req.setAttribute("refLevel", refLevel);
			req.setAttribute("pageNum", pageNum);
			
			viewPage = "/board/boardWrite.jsp";	
			
		// 게시글 작성 - 처리
		} else if (url.equals("/boardWriteAction.bo")) {		
			System.out.println("[Bo][Controller][url=>/boardWriteAction.bo]");			
			service.boardWriteAction(req, res);
			
			viewPage = "/board/boardWriteAction.jsp";	
		
		// 게시글 삭제 - 페이지 호출
		} else if (url.equals("/boardDelete.bo")) {		
			System.out.println("[Bo][Controller][url=>/boardDelete.bo]");			
			
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			
			viewPage = "/board/boardDelete.jsp";
		
		// 게시글 삭제 - 처리
		} else if (url.equals("/boardDeleteAction.bo")) {		
			System.out.println("[Bo][Controller][url=>/boardDeleteAction.bo]");			
			
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			service.boardDeleteAction(req, res);
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			
			viewPage = "/board/boardDeleteAction.jsp";	
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
	}

}
