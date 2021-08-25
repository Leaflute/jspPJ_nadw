package leafcom.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import leafcom.vo.ItemVO;

public interface CustomerDAO {
	
	// 전체 상품 리스트 개수 구하기
	public int getItemCnt(int categoryId);
	
	// 상품 카테고리 맵
	public HashMap<Integer, String> getCategoryMap();
	
	// 상품 카테고리 이름
	String getCategoryName(int categoryId);
	
	// 상품 목록 조회
	public ArrayList<ItemVO> getItemList(int start, int end, int categoryId);
	
	// 상품 상세 페이지
	public ItemVO getItemDetail(int itemId, int categoryId);
	
	// 장바구니 상품 추가();
	public void addCart(List<ItemVO> vo);

	

	
}