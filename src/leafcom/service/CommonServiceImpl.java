package leafcom.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.CommonDAO;
import leafcom.dao.CommonDAOImpl;
import leafcom.vo.MemberVO;

public class CommonServiceImpl implements CommonService {
	
	// 로그인 처리
	@Override
	public void loginAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][loginAction()]");
		
		String strId = req.getParameter("id");
		String strPw = req.getParameter("pw");
		
		CommonDAO dao = CommonDAOImpl.getInstance();
		
		int selectCnt = dao.idPwChk(strId, strPw);
		
		// 로그인 세션 추가
		if(selectCnt==1) {
			req.getSession().setAttribute("sessionID", strId);
			req.getSession().setAttribute("sessionRole", 0);
		} else if (selectCnt==2) {
			req.getSession().setAttribute("sessionID", strId);
			req.getSession().setAttribute("sessionRole", 1);
		}
		
		req.setAttribute("selectCnt", selectCnt);
	}
	
	// 회원가입 - 아이디 중복확인
	@Override
	public void idDupChk(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][idDupChk()]");
		// 3단계. 화면에서 입력받은 값을 추출
		String strId = req.getParameter("id");
		
		// 4단계. dao객체 생성
		CommonDAO dao = CommonDAOImpl.getInstance();
		
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
		MemberVO vo = new MemberVO();
		
		vo.setId(req.getParameter("id"));
		vo.setPw(req.getParameter("pw"));
		vo.setName(req.getParameter("name"));
		vo.setEmail(req.getParameter("email"));
		vo.setPhone(req.getParameter("phone"));
		vo.setRegDate(new Timestamp(System.currentTimeMillis()));
		vo.setRole(0);
		vo.setCondition(1);
		
		CommonDAO dao = CommonDAOImpl.getInstance();
		
		int insertCnt = dao.insertMember(vo);
		
		req.setAttribute("insertCnt", insertCnt);
	}

	// 회원탈퇴 -> 비밀번호 재확인
	@Override
	public void withdrawMemAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[Member][service][withdrawMemAction()]");
		String strId = (String)req.getSession().getAttribute("sessionID");
		String strPw = req.getParameter("pw");
		
		CommonDAO dao = CommonDAOImpl.getInstance();
		
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
		System.out.println("[Member][service][viewMemInfoAction()]");
		String strId = (String)req.getSession().getAttribute("sessionID");
		String strPw = req.getParameter("pw");
		
		CommonDAO dao = CommonDAOImpl.getInstance();
		
		int selectCnt = dao.idPwChk(strId, strPw);
		
		MemberVO vo = new MemberVO();
		if (selectCnt==1) {
			vo = dao.getMemberInfo(strId);
		}
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("vo", vo);
		
	}
	
	// 회원정보 수정
	@Override
	public void updateMemInfoAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[co][service][updateMemInfoAction()]");
		CommonDAO dao = CommonDAOImpl.getInstance();
		MemberVO vo = new MemberVO();
		
		vo.setId((String)req.getSession().getAttribute("sessionID"));
		vo.setPw(req.getParameter("pw"));
		vo.setName(req.getParameter("name"));
		vo.setEmail(req.getParameter("email"));
		vo.setPhone(req.getParameter("phone"));
		
		int updateCnt = dao.updateMember(vo);
		
		req.setAttribute("updateCnt", updateCnt);
	}

}
