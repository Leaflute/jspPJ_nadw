package leafcom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import leafcom.vo.ItemVO;

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
			String sql = "SELECT COUNT(*) cnt FROM item\r\n";
			
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
	
	// 카테고리 이름 맵
	@Override
	public HashMap<Integer, String> getCategoryName() {
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
					categoryMap.put(rs.getInt("category_id"), rs.getString("category_name"));
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
			
				sql +=	"WHERE rNum >= ? AND rNum <=? AND category_id = ?";
			
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
					vo.setItemId(rs.getInt("item_id"));
					vo.setCategoryId(rs.getInt("category_id"));
					vo.setCategoryName(rs.getString("category_name"));
					vo.setItemName(rs.getString("item_name"));
					vo.setCompany(rs.getString("item_company"));
					vo.setSmallImg(rs.getString("item_small_img"));
					vo.setLargeImg(rs.getString("item_large_img"));
					vo.setDetailImg(rs.getString("item_detail_img"));
					vo.setRegDate(rs.getTimestamp("item_regdate"));
					vo.setInfo(rs.getString("item_info"));
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
	
	// 상품 상세 정보 호출
	@Override
	public ItemVO getItemDetail(int itemId, int categoryId) {
		ItemVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM item_v WHERE item_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				vo = new ItemVO();
				
				vo.setItemId(rs.getInt("item_id"));
				vo.setCategoryId(rs.getInt("category_id"));
				vo.setCategoryName(rs.getString("category_name"));
				vo.setItemName(rs.getString("item_name"));
				vo.setCompany(rs.getString("item_company"));
				vo.setSmallImg(rs.getString("item_small_img"));
				vo.setLargeImg(rs.getString("item_large_img"));
				vo.setDetailImg(rs.getString("item_detail_img"));
				vo.setRegDate(rs.getTimestamp("item_regdate"));
				vo.setInfo(rs.getString("item_info"));
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
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "INSERT INTO item VALUES (";
			
			switch (vo.getCategoryId()) {
				case 1 :
					sql += "cpu";
					break;
				case 2 :
					sql += "ram";
					break;
				case 3 :
					sql += "mb";
					break;
				case 4 :
					sql += "gpu";
					break;
				case 5 :
					sql += "powsup";
					break;
				case 6 :
					sql += "ssd";
					break;
				case 7 :
					sql += "hdd";
					break;
				case 8 :
					sql += "case";
					break;
				case 9 :
					sql += "mon";
					break;					
			}
			sql += "_num_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getCategoryId());
			pstmt.setString(2, vo.getItemName());
			pstmt.setString(3, vo.getCompany());
			pstmt.setString(4, vo.getSmallImg());
			pstmt.setString(5, vo.getLargeImg());
			pstmt.setString(6, vo.getDetailImg());
			pstmt.setTimestamp(7, vo.getRegDate());
			pstmt.setString(8, vo.getInfo());
			pstmt.setInt(9, vo.getQuantity());
			pstmt.setInt(10, vo.getCost());
			pstmt.setInt(11, vo.getPrice());
			
			insertCnt = pstmt.executeUpdate();
			
			System.out.println("[ad][dao]insertCnt: "+ insertCnt + " ]");
			
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
		return insertCnt;
	}

	@Override
	public int updateItem(ItemVO vo) {
		int updateCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql="UPDATE item SET "
					+ "category_id = ?, "
					+ "item_name = ?, "
					+ "item_company = ?, "
					+ "item_small_img = ?, "
					+ "item_large_img = ?, "
					+ "item_detail_img = ?, "
					+ "item_regdate = ?, "
					+ "item_info = ?, "
					+ "item_quantity = ?, "
					+ "item_cost = ?, "
					+ "item_price = ?, "
					+ "item_grade = ? "
					+ "WHERE item_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getCategoryId());
			pstmt.setString(2, vo.getItemName());
			pstmt.setString(3, vo.getCompany());
			pstmt.setString(4, vo.getSmallImg());
			pstmt.setString(5, vo.getLargeImg());
			pstmt.setString(6, vo.getDetailImg());
			pstmt.setTimestamp(7, vo.getRegDate());
			pstmt.setString(8, vo.getInfo());
			pstmt.setInt(9, vo.getQuantity());
			pstmt.setInt(10, vo.getCost());
			pstmt.setInt(11, vo.getPrice());
			pstmt.setDouble(12, vo.getGrade());
			pstmt.setInt(13, vo.getItemId());
			
			updateCnt = pstmt.executeUpdate();
			
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
		
		return updateCnt;
	}

	@Override
	public int deleteItem(int itemId) {
		int deleteCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "DELETE FROM item WHERE item_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			
			deleteCnt = pstmt.executeUpdate();
		
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
	
}
