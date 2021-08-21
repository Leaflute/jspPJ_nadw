package leafcom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import leafcom.vo.ItemVO;
import leafcom.vo.PostVO;

public class AdminDAOImpl implements AdminDAO {

	// 싱글톤 방식으로 객체 생성
	private static AdminDAOImpl instance = new AdminDAOImpl();
	
	public static AdminDAOImpl getInstance() {
		if(instance == null) {
			instance = new AdminDAOImpl();
		}
		return instance;
	}
		
	// 커넥션 풀 객체
	DataSource dataSource;
	
	// 생성자에 커넥션 풀 생성
	private AdminDAOImpl() {
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
			String sql = "SELECT COUNT(*) cnt FROM item";
			
			if (categoryId==0) {
				pstmt = conn.prepareStatement(sql);
				
			} else {
				sql += "WHERE category_id = ?";	
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return selectCnt;
	}
	
	// 카테고리 이름 반환
	@Override
	public String getCategoryName(int categoryId) {
		String categoryName = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM categories WHERE categoryId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				categoryName = rs.getString("category_name");
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
			
			String sql = "SELECT * FROM" + 
					"    (SELECT item_id, category_id, item_name, item_company, item_small_img, item_large_img, item_detail_img, " + 
					"            item_detail_img, item_regdate, item_content, item_quantity, item_cost, item_price, item_grade, " + 
					"            ROWNUM rNum" + 
					"            FROM (SELECT * FROM item WHERE category_id = ? ORDER BY item_regdate DESC))" + 
					"	WHERE rNum >= ? AND rNum <=?";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, categoryId);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();

			// 결과가 존재하면
			if (rs.next()) {
				
				// 2. 큰바구니(ArrayList) 생성
				list = new ArrayList<ItemVO>();
				
				do {
					// 3. 작은바구니(PostVO) 생성
					ItemVO vo = new ItemVO();
					String categoryName = getCategoryName(categoryId);
					
					// 4. 한 건을 읽어서 rs결과를 setter로 작은 바구니에 담음
					vo.setItemId(rs.getInt("item_id"));
					vo.setCategoryId(rs.getInt("category_id"));
					vo.setCategoryName(categoryName);
					vo.setItemName(rs.getString("item_name"));
					vo.setCompany(rs.getString("item_company"));
					vo.setSmallImg(rs.getString("item_small_img"));
					vo.setLargeImg(rs.getString("item_large_img"));
					vo.setDetailImg(rs.getString("item_detail_img"));
					vo.setRegDate(rs.getTimestamp("post_regdate"));
					vo.setContent(rs.getString("item_content"));
					vo.setQuantity(rs.getInt("item_quantity"));
					vo.setCost(rs.getInt("item_cost"));
					vo.setPrice(rs.getInt("item_price"));
					vo.setGrade(rs.getInt("item_grade"));
					
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
	
	// 상품 카테고리 구하기
	
	// 상품 상세 페이지
	@Override
	public ItemVO getItemDetail(int itemId, int categoryId) {
		ItemVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM item WHERE item_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				vo = new ItemVO();
				String categoryName = getCategoryName(categoryId);
				
				vo.setItemId(rs.getInt("item_id"));
				vo.setCategoryId(rs.getInt("category_id"));
				vo.setCategoryName(categoryName);
				vo.setItemName(rs.getString("item_name"));
				vo.setCompany(rs.getString("item_company"));
				vo.setSmallImg(rs.getString("item_small_img"));
				vo.setLargeImg(rs.getString("item_large_img"));
				vo.setDetailImg(rs.getString("item_detail_img"));
				vo.setRegDate(rs.getTimestamp("post_regdate"));
				vo.setContent(rs.getString("item_content"));
				vo.setQuantity(rs.getInt("item_quantity"));
				vo.setCost(rs.getInt("item_cost"));
				vo.setPrice(rs.getInt("item_price"));
				vo.setGrade(rs.getInt("item_grade"));
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
			
		return vo;
	}

	
	@Override
	public int insertItem(ItemVO vo) {
		int insertCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		return 0;
	}

	@Override
	public int updateItem(ItemVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteItem(int itemId) {
		// TODO Auto-generated method stub
		return 0;
	}


	
	
}
