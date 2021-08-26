package leafcom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import leafcom.vo.CartVO;
import leafcom.vo.ItemVO;

public class CustomerDAOImpl implements CustomerDAO{
	// 싱글톤 방식으로 객체 생성
	private static CustomerDAOImpl instance = new CustomerDAOImpl();
	
	public static CustomerDAOImpl getInstance() {
		if(instance == null) {
			instance = new CustomerDAOImpl();
		}
		return instance;
	}
		
	// 커넥션 풀 객체
	DataSource dataSource;
	
	// 생성자에 커넥션 풀 생성
	private CustomerDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/leafcom");		
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 카테고리별 상품 숫자 구하기
	@Override
	public int getItemCnt(int categoryId) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) cnt FROM item\r\n";
			
			if (categoryId==0) {
				pstmt = conn.prepareStatement(sql);
				
			} else {
				sql += "WHERE cg_id = ?";	
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, categoryId);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				selectCnt = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
				if(rs!=null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return selectCnt;
	}
	
	// 카테고리 이름 반환 맵
	@Override
	public HashMap<Integer, String> getCategoryMap() {
		HashMap<Integer, String> categoryMap = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM categories";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				categoryMap = new HashMap<Integer,String>();
				
				do {
					categoryMap.put(rs.getInt("cg_id"), rs.getString("cg_name"));
				} while (rs.next());
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
				if(rs!=null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return categoryMap;
	}
	
	// 카테고리 이름에 따른 카테고리 이름 반환
	@Override
	public String getCategoryName(int categoryId) {
		String categoryName = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM categories WHERE cg_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				categoryName = rs.getString("cg_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return categoryName;
	}
	
	// 상품 목록 구하기
	@Override
	public ArrayList<ItemVO> getItemList(int start, int end, int categoryId) {
		ArrayList<ItemVO> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM item_v\r\n";
			
			if (categoryId==0) {
				sql +=	"WHERE rNum >= ? AND rNum <=?";
				
				pstmt = conn.prepareStatement(sql);	
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
			} else {
			
				sql +=	"WHERE rNum >= ? AND rNum <=? AND cg_id = ?";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			pstmt.setInt(3, categoryId);
		
			}
			
			rs = pstmt.executeQuery();

			// 결과가 존재하면
			if (rs.next()) {
				
				// 2. 큰바구니(ArrayList) 생성
				list = new ArrayList<ItemVO>();
				
				do {
					// 3. 작은바구니(PostVO) 생성
					ItemVO vo = new ItemVO();
					
					// 4. 한 건을 읽어서 rs결과를 setter로 작은 바구니에 담음
					vo.setItemId(rs.getInt("it_id"));
					vo.setCategoryId(rs.getInt("cg_id"));
					vo.setCategoryName(rs.getString("cg_name"));
					vo.setItemName(rs.getString("it_name"));
					vo.setCompany(rs.getString("it_company"));
					vo.setSmallImg(rs.getString("it_small_img"));
					vo.setLargeImg(rs.getString("it_large_img"));
					vo.setDetailImg(rs.getString("it_detail_img"));
					vo.setRegDate(rs.getTimestamp("it_regdate"));
					vo.setInfo(rs.getString("it_info"));
					vo.setStock(rs.getInt("it_stock"));
					vo.setCost(rs.getInt("it_cost"));
					vo.setPrice(rs.getInt("it_price"));
					vo.setGrade(rs.getInt("it_grade"));
					
					// 5. 큰 바구니에 작은 바구니를 담음
					list.add(vo);
					
				} while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		return list;
	}
	
	// 상품 상세 정보 호출
	@Override
	public ItemVO getItemDetail(int itemId, int categoryId) {
		ItemVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM item_v WHERE it_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				vo = new ItemVO();
				
				vo.setItemId(rs.getInt("it_id"));
				vo.setCategoryId(rs.getInt("cg_id"));
				vo.setCategoryName(rs.getString("cg_name"));
				vo.setItemName(rs.getString("it_name"));
				vo.setCompany(rs.getString("it_company"));
				vo.setSmallImg(rs.getString("it_small_img"));
				vo.setLargeImg(rs.getString("it_large_img"));
				vo.setDetailImg(rs.getString("it_detail_img"));
				vo.setRegDate(rs.getTimestamp("it_regdate"));
				vo.setInfo(rs.getString("it_info"));
				vo.setStock(rs.getInt("it_stock"));
				vo.setCost(rs.getInt("it_cost"));
				vo.setPrice(rs.getInt("it_price"));
				vo.setGrade(rs.getInt("it_grade"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
			if(rs!=null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		return vo;
	}
	
	// 장바구니 상품추가
	@Override
	public int addCart(List<CartVO> list, int itId) {
		int addCnt = 0;
		int updateCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "";
			
			for (CartVO vo:list) {
				List<Integer> itIdList= getItemId(vo.getMeId());
				
				if (itIdList.contains(vo.getItId())) {
					updateCnt += updateCart(vo.getCaId(),vo.getAmount());
					
				} else {
					sql = "INSERT INTO cart VALUES (cart_num_seq.nextval, ?, ?, ?, ?, ?)";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, vo.getMeId());
					pstmt.setInt(2, vo.getItId());
					pstmt.setInt(3, vo.getAmount());
					pstmt.setTimestamp(4, vo.getRegDate());
					pstmt.setInt(5, vo.getCondition());
					
					addCnt += pstmt.executeUpdate();
					System.out.println("새 품목 추가");
				}
			}
			System.out.println("수정된 상품 수: " + addCnt);
			System.out.println("추가된 상품 수: " + updateCnt);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return addCnt+updateCnt;
		
	}
	
	// 장바구니 리스트
	@Override
	public List<CartVO> cartList(String meId) {
		List<CartVO> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT c.ca_id ca_id,"
					+ "			c.me_id me_id,"
					+ "			c.it_id it_id,"
					+ "			c.ca_amount ca_amount,"
					+ "			c.ca_regDate ca_regDate,"
					+ "			c.ca_condition ca_condition,"
					+ "			i.it_price it_price,"
					+ "			i.it_name it_name,"
					+ "			i.it_small_img it_small_img"
					+ "		FROM cart c, item i "
					+ "	   WHERE c.it_id = i.it_id"
					+ "	 	 AND c.me_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, meId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				list = new ArrayList<CartVO>();
				
				do {
					CartVO vo = new CartVO();
					
					vo.setCaId(rs.getInt("ca_id"));
					vo.setMeId(meId);
					vo.setItId(rs.getInt("it_id"));
					vo.setAmount(rs.getInt("ca_amount"));
					vo.setRegDate(rs.getTimestamp("ca_regDate"));
					vo.setCondition(rs.getInt("ca_condition"));
					vo.setPrice(rs.getInt("it_price"));
					vo.setItName(rs.getString("it_name"));
					vo.setSmallimg(rs.getString("it_small_img"));
					
					list.add(vo);
					
				} while(rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
				if(rs!=null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
		return list;
	}
	
	// 회원이 주문한 상품 번호 리스트 구하기
	@Override
	public List<Integer> getItemId(String meId) {
		int itId = 0;
		List<Integer> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT it_Id FROM cart WHERE me_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, meId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list.add(rs.getInt("it_id"));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
				if(rs!=null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return list;
	}
	
	// 수량 조정
	@Override
	public int updateCart(int caId, int amount) {
		int updateCnt = 0;
		String sql = "UPDATE cart SET ca_amount ca_id = ?";
		try (	
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt =  conn.prepareStatement(sql);
			) {
			pstmt.setInt(1, caId);
			updateCnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updateCnt;
	}
	
	// 장바구니 상품 삭제
	@Override
	public int deleteCart(List<Integer> caIdList) {
		int deleteCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			for (int caId:caIdList) {
				String sql = "DELETE FROM cart WHERE ca_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, caId);
				deleteCnt = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return deleteCnt;
	}
	
	// 
}
