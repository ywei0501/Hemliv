package com.hemliv.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.hemliv.domain.Order;
import com.hemliv.utils.C3P0Utils;
import com.hemliv.utils.DataSourceUtils;

public class AdminOrderDao {

	public List<Order> findAllOrderList() throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from orders";
		List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class));
		return orderList;
	}

	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select p.proimage,p.proname,p.proprice,i.count,i.subtotal from product p,orderitem i where p.proid=i.proid and i.oid=?";
		return runner.query(sql,new MapListHandler(), oid);
	}

}
