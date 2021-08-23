package leafcom.dao;

import leafcom.vo.MemberVO;

public interface CommonDAO {
	
	// 아이디, 비밀번호 확인
	public int idPwChk(String strId, String strPw);
	
	// 아이디 중복 확인
	public int idDupChk(String strId);

	// 회원정보 조회
	public MemberVO getMemberInfo(String strId);
	
	// 회원정보 DB에 삽입
	public int insertMember(MemberVO vo);
	
	// 인증 이메일
	public void sendActivationEmail(String email, String key);
	
	// 회원정보 DB로부터 삭제
	public int withrawMember(String strId);
	
	// 회원정보 DB에서 수정
	public int updateMember(MemberVO vo);
	
	// 상품 리스트 조회
	
	// 상품 상세정보 조회
}
