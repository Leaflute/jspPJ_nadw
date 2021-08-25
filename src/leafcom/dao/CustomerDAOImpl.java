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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		return vo;
	}

	
	@Override
	public void addCart(List<ItemVO> vo) {
		// TODO Auto-generated method stub
		
	}

}
