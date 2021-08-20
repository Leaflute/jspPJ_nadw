package leafcom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import leafcom.vo.PostVO;

public class PostDAOImpl implements PostDAO {
	
	// 싱글톤 방식으로 객체 생성
	private static PostDAOImpl instance = new PostDAOImpl();
	
	public static PostDAOImpl getInstance() {
		if(instance == null) {
			instance = new PostDAOImpl();
		}
		return instance;
	}
		
	// 커넥션 풀 객체
	DataSource dataSource;
	
	// 생성자에 커넥션 풀 생성
	private PostDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/leafcom");		
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 게시글 수 구하기
	@Override
	public int getPostCount(int boardId, int fullList, String writer) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT COUNT(*) cnt FROM Post WHERE board_id = ?";
			
			if (fullList==0) {
				sql += "AND post_writer = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, boardId);
				pstmt.setString(2, writer);
			} else {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, boardId);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				selectCnt = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return selectCnt;
	}
	
	// 고객문의 게시글 목록 구하기
	@Override
	public ArrayList<PostVO> getPostList(int start, int end, int boardId, int fullList, String writer) {
		
		ArrayList<PostVO> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * "
						+ "FROM (SELECT post_num, board_id, post_writer, post_title, post_content, post_reg_date, "
									+ "post_hit, post_ref, post_ref_level, post_ref_step, writer_ip, post_condition, rowNum rNum "
								+ "FROM (SELECT * FROM post WHERE board_id = ? ";
									
			if(fullList==0) {
				sql += "AND post_writer IN (?, ?) ORDER BY post_ref DESC, post_ref_step ASC)) WHERE rNum >= ? AND rNum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, boardId);
				pstmt.setString(2, writer);
				pstmt.setString(3, "admin");
				pstmt.setInt(4, start);
				pstmt.setInt(5, end);
				System.out.println("고객문의 - 고객");
			} else {
				sql += "ORDER BY post_ref DESC, post_ref_step ASC)) WHERE rNum >= ? AND rNum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, boardId);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				System.out.println("고객문의 - 관리자");
			}

			rs = pstmt.executeQuery();

			// 결과가 존재하면
			if (rs.next()) {
				
				// 2. 큰바구니(ArrayList) 생성
				list = new ArrayList<PostVO>();
				
				do {
					// 3. 작은바구니(PostVO) 생성
					PostVO vo = new PostVO();
					
					// 4. 한 건을 읽어서 rs결과를 setter로 작은 바구니에 담음
					vo.setPostNum(rs.getInt("post_num"));
					vo.setBoardId(rs.getInt("board_id"));
					vo.setWriter(rs.getString("post_writer"));
					vo.setTitle(rs.getString("post_title"));
					vo.setContent(rs.getString("post_content"));
					vo.setHit(rs.getInt("post_hit"));
					vo.setRegDate(rs.getTimestamp("post_reg_date"));
					vo.setRef(rs.getInt("post_ref"));
					vo.setRefStep(rs.getInt("post_ref_step"));
					vo.setRefLevel(rs.getInt("post_ref_level"));
					vo.setIp(rs.getString("writer_ip"));
					vo.setCondition(rs.getInt("post_condition"));
					
					// 5. 큰 바구니에 작은 바구니를 담음
					list.add(vo);
					
				} while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	// 게시글 조회수 증가
	@Override
	public void addPostHit(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "UPDATE post SET post_hit = post_hit+1 WHERE post_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 게시글 상세페이지, 수정 상세
	@Override
	public PostVO getPostDetail(int num) {
		PostVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM post WHERE post_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new PostVO();
				
				vo.setPostNum(rs.getInt("post_num"));
				vo.setBoardId(rs.getInt("board_id"));
				vo.setWriter(rs.getString("post_writer"));
				vo.setTitle(rs.getString("post_title"));
				vo.setContent(rs.getString("post_content"));
				vo.setHit(rs.getInt("post_hit"));
				vo.setRegDate(rs.getTimestamp("post_reg_date"));
				vo.setRef(rs.getInt("post_ref"));
				vo.setRefStep(rs.getInt("post_ref_step"));
				vo.setRefLevel(rs.getInt("post_ref_level"));
				vo.setIp(rs.getString("writer_ip"));
				vo.setCondition(rs.getInt("post_condition"));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
	// 작성자 인증
	@Override
	public int chkWriter(int num, String writer) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM post WHERE post_num = ? AND post_writer = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, writer);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				selectCnt = 1;
			}
			System.out.println("[bo][DAO][numPasswordCheck()][selectCnt: " + selectCnt + "]");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return selectCnt;
	}
	
	// 게시글 수정
	@Override
	public int updatePost(PostVO vo) {
		int updateCnt=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE post SET post_title=?, post_content=? WHERE post_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getPostNum());
			
			updateCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return updateCnt;
	}
	
	
	// 게시글 작성
	@Override
	public int insertPost(PostVO vo) {
		int insertCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {	
			String sql = "";
			conn = dataSource.getConnection();
			int num = vo.getPostNum();
			int ref = vo.getRef();
			int refStep = vo.getRefStep();
			int refLevel = vo.getRefLevel();
			int postCondition = vo.getCondition();
			
			// 답글인 경우
			if(num!=0) {
				// 삽입할 글보다 아래쪽 글들이 한 줄씩 밀려남
				sql = "UPDATE post SET post_ref_step = post_ref_step+1 WHERE post_ref=? AND post_ref_step > ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, refStep);
				
				pstmt.executeUpdate();
				
				// 작성중인 답변글은 한 줄 아래 위치하고 한 칸 들여씀
				refStep++;
				refLevel++;
				postCondition=1;
				
				// 답 글인 경우
				sql="INSERT INTO post (post_num, board_id, post_writer, post_title, post_content, post_reg_date, "
						+ "post_hit, post_ref, post_ref_step, post_ref_level, writer_ip ,post_condition)"
						+ "VALUES (post_num_seq.nextval, ?, ?, ?, ?, ?, 0, ?, ?, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getBoardId());
				pstmt.setString(2, vo.getWriter());
				pstmt.setString(3, vo.getTitle());
				pstmt.setString(4, vo.getContent());
				pstmt.setTimestamp(5, vo.getRegDate());
				pstmt.setInt(6, ref);
				pstmt.setInt(7, refStep);
				pstmt.setInt(8, refLevel);
				pstmt.setString(9, vo.getIp());
				pstmt.setInt(10, postCondition);
				
			} else {
				System.out.println("원글 작성");
				refStep = 0;
				refLevel = 0;
				
				// 새 글인 경우
				sql="INSERT INTO post (post_num, board_id, post_writer, post_title, post_content, post_reg_date, "
						+ "post_hit, post_ref, post_ref_step, post_ref_level, writer_ip, post_condition)"
						+ "VALUES (post_num_seq.nextval, ?, ?, ?, ?, ?, 0, post_num_seq.currval, ?, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getBoardId());
				pstmt.setString(2, vo.getWriter());
				pstmt.setString(3, vo.getTitle());
				pstmt.setString(4, vo.getContent());
				pstmt.setTimestamp(5, vo.getRegDate());
				pstmt.setInt(6, refStep);
				pstmt.setInt(7, refLevel);
				pstmt.setString(8, vo.getIp());
				pstmt.setInt(9, postCondition);
			}
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("[bo][DAO][insertPost][insertCnt: " + insertCnt +" ]");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return insertCnt;
	}
	
	// 게시글 삭제 
	public int deletePost(int num) {
		int deleteCnt = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			conn = dataSource.getConnection();
			sql = "SELECT * FROM post WHERE post_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int ref = rs.getInt("post_ref");
				int refStep = rs.getInt("post_ref_step");
				int refLevel = rs.getInt("post_ref_level");
				
				// 답글이 존재하는지 여부
				sql = "SELECT * FROM post WHERE post_ref=? AND post_ref_step=?+1 AND post_ref_level > ?";
				
				pstmt.close();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, refStep);
				pstmt.setInt(3, refLevel);
				
				rs.close();
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					// 답글이 존재하는 경우
					sql = "DELETE FROM post WHERE post_num = ? OR (post_ref=? AND post_ref_level>?)";
					// pstmt.close();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num);
					pstmt.setInt(2, ref);
					pstmt.setInt(3, refLevel);
					
					deleteCnt = pstmt.executeUpdate();
					System.out.println("답글이 존재하는 경우 전체 삭제 deleteCnt: " + deleteCnt);
					if (deleteCnt==2) {
						deleteCnt=1;
					}
				} else {
					// 답글이 존재하지 않는 경우
					sql = "DELETE FROM post WHERE post_num = ?";
					// pstmt.close();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num);
					
					deleteCnt = pstmt.executeUpdate();
					System.out.println("답글이 존재하지 않는 경우 삭제 deleteCnt: " + deleteCnt);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	return deleteCnt;
	}
}
