package com.hemliv.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hemliv.dao.AdminOrderDao;
import com.hemliv.domain.Order;

public class AdminOrderService {

	public List<Order> findAllOrderList() throws SQLException {
		AdminOrderDao dao = new AdminOrderDao();
		List<Order> orderList = dao.findAllOrderList();
		return orderList;
	}

	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		AdminOrderDao dao = new AdminOrderDao();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = dao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapList;
	}

}
