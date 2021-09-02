package leafcom.dao;

import java.sql.CallableStatement;
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

import leafcom.util.Code;
import leafcom.vo.ItemVO;
import leafcom.vo.OrderVO;
import leafcom.vo.ReportVO;

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
	
	// 상품 추가
	@Override
	public int insertItem(ItemVO vo) {
		int insertCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "INSERT INTO item VALUES (";
			
			switch (vo.getCategoryId()) {
				case Code.CPU :
					sql += "cpu";
					break;
				case Code.RAM :
					sql += "ram";
					break;
				case Code.MBD :
					sql += "mbd";
					break;
				case Code.GPU :
					sql += "gpu";
					break;
				case Code.PWS :
					sql += "pws";
					break;
				case Code.SSD :
					sql += "ssd";
					break;
				case Code.HDD :
					sql += "hdd";
					break;
				case Code.CSE :
					sql += "cse";
					break;
				case Code.MNT :
					sql += "mnt";
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
			pstmt.setInt(9, vo.getStock());
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
	
	// 상품 수정
	@Override
	public int updateItem(ItemVO vo) {
		int updateCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql="UPDATE item SET "
					+ "cg_id = ?, "
					+ "it_name = ?, "
					+ "it_company = ?, "
					+ "it_small_img = ?, "
					+ "it_large_img = ?, "
					+ "it_detail_img = ?, "
					+ "it_regdate = ?, "
					+ "it_info = ?, "
					+ "it_stock = ?, "
					+ "it_cost = ?, "
					+ "it_price = ?, "
					+ "it_grade = ? "
					+ "WHERE it_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getCategoryId());
			pstmt.setString(2, vo.getItemName());
			pstmt.setString(3, vo.getCompany());
			pstmt.setString(4, vo.getSmallImg());
			pstmt.setString(5, vo.getLargeImg());
			pstmt.setString(6, vo.getDetailImg());
			pstmt.setTimestamp(7, vo.getRegDate());
			pstmt.setString(8, vo.getInfo());
			pstmt.setInt(9, vo.getStock());
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
	
	// 상품 삭제
	@Override
	public int deleteItem(int itemId) {
		int deleteCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "DELETE FROM item WHERE it_id = ?";
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
	
	
	// 주문상태별 주문목록 개수 구하기
	@Override
	public int getOrderCnt(int condition) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) cnt FROM orders ";
			
			if (condition==0) {
				pstmt = conn.prepareStatement(sql);
				
			} else {
				sql += "WHERE od_condition = ?";	
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, condition);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				cnt = rs.getInt("cnt");
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
		
		return cnt;
	}
	
	
	// 상품 목록 구하기
	@Override
	public List<OrderVO> orderList(int start, int end, int condition) {
		List<OrderVO> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM order_v ";
			
			if (condition==0) {
				sql +=	"WHERE rNum >= ? AND rNum <= ?";
				
				pstmt = conn.prepareStatement(sql);	
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
			
			} else {
				sql +=	"WHERE rNum >= ? AND rNum <=? AND od_condition = ?";
			
				pstmt = conn.prepareStatement(sql);		
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				pstmt.setInt(3, condition);
		
			}
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				list = new ArrayList<OrderVO>();
				
				do {
					OrderVO oVo = new OrderVO();
					
					oVo.setOdId(rs.getInt("od_id"));
					oVo.setMeId(rs.getString("me_id"));
					oVo.setItId(rs.getInt("it_id"));
					oVo.setAdId(rs.getInt("ad_id"));
					oVo.setQuantity(rs.getInt("od_quantity"));
					oVo.setRegDate(rs.getTimestamp("od_regDate"));
					oVo.setCondition(rs.getInt("od_condition"));
					oVo.setPrice(rs.getInt("it_price"));
					oVo.setItName(rs.getString("it_name"));
					oVo.setSmallImg(rs.getString("it_small_img"));
					
					list.add(oVo);
					
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
	
	// 주문 상세 정보
	@Override
	public OrderVO orderInfo(int odId) {
		OrderVO oVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM orders WHERE od_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, odId);
			
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				oVo = new OrderVO();
				oVo.setOdId(rs.getInt("od_id"));
				oVo.setAdId(rs.getInt("ad_id"));
				oVo.setMeId(rs.getString("me_id"));
				oVo.setItId(rs.getInt("it_id"));
				oVo.setCondition(rs.getInt("od_condition"));
				oVo.setQuantity(rs.getInt("od_quantity"));
				oVo.setRegDate(rs.getTimestamp("od_regdate"));
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
		return oVo;
	}
	
	// 주문정보 수정
	@Override
	public int updateOrder(int odId, int condition) {
		int updateCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "UPDATE orders "
					   + "	 SET od_condition = ?"
					   + " WHERE od_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, condition);
			pstmt.setInt(2, odId);
			
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
	
	// 재고 감소 프로시저
	@Override
	public int stockReduce(int itId, int quantity) {
		int reduceCnt = 0;
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "{call item_stock_minus(?,?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, itId);
			cstmt.setInt(2, quantity);
			
			reduceCnt = cstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(cstmt!=null) cstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return reduceCnt;	
	}
	
	// 재고 증가 프로시저
	@Override
	public int stockIncrease(int itId, int quantity) {
		int reduceCnt = 0;
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "{call item_stock_plus(?,?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, itId);
			cstmt.setInt(2, quantity);
			
			reduceCnt = cstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(cstmt!=null) cstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return reduceCnt;	
	}
	
	// 결산(5일간의 매출액과 영업이익)
	@Override
	public List<ReportVO> fiveDayReport() {
		List<ReportVO> reportList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT SUM(i.it_price * o.od_quantity) sales, \r\n" + 
					"       SUM((i.it_price - i.it_cost) * o.od_quantity) margin,\r\n" + 
					"       TO_CHAR(o.od_regdate,'yyyy-fmmm-dd') odate\r\n" + 
					"  FROM orders o, item i\r\n" + 
					" WHERE o.it_id = i.it_id\r\n" + 
					"   AND o.od_condition = 9\r\n" + 
					"   AND o.od_regdate BETWEEN sysdate-5 AND sysdate\r\n" + 
					" GROUP BY TO_CHAR(o.od_regdate,'yyyy-fmmm-dd')";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				reportList = new ArrayList<ReportVO>();
				do {
					ReportVO rVo = new ReportVO(); 
					rVo.setSales(rs.getInt("sales"));
					rVo.setMargin(rs.getInt("margin"));
					rVo.setoDate(rs.getString("odate"));
					reportList.add(rVo);
				} while (rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) pstmt.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
		return reportList;
	}
	

	
}
