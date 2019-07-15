package com.hemliv.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hemliv.domain.Order;
import com.hemliv.domain.OrderItem;
import com.hemliv.domain.Product;
import com.hemliv.domain.ProductSort;
import com.hemliv.utils.C3P0Utils;
import com.hemliv.utils.DataSourceUtils;

public class ProductDao {

	public List<Product> findAllProduct() throws SQLException {
		return new QueryRunner(C3P0Utils.getDataSource()).query("select * from product",new BeanListHandler<Product>(Product.class));
		
	}
	//获得商品的条数   
	public int getTotalCount(String proSid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select count(*) from product where prosid=?";
		Long query = (Long) runner.query(sql, new ScalarHandler(),proSid);
		return query.intValue();
	}
	//获得分页的卧室系列商品数据
	public List<Product> findProductListForPageBean(int index,int currentPage) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		//第一个“ ？ ”代表索引开始，第二个“ ? ”代表 每页显示的条数  
		String sql="select * from product WHERE prosid=(select prosid from productsort where ProSName='卧室系列') limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), index,currentPage);
	}
	public List<Object> findProductByWord(String word) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where proname like ? limit 0,8";
		List<Object> query = (List<Object>) runner.query(sql, new ColumnListHandler("proname"), "%"+word+"%");
		return query;
	}
	public List<ProductSort> findAllProductSort() throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from productsort";
		return runner.query(sql, new BeanListHandler<ProductSort>(ProductSort.class));
		
	}
	public List<Product> findProdutByPage(String proSid, int index, int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where prosid=? limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class),proSid,index,currentCount);
		return list;
	}
	public Product findProductInfoById(String proId) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where proid=? ";
		return runner.query(sql, new BeanHandler<Product>(Product.class),proId);
		 
	}
	//向orders表插入数据
	public void addOrders(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		runner.update(conn,sql,order.getOid(),order.getMemberinfo().getMemId(),order.getName(),order.getTelephone(),
				order.getAddress(),order.getTotal(),order.getOrdertime(),order.getState());
	}
	//向orderitem表插入数据
	public void addOrderItems(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		List<OrderItem> orderItems = order.getOrderItems();
		for(OrderItem item : orderItems) {
			runner.update(conn,sql,item.getItemid(),item.getProduct().getProId(),item.getOrder().getOid(),
					item.getCount(),item.getSubtotal());
		}
	}
	public void updateOrderAdrr(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set address=?,name=?,telephone=?,state=? where oid=?";
		runner.update(sql,order.getAddress(),order.getName(),order.getTelephone(),1,order.getOid());
	}
	public List<Order> findAllOrders(String memId) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where memid=?";
		return runner.query(sql, new BeanListHandler<Order>(Order.class), memId);
	}
	public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select i.count,i.subtotal,p.proImage,p.proName,p.proPrice from orderitem i,product p where i.proid=p.proid and i.oid=?";
		List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
		return mapList;
	}

}
