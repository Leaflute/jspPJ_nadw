package leafcom.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import leafcom.vo.ReportVO;
import leafcom.vo.ItemVO;
import leafcom.vo.OrderVO;

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

	int getOrderCnt(int condition);

	List<OrderVO> orderList(int start, int end, int condition);

	int updateOrder(int odId, int condition);

	int stockReduce(int itId, int quantity);

	int stockIncrease(int itId, int quantity);

	OrderVO orderInfo(int odId);

	public List<ReportVO> fiveDayReport();
}
