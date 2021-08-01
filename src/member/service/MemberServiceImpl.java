package member.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.dao.MemberDAO;
import member.dao.MemberDAOImpl;
import member.vo.MemberVO;

public class MemberServiceImpl implements MemberService {
	
	// 로그인 처리
	@Override
	public void loginAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[Member][service][loginAction()]");
		
		String strEmail = req.getParameter("email");
		String strPw = req.getParameter("pw");
		
		MemberDAO dao = MemberDAOImpl.getInstance();
		
		int selectCnt = dao.idPwChk(strEmail, strPw);
		
		// 로그인 세션 추가
		if(selectCnt==1) {
			req.getSession().setAttribute("sessionID", strEmail);
			req.getSession().setAttribute("isAdmin", 0);
		} else if (selectCnt==2) {
			req.getSession().setAttribute("sessionID", strEmail);
			req.getSession().setAttribute("isAdmin", 1);
		}
		
		req.setAttribute("selectCnt", selectCnt);
	}
	
	// 회원가입 - 이메일 중복확인
	@Override
	public void confirmEmail(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[Member][service][confirmEmail()]");
		// 3단계. 화면에서 입력받은 값을 추출
		String strEmail = req.getParameter("email");
		
		// 4단계. dao객체 생성
		MemberDAO dao = MemberDAOImpl.getInstance();
		
		// 5단계. 중복확인 처리
		int selectCnt = dao.emailDupChk(strEmail);
		
		// 6단계. jsp로 결과 전달(request로 처리결과 저장)
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("email", strEmail);
	}
	
	// 회원가입 - DB INSERT
	@Override
	public void signInAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[Member][service][signInActions()]");
		MemberVO vo = new MemberVO();
		
		vo.setEmail(req.getParameter("email"));
		vo.setPw(req.getParameter("pw"));
		vo.setName(req.getParameter("name"));
		vo.setPhone(req.getParameter("phone"));
		vo.setRegDate(new Timestamp(System.currentTimeMillis()));
		vo.setCondition(1);
		vo.setIsAdmin(0);
		
		MemberDAO dao = MemberDAOImpl.getInstance();
		
		int insertCnt = dao.insertMember(vo);
		
		req.setAttribute("insertCnt", insertCnt);
	}

	@Override
	public void withdrawMemAction(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewMemInfoAction(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyMemInfoAction(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

}
