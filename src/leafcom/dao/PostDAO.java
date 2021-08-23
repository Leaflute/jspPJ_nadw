package leafcom.dao;

import java.util.ArrayList;

import leafcom.vo.PostVO;

public interface PostDAO {
	
	// 게시글 개수 구하기
	public int getPostCount(int boardId, boolean fullList, String writer);
	
	// 게시글 목록 조회
	public ArrayList<PostVO> getPostList(int start, int end, int boardId, boolean fullList, String writer);

	// 조회수 증가
	public void addPostHit(int num);
	
	// 게시글 상세, 수정 상세
	public PostVO getPostDetail(int num);
	
	// 게시글 인증 - 수정
	public int chkWriter(int num, String writer);
	
	// 게시글 수정 처리
	public int updatePost(PostVO vo);
	
	// 게시글 작성 처리
	public int insertPost(PostVO vo);
	
	// 게시글 인증 - 삭제
	public int deletePost(int num);
}
