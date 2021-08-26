package leafcom.service;

import java.sql.Timestamp;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.CommonDAO;
import leafcom.dao.CommonDAOImpl;
import leafcom.util.Code;
import leafcom.util.MailSendHandler;
import leafcom.vo.MemberVO;

public class CommonServiceImpl implements CommonService {
	
	CommonDAO dao = CommonDAOImpl.getInstance();
	
	// 로그인 처리
	@Override
	public void loginAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][loginAction()]");
		
		String strId = req.getParameter("id");
		String strPw = req.getParameter("pw");
		
		int selectCnt = dao.idPwChk(strId, strPw);
		MemberVO vo = null;
		
		// 로그인 세션 추가
		if(selectCnt>0) {
			vo = dao.getMemberInfo(strId);
			req.getSession().setAttribute("member", vo);
			System.out.println("role: " + vo.getRole());
		} 
		
		req.setAttribute("selectCnt", selectCnt);
	}
	
	// 회원가입 - 아이디 중복확인
	@Override
	public void idDupChk(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][idDupChk()]");
		// 3단계. 화면에서 입력받은 값을 추출
		String strId = req.getParameter("id");
		
		// 5단계. 중복확인 처리
		int selectCnt = dao.idDupChk(strId);
		
		// 6단계. jsp로 결과 전달(request로 처리결과 저장)
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("id", strId);
	}
	
	// 회원가입 - DB INSERT
	@Override
	public void signInAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][signInActions()]");
		
		// 랜덤 key 생성
		StringBuffer temp = new StringBuffer();
		Random rd = new Random();
		
		for(int i=0; i<6; i++) {
			int rIndex = rd.nextInt(2);
			switch (rIndex) {
			case 0:
				temp.append((char)((int)(rd.nextInt(26))+65));
				break;
			case 1:
				temp.append((rd.nextInt(10)));
				break;
			}
		}
		
		String key = temp.toString();
		
		MemberVO vo = new MemberVO();
		String email = req.getParameter("email");
		String id = req.getParameter("id");
		vo.setId(id);
		vo.setPw(req.getParameter("pw"));
		vo.setName(req.getParameter("name"));
		vo.setEmail(email);
		vo.setPhone(req.getParameter("phone"));
		vo.setRegDate(new Timestamp(System.currentTimeMillis()));
		vo.setRole(Code.GUEST);
		vo.setCondition(Code.NOT_ACTIVATED);
		vo.setKey(key);
		
		int insertCnt = dao.insertMember(vo);
		if(insertCnt==1) {
			MailSendHandler msh = new MailSendHandler();
			msh.sendActivationEmail(id, email, key);
		}
		
		req.setAttribute("insertCnt", insertCnt);
	}
	
	// 회원탈퇴 -> 비밀번호 재확인
	@Override
	public void withdrawMemAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][withdrawMemAction()]");
		MemberVO vo = (MemberVO) req.getSession().getAttribute("member");
		String strId = vo.getId();
		String strPw = req.getParameter("pw");
		
		int selectCnt = dao.idPwChk(strId, strPw);
		int deleteCnt = 0;
		
		if (selectCnt==1) {
			deleteCnt = dao.withrawMember(strId);
		}
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("deleteCnt", deleteCnt);
	}

	// 회원정보 조회
	@Override
	public void viewMemInfoAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][viewMemInfoAction()]");
		MemberVO vo = (MemberVO) req.getSession().getAttribute("member");
		String strId = vo.getId();
		String strPw = req.getParameter("pw");
		
		int selectCnt = dao.idPwChk(strId, strPw);
		
		if (selectCnt==1) {
			vo = dao.getMemberInfo(strId);
		}
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("dto", vo);
		
	}
	
	// 회원정보 수정
	@Override
	public void updateMemInfoAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][updateMemInfoAction()]");
		MemberVO vo = (MemberVO) req.getSession().getAttribute("member");
		
		vo.setId(vo.getId());
		vo.setPw(req.getParameter("pw"));
		vo.setName(req.getParameter("name"));
		vo.setEmail(req.getParameter("email"));
		vo.setPhone(req.getParameter("phone"));
		
		int updateCnt = dao.updateMember(vo);
		
		req.setAttribute("updateCnt", updateCnt);
	}
	
	// 아이디 권한 활성화
	@Override
	public void activateID(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][ActivateID()]");
		
		String key = req.getParameter("key");
		String id = req.getParameter("id");
		System.out.println("id:" + id);
		
		int selectCnt = dao.idKeyChk(id, key);
		int updateCnt = 0;
		if(selectCnt==1) {
			updateCnt = dao.updateCondition(id, Code.NORMAL);
		}
		
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("updateCnt", updateCnt);
	}

}
