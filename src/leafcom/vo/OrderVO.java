  package leafcom.vo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import leafcom.util.Code;

public class OrderVO {
	
	int odId;
	int adId;
	String meId;
	int itId;
	int condition;
	int quantity;
	Timestamp regDate;
	
	int price;
	String itName;
	String smallImg;
	
	private Map<Integer,String> odConMap = null;
	
	public int getOdId() {
		return odId;
	}
	public void setOdId(int odId) {
		this.odId = odId;
	}
	public int getAdId() {
		return adId;
	}
	public void setAdId(int adId) {
		this.adId = adId;
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
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
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
	
	public Map<Integer,String> getOdConMap() {
		this.odConMap = new HashMap<Integer,String>();
		odConMap.put(Code.PURCHASE_REQUEST, "구매요청");
		odConMap.put(Code.PURCHASE_CANCEL, "결제취소");
		odConMap.put(Code.PURCHASE_APPROVAL, "구매승인");
		odConMap.put(Code.SHIPPING, "배송중");
		odConMap.put(Code.SHIPPING_COMPLETE, "배송완료");
		odConMap.put(Code.EXCHANGE_REQUEST, "교환요청");
		odConMap.put(Code.REFUND_REQUEST, "환불요청");
		odConMap.put(Code.REFUND_COMPLETE, "환불완료");
		odConMap.put(Code.PURCHASE_COMPLETE, "구매확정");
		return odConMap;
	}
}
