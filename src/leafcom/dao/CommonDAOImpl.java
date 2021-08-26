package leafcom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import leafcom.util.Code;
import leafcom.vo.MemberVO;

public class CommonDAOImpl implements CommonDAO {
	
	private static CommonDAOImpl instance = new CommonDAOImpl();
	
	public static CommonDAOImpl getInstance() {
		if(instance == null) {
			instance = new CommonDAOImpl();
		}
		return instance;
	}
	
	DataSource dataSource;
	
	private CommonDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/leafcom");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	// 로그인 - 아이디,비밀번호 체크
	@Override
	public int idPwChk(String strId, String strPw) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM members WHERE me_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (strPw.equals(rs.getString("me_pw"))) {
					if(rs.getBoolean("me_role")) {
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
			System.out.println("[co][DAO] idPwChk() selectCnt => " + selectCnt);
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
	
	// 회원가입 - 아이디 중복 확인
	@Override
	public int idDupChk(String strId) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM members WHERE me_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				selectCnt = 1;
			} 
			
			System.out.println("[co][DAO] idDupChk() selectCnt => " + selectCnt);
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
	public MemberVO getMemberInfo(String strId) {
		MemberVO vo = new MemberVO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {	
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM members WHERE me_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo.setId(rs.getString("me_id"));
				vo.setEmail(rs.getString("me_email"));
				vo.setPw("");
				vo.setName(rs.getString("me_name"));
				vo.setPhone(rs.getString("me_phone"));
				vo.setRegDate(rs.getTimestamp("me_regdate"));
				vo.setRole(rs.getInt("me_role"));
				vo.setCondition(rs.getInt("me_condition"));
				vo.setKey(rs.getString("me_key"));
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
			String sql = "INSERT INTO members VALUES(?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getPhone());
			pstmt.setTimestamp(6, vo.getRegDate());
			pstmt.setInt(7, vo.getRole());
			pstmt.setInt(8, vo.getCondition());
			pstmt.setString(9, vo.getKey());
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("[co][DAO] insertMember() insertCnt => " + insertCnt);
			
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
	public int withrawMember(String strId) {
		int deleteCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE members WHERE me_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			deleteCnt = pstmt.executeUpdate();
			System.out.println("[co][DAO] withrawMember() deleteCnt => " + deleteCnt);
			
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
			String sql = "UPDATE members SET me_pw = ?, me_name = ?, me_email = ?, me_phone = ? WHERE me_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getId());
			
			updateCnt = pstmt.executeUpdate();
			System.out.println("[co][DAO] withrawMember() updateCnt => " + updateCnt);
			
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
	
	// 키값 비교
	@Override
	public int idKeyChk(String id, String key) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM members WHERE me_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(key.equals(rs.getString("me_key"))) {
					selectCnt = 1;
				} else {
					selectCnt = 0;
				}
			} else {
				selectCnt = -1;
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
		
		return selectCnt;
	}

	// 권한 활성화
	@Override
	public int updateCondition(String id, int condition) {
		int updateCnt = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "UPDATE members SET me_condition = ? WHERE me_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, condition);
			pstmt.setString(2, id);
			
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


}
