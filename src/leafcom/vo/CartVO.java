package leafcom.vo;

import java.sql.Timestamp;

public class CartVO {
	
	// 파라미터나 세션에서 가져오는 항목들
	private int caId;
	private String meId;
	private int itId;
	private int amount;
	// service에서 자동 입력되는 항목들
	private Timestamp regDate;
	private int condition;
	// itemVO에서 가져와야 할 상목들
	private int price;
	private String itName;
	private String smallimg;
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getItName() {
		return itName;
	}
	public void setItName(String itName) {
		this.itName = itName;
	}
	public String getSmallimg() {
		return smallimg;
	}
	public void setSmallimg(String smallimg) {
		smallimg = smallimg;
	}
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
