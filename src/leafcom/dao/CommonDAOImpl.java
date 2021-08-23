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
			String sql = "SELECT * FROM members WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (strPw.equals(rs.getString("mem_pw"))) {
					if(rs.getBoolean("mem_role")) {
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
	
	// 회원가입 - 이메일 중복 확인
	@Override
	public int idDupChk(String strId) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM members WHERE mem_id = ?";
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
			String sql = "SELECT * FROM members WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo.setId(rs.getString("mem_id"));
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
			String sql = "INSERT INTO members VALUES(?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getPhone());
			pstmt.setTimestamp(6, vo.getRegDate());
			pstmt.setInt(7, vo.getRole());
			pstmt.setInt(8, vo.getCondition());
			
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
	

	@Override
	public void sendActivationEmail(String email, String key) {
		final String username = Code.ACTIVATION_HELPER;
		final String password = Code.PW;
		final String host = "smtp.gmail.com";
		
		// SMTP(메일 서버) 설정
		Properties props = new Properties();
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "enable");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		
		// porpert값 설정
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("admin@leafcom.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			
			String content = "LEAFCOM 회원가입을 축하합니다. 보이는 링크를 눌러 회원가입을 완료하세요. "
							+ "<a href='http://localhost/jsp_pj_ndw/emailChk.co?key=" + key + "'>링크</a>";
			
			msg.setSubject("LEAFCOM 회원가입 인증메일입니다.");
			msg.setContent(content, "text/html; charset=utf-8"); 
			
			System.out.println("메일 전송 시작");
			Transport.send(msg);
			System.out.println("메일 전송 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 회원 탈퇴 처리
	@Override
	public int withrawMember(String strId) {
		int deleteCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE members WHERE mem_id = ?";
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
			String sql = "UPDATE members SET mem_pw = ?, mem_name = ?, mem_email = ?, mem_phone = ? WHERE mem_id = ?";
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


}
