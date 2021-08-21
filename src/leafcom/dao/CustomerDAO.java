package leafcom.dao;

import java.util.List;

import leafcom.vo.ItemVO;

public interface CustomerDAO {
	// 장바구니 상품 추가();
	public void addCart(List<ItemVO> vo);
}