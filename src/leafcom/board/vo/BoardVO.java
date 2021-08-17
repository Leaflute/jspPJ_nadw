package leafcom.board.vo;

import java.sql.Timestamp;

public class BoardVO {
	/*
    num     number(6) PRIMARY KEY,   --글번호
    writer  varchar2(20) NOT NULL,   --작성자
    pw      varchar2(20) NOT NULL,   --비밀번호
    subject varchar2(50) NOT NULL,  --글제목
    content varchar2(4000),          --글내용
    readcount number(6) DEFAULT 0,    --조회수
    ref        number(6) DEFAULT 0,    --답변글 그룹아이디
    ref_step number(6) DEFAULT 0,   --답변글 그룹스텝 
    ref_level number(6) DEFAULT 0,  --답변글 그룹레벨
    regdate TIMESTAMP DEFAULT sysdate, --작성일
    ip varchar2(15)
	*/
	
	private int num;
	private String writer;
	private String pw;
	private String subject;
	private String content;
	private int readCount;
	private int ref;
	private int refStep;
	private int refLevel;
	private Timestamp regDate;
	private String ip;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getRefStep() {
		return refStep;
	}
	public void setRefStep(int refStep) {
		this.refStep = refStep;
	}
	public int getRefLevel() {
		return refLevel;
	}
	public void setRefLevel(int refLevel) {
		this.refLevel = refLevel;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
}
