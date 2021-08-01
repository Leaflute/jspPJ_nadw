package member.dao;

import member.vo.MemberVO;

public interface MemberDAO {
	
	// 아이디, 비밀번호 확인
	public int idPwChk(String strEmail, String strPw);
	
	// 아이디 중복 확인
	public int emailDupChk(String strEmail);

	// 회원정보 조회
	public MemberVO getMemberInfo(String strEmail);
	
	// 회원정보 DB에 삽입
	public int insertMember(MemberVO vo);
	
	// 회원정보 DB로부터 삭제
	public int withrawMember(String strEmail);
	
	// 회원정보 DB에서 수정
	public int updateMember(MemberVO vo);

}
