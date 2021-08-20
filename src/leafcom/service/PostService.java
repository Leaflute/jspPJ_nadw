package leafcom.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PostService {
	
	// 게시판 목록 조회
	public void postList(HttpServletRequest req, HttpServletResponse res);

	// 게시글 상세 조회
	public void postDetail(HttpServletRequest req, HttpServletResponse res);

	// 게시글 작성 
	public void postWriteAction(HttpServletRequest req, HttpServletResponse res);
	
	// 게시글 수정 처리
	public void postUpdateAction(HttpServletRequest req, HttpServletResponse res);
	
	// 게시글 삭제
	public void postDeleteAction(HttpServletRequest req, HttpServletResponse res);
}
