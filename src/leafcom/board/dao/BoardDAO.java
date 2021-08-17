package leafcom.board.dao;

import java.util.ArrayList;

import leafcom.board.vo.BoardVO;

public interface BoardDAO {
	
	// 게시글 개수 구하기
	public int getBoardCount();
	
	// 게시글 목록 조회
	public ArrayList<BoardVO> getBoardList(int start, int end);
	
	// 조회수 증가
	public void addReadCount(int nul);
	
	// 게시글 상세, 수정 상세
	public BoardVO getBoardDetail(int num);
	
	// 게시글 인증 - 수정
	public int numPasswordCheck(int num, String pw);
	
	// 게시글 수정 처리
	public int updateBoard(BoardVO vo);
	
	// 게시글 작성 처리
	public int insertBoard(BoardVO vo);
	
	// 게시글 인증 - 삭제
	public int deleteBoard(int num);
}
