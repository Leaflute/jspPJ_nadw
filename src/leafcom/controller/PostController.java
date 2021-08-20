package leafcom.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.service.PostService;
import leafcom.service.PostServiceImpl;

@WebServlet("*.bo")
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PostController() {
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
		PostService service = new PostServiceImpl();
		
		// 2단계. 요청분석
		// 고객 문의 게시판(Post_id=1)
		// 고객문의 목록 조회
		if (url.equals("/csList.bo")||url.equals("/*.bo")) {
			System.out.println("[Bo][Controller][url=>/csList.bo]");
			service.postList(req, res);
			
			viewPage = "/board/cs/csList.jsp";
		
		// 게시글 상세 보기	
		} else if (url.equals("/csDetail.bo")) {
			System.out.println("[Bo][Controller][url=>/csDetail.bo]");
			service.postDetail(req, res);
			
			viewPage = "/board/cs/csDetail.jsp";
		
		// 게시글 수정 요청
		} else if (url.equals("/csUpdate.bo")) {	
			System.out.println("[Bo][Controller][url=>/csUpdate.bo]");
			
			int boardId = Integer.parseInt(req.getParameter("boardId"));
			int fullList = Integer.parseInt(req.getParameter("fullList"));
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			service.postDetail(req, res);
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("fullList", fullList);
			req.setAttribute("boardId", boardId);
			
			viewPage = "/board/cs/csUpdate.jsp";

		// 게시글 수정 처리
		} else if (url.equals("/csUpdateAction.bo")) {		
			System.out.println("[Bo][Controller][url=>/csUpdateAction.bo]");			
			service.postUpdateAction(req, res);
			
			viewPage = "/board/cs/csUpdateAction.jsp";

		// 게시글 작성 - 화면
		} else if (url.equals("/csWrite.bo")) {		
			System.out.println("[Bo][Controller][url=>/csWrite.bo]");			
			
			// 3단계. 화면에서 받은 hidden값을 받아온다
			// 새 글쓰기
			int boardId = Integer.parseInt(req.getParameter("boardId"));
			int fullList = Integer.parseInt(req.getParameter("fullList"));
			int num = 0;
			int pageNum = 0;
			int ref = 0;
			int refStep = 0;
			int refLevel = 0;
			
			// PostWrite.bo?num=${dto.num}&pageNum=${pageNum}&ref=${dto.ref}&refStep=${dto.refStep}&refLevel=${dto.refLevel}
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
			req.setAttribute("fullList", fullList);
			req.setAttribute("boardId", boardId);
			
			viewPage = "/board/cs/csWrite.jsp";	
			
		// 게시글 작성 - 처리
		} else if (url.equals("/csWriteAction.bo")) {		
			System.out.println("[Bo][Controller][url=>/csWriteAction.bo]");			
			service.postWriteAction(req, res);
			
			viewPage = "/board/cs/csWriteAction.jsp";	
		
		// 게시글 삭제 - 페이지 호출
		} else if (url.equals("/csDelete.bo")) {		
			System.out.println("[Bo][Controller][url=>/PostDelete.bo]");			
			
			int boardId = Integer.parseInt(req.getParameter("boardId"));
			int fullList = Integer.parseInt(req.getParameter("fullList"));
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("fullList", fullList);
			req.setAttribute("boardId", boardId);
			
			viewPage = "/board/cs/csDelete.jsp";
		
		// 게시글 삭제 - 처리
		} else if (url.equals("/csDeleteAction.bo")) {		
			System.out.println("[Bo][Controller][url=>/csDeleteAction.bo]");			
			
			int boardId = Integer.parseInt(req.getParameter("boardId"));
			int fullList = Integer.parseInt(req.getParameter("fullList"));
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			service.postDeleteAction(req, res);
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("fullList", fullList);
			req.setAttribute("boardId", boardId);
			
			viewPage = "/board/cs/csDeleteAction.jsp";	
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
	}

}
