package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {
	
	private static MemberDAOImpl instance = new MemberDAOImpl();
	
	public static MemberDAOImpl getInstance() {
		if(instance == null) {
			instance = new MemberDAOImpl();
		}
		return instance;
	}
	
	DataSource dataSource;
	
	private MemberDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/leafcom");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	// 로그인 - 아이디,비밀번호 체크
	@Override
	public int idPwChk(String strEmail, String strPw) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM members WHERE mem_email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strEmail);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (strPw.equals(rs.getString("mem_pwd"))) {
					if(rs.getBoolean("mem_is_admin")) {
						// 관리자 로그인 성공
						selectCnt = 2;
					} else {
						// 회원 로그인 성공
						selectCnt = 1;
					}
				} else {
					// 회원 비밀번호 틀림
					selectCnt = -1;
				}
			} else {
				// 아이디 없음
				selectCnt = 0;
			}
			System.out.println("[Member][DAO] idPwChk() selectCnt => " + selectCnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return selectCnt;
	}
	
	// 회원가입 - 이메일 중복 확인
	@Override
	public int emailDupChk(String strEmail) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM members WHERE mem_email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strEmail);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				selectCnt = 1;
			} 
			
			System.out.println("[Member][DAO] emailDupChk() selectCnt => " + selectCnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				
			}
		}
		
		return selectCnt;
	}
	
	// 회원 정보 조회
	@Override
	public MemberVO getMemberInfo(String strEmail) {
		MemberVO vo = new MemberVO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {	
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM members WHERE mem_email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strEmail);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo.setEmail(rs.getString("mem_email"));
				vo.setName(rs.getString("mem_name"));
				vo.setPhone(rs.getString("mem_phone"));
				vo.setRegDate(rs.getTimestamp("mem_regdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				
			}
		}
			
		return vo;
	}
	
	// 회원가입 - 입력 처리
	@Override
	public int insertMember(MemberVO vo) {
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO members VALUES(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone());
			pstmt.setTimestamp(5, vo.getRegDate());
			pstmt.setInt(6, vo.getIsAdmin());
			pstmt.setInt(7, vo.getCondition());
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("[Member][DAO] insertMember() insertCnt => " + insertCnt);
			
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
	
	// 회원 탈퇴 처리
	@Override
	public int withrawMember(String strEmail) {
		int deleteCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE members WHERE mem_email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strEmail);
			
			deleteCnt = pstmt.executeUpdate();
			System.out.println("[Member][DAO] withrawMember() deleteCnt => " + deleteCnt);
			
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
	
	// 회원정보 수정
	@Override
	public int updateMember(MemberVO vo) {
		int updateCnt = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE members SET mem_pwd = ?, mem_name = ?, mem_phone = ? WHERE mem_email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPhone());
			pstmt.setString(4, vo.getEmail());
			
			updateCnt = pstmt.executeUpdate();
			System.out.println("[Member][DAO] withrawMember() updateCnt => " + updateCnt);
			
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

}
