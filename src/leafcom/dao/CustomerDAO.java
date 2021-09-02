package leafcom.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import leafcom.vo.AddressVO;
import leafcom.vo.CartVO;
import leafcom.vo.ItemVO;
import leafcom.vo.OrderVO;

public interface CustomerDAO {
	
	// ------------ 상품 --------------
	
	// 전체 상품 리스트 개수 구하기
	public int getItemCnt(int categoryId);
	
	// 상품 카테고리 맵
	public HashMap<Integer, String> getCategoryMap();
	
	// 상품 카테고리 이름
	public String getCategoryName(int categoryId);
	
	// 상품 목록 조회
	public ArrayList<ItemVO> getItemList(int start, int end, int categoryId);
	
	// 상품 상세 페이지
	public ItemVO getItemDetail(int itemId);

	// ------------ 장바구니 ------------
	
	// 회원 장바구니 리스트
	public List<CartVO> cartList(String meId);

	// 장바구니 상품 id 리스트
	List<Integer> getItIdList(String meId);
	
	// 장바구니 상세 정보
	CartVO getCartInfo(int caId);
	
	// 장바구니 수량 업데이트
	int updateCart(int caId, int amount);
	
	// 장바구니 리스트 삭제
	int deleteCart(int caId);
	
	// 장바구니 존재 여부
	int getCartCnt(String meId);
	
	// 장바구니 시퀀스 반환
	int cartSeq();

	int insertCart(CartVO vo);

	int insertOrder(OrderVO oVo);

	int insertAddress(AddressVO aVo);

	List<AddressVO> addressList(String meId);

	int addressSeq();

	int updateAddress(AddressVO aVo);

	int deleteAddress(int adId);

	List<OrderVO> orderList(String meId);

	AddressVO getAddressInfo(int adId);

	CartVO getCartInfo2(int itId, String meId);

	AddressVO getPrimaryAddressInfo(String meId);

	int updateOrder(int odId, int condition);

	OrderVO getOrderInfo(int odId);
	
}