package leafcom.dao;

import java.util.ArrayList;
import java.util.HashMap;

import leafcom.vo.ItemVO;

public interface AdminDAO {
	
	// 전체 상품 리스트 개수 구하기
	public int getItemCnt(int categoryId);
	
	// 상품 카테고리 이름 맵
	public HashMap<Integer, String> getCategoryName();
	
	// 상품 목록 조회
	public ArrayList<ItemVO> getItemList(int start, int end, int categoryId);
	
	// 상품 상세 페이지
	public ItemVO getItemDetail(int itemId, int categoryId);
	
	// 상품 추가
	public int insertItem(ItemVO vo);
	
	// 상품 수정
	public int updateItem(ItemVO vo);
	
	// 상품 삭제
	public int deleteItem(int itemId);
}
