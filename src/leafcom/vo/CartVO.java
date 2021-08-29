package leafcom.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class CartVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	private String smallImg;
	
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
	public String getSmallImg() {
		return smallImg;
	}
	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
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
	@Override
	public String toString() {
		return "CartVO [caId=" + caId + ", meId=" + meId + ", itId=" + itId + ", amount=" + amount + ", regDate="
				+ regDate + ", condition=" + condition + ", price=" + price + ", itName=" + itName + ", smallimg="
				+ smallImg + "]";
	}
	
	
}
