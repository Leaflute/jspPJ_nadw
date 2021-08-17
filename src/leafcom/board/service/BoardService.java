package leafcom.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoardService {
	
	// 게시글 목록 조회
	public void boardList(HttpServletRequest req, HttpServletResponse res);
	
	// 게시글 상세 조회
	public void boardDetail(HttpServletRequest req, HttpServletResponse res);
	
	// 게시글 작성 처리(답글포함)
	public void boardWriteAction(HttpServletRequest req, HttpServletResponse res);
	
	// 게시글 인증 - 수정, 삭제
	public void boardModifyDetailAction(HttpServletRequest req, HttpServletResponse res);
	
	// 게시글 수정 - 처리
	public void boardModifyAction(HttpServletRequest req, HttpServletResponse res);
	
	// 게시글 삭제 - 처리
	public void boardDeleteAction(HttpServletRequest req, HttpServletResponse res);
}
