package leafcom.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafcom.dao.CustomerDAO;
import leafcom.dao.CustomerDAOImpl;

public class CustomerServiceImpl implements CustomerService {
	
	CustomerDAO dao = CustomerDAOImpl.getInstance();

	@Override
	public void getItemList(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getItemDetail(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cartList(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyInCart(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyNow(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDestination(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDestination(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDestination(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderList(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOrder(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refundList(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

}
