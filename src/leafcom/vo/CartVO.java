package leafcom.vo;

import java.sql.Timestamp;

public class CartVO {
	
	private int caId;
	private String meId;
	private int itId;
	private int amount;
	private Timestamp regDate;
	private int condition;
	
	public int getCaId() {
		return caId;
	}
	public void setCaId(int caId) {
		this.caId = caId;
	}
	public String getMeId() {
		return meId;
	}
	public void setMeId(String meId) {
		this.meId = meId;
	}
	public int getItId() {
		return itId;
	}
	public void setItId(int itId) {
		this.itId = itId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	
	
}
