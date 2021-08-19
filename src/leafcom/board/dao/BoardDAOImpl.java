package leafcom.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import leafcom.board.vo.BoardVO;

public class BoardDAOImpl implements BoardDAO {
	
	// 싱글톤 방식으로 객체 생성
	private static BoardDAOImpl instance = new BoardDAOImpl();
	
	public static BoardDAOImpl getInstance() {
		if(instance == null) {
			instance = new BoardDAOImpl();
		}
		return instance;
	}
	
		
	// 커넥션 풀 객체
	DataSource dataSource;
	
	// 생성자에 커넥션 풀 생성
	private BoardDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/leafcom");		
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 고객문의 게시글 개수 구하기
	@Override
	public int getBoardCount() {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT COUNT(*) cnt FROM board WHERE board_id = ?";
			pstmt.setInt(1, 1);
			pstmt = conn.prepareStatement(sql);
			
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
	public ArrayList<BoardVO> getPostList(int start, int end) {
		
		// 1. 큰바구니(ArrayList) 선언
		ArrayList<BoardVO> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * "
						+ "FROM (SELECT post_num, post_writer, post_title, post_content, post_hits, "
									+ "post_ref, post_ref_step, post_ref_level, post_regdate, post_ip, rowNum rNum "
								+ "FROM (SELECT * FROM post WHERE board_id = ? ORDER BY ref DESC, ref_step ASC)) " 
						+ "WHERE rNum >= ? AND rNum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			
			// 결과가 존재하면
			if (rs.next()) {
				
				// 2. 큰바구니(ArrayList) 생성
				list = new ArrayList<BoardVO>();
				
				do {
					// 3. 작은바구니(BoardVO) 생성
					BoardVO vo = new BoardVO();
					
					// 4. 한 건을 읽어서 rs결과를 setter로 작은 바구니에 담음
					vo.setPostNum(rs.getInt("post_num"));
					vo.setWriter(rs.getString("post_writer"));
					vo.setTitle(rs.getString("post_title"));
					vo.setContent(rs.getString("post_content"));
					vo.setHit(rs.getInt("post_hit"));
					vo.setRegDate(rs.getTimestamp("post_regdate"));
					vo.setRef(rs.getInt("post_ref"));
					vo.setRefStep(rs.getInt("post_refref_step"));
					vo.setRefLevel(rs.getInt("post_refref_level"));
					vo.setIp(rs.getString("post_ip"));
					
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


	@Override
	public void addHit(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "UPDATE mvc_board_tbl SET readcount = readcount+1 WHERE num = ?";
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
	public BoardVO getBoardDetail(int num) {
		BoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM mvc_board_tbl WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new BoardVO();
				
				vo.setNum(rs.getInt("num"));
				vo.setWriter(rs.getString("writer"));
				vo.setPw(rs.getString("pw"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadCount(rs.getInt("readcount"));
				vo.setRef(rs.getInt("ref"));
				vo.setRefStep(rs.getInt("ref_step"));
				vo.setRefLevel(rs.getInt("ref_level"));
				vo.setRegDate(rs.getTimestamp("regdate"));
				vo.setIp(rs.getString("ip"));
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
	
	// 비밀번호 인증
	@Override
	public int numPasswordCheck(int num, String pw) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM mvc_board_tbl WHERE num = ? AND pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				selectCnt = 1;
			}
			System.out.println("[DAO][numPasswordCheck()][selectCnt: " + selectCnt + "]");
			
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

	@Override
	public int updateBoard(BoardVO vo) {
		int updateCnt=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_board_tbl SET writer=?, pw=?, subject=?, content=? WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getWriter());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getSubject());
			pstmt.setString(4, vo.getContent());
			pstmt.setInt(5, vo.getNum());
			
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
	public int insertBoard(BoardVO vo) {
		int insertCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {	
			String sql = "";
			conn = dataSource.getConnection();
			int num = vo.getNum();
			int ref = vo.getRef();
			int refStep = vo.getRefStep();
			int refLevel = vo.getRefLevel();
			
			// 답글인 경우
			if(num!=0) {
				// 삽입할 글보다 아래쪽 글들이 한 줄씩 밀려남
				sql = "UPDATE mvc_board_tbl SET ref_step = ref_step+1 WHERE ref=? AND ref_step > ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, refStep);
				
				pstmt.executeUpdate();
				
				// 작성중인 답변글은 한 줄 아래 위치하고 한 칸 들여씀
				refStep++;
				refLevel++;
				
				// 답 글인 경우
				sql="INSERT INTO mvc_board_tbl (num, writer, pw, subject, content, readcount, ref, ref_step, ref_level, regdate, ip)"
						+ "VALUES (board_seq.nextval, ?, ?, ?, ?, 0, ?, ?, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getWriter());
				pstmt.setString(2, vo.getPw());
				pstmt.setString(3, vo.getSubject());
				pstmt.setString(4, vo.getContent());
				pstmt.setInt(5, ref);
				pstmt.setInt(6, refStep);
				pstmt.setInt(7, refLevel);
				pstmt.setTimestamp(8, vo.getRegDate());
				pstmt.setString(9, vo.getIp());
				
			} else {
				System.out.println("원글 작성");
				refStep = 0;
				refLevel = 0;
				
				// 새 글인 경우
				sql="INSERT INTO mvc_board_tbl (num, writer, pw, subject, content, readcount, ref, ref_step, ref_level, regdate, ip)"
						+ "VALUES (board_seq.nextval, ?, ?, ?, ?, 0, board_seq.currval, ?, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getWriter());
				pstmt.setString(2, vo.getPw());
				pstmt.setString(3, vo.getSubject());
				pstmt.setString(4, vo.getContent());
				pstmt.setInt(5, refStep);
				pstmt.setInt(6, refLevel);
				pstmt.setTimestamp(7, vo.getRegDate());
				pstmt.setString(8, vo.getIp());
			}
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("[DAO][insertBoard][insertCnt: " + insertCnt +" ]");
			
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
	public int deleteBoard(int num) {
		int deleteCnt = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			conn = dataSource.getConnection();
			sql = "SELECT * FROM mvc_board_tbl WHERE num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int ref = rs.getInt("ref");
				int refStep = rs.getInt("ref_step");
				int refLevel = rs.getInt("ref_level");
				
				// 답글이 존재하는지 여부
				sql = "SELECT * FROM mvc_board_tbl WHERE ref=? AND ref_step=?+1 AND ref_level > ?";
				
				pstmt.close();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, refStep);
				pstmt.setInt(3, refLevel);
				
				rs.close();
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					// 답글이 존재하는 경우
					sql = "DELETE FROM mvc_board_tbl WHERE num = ? OR (ref=? AND ref_level>?)";
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
					sql = "DELETE FROM mvc_board_tbl WHERE num = ?";
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
