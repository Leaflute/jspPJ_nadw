package leafcom.vo;

import java.sql.Timestamp;

public class PostVO {
	
	private int boardId;
	private String meId;
	private String boardName;
	private int postNum;
	private String writer;
	private String title;
	private String content;
	private Timestamp regDate;
	private int hit;
	private int ref;
	private int refLevel;
	private int refStep;
	private String ip;
	private int condition;
	
	public PostVO() {}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
	public String getMeId() {
		return meId;
	}

	public void setMeId(String meId) {
		this.meId = meId;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getRefLevel() {
		return refLevel;
	}

	public void setRefLevel(int refLevel) {
		this.refLevel = refLevel;
	}

	public int getRefStep() {
		return refStep;
	}

	public void setRefStep(int refStep) {
		this.refStep = refStep;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}
}
