package com.hemliv.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hemliv.dao.ProductDao;
import com.hemliv.domain.Order;
import com.hemliv.domain.Product;
import com.hemliv.domain.ProductSort;
import com.hemliv.utils.DataSourceUtils;
import com.hemliv.vo.PageBean;

public class ProductService {

	public List<Product> findAllProduct() throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.findAllProduct();
	}
	//分页操作
	/**
//	public PageBean findPageBean(int currentPage,int currentCount) throws SQLException {
//		
//		ProductDao dao = new ProductDao();
//		
//		// 封装pagebean  并返回
//		PageBean pageBean = new PageBean();
//		//1、总条数private int totalCount;
//		int totalCount = dao.getTotalCount();
//		pageBean.setTotalCount(totalCount);
//		//2、总页数private int totalPage;
//		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
//		pageBean.setTotalPage(totalPage);
//		//3、当前页数private int currentPage;
//		pageBean.setCurrentPage(currentPage);
//		//4、当前页显示的条数private int currentCount;
//		pageBean.setCurrentCount(currentCount);
//		//5、每页显示的数据private List<T> productList = new ArrayList<T>();
//		
//		/**
//		 * 页数与limit起始索引的关系
//		 * 例如每页显示4条
//		 * 页数            起始索引                     每页显示条数
//		 * 1 		0			4
//		 * 2		4			4
//		 * 3		8			4
//		 * 4		12			4
//		 * 
//		 * 索引index=(当前页数-1) * 每页显示的条数
//		 */
//		int index = (currentPage-1)*currentCount;
//		
//		List<Product> productList = dao.findProductListForPageBean(index,currentCount);
//		pageBean.setProductList(productList);
//		return pageBean;
//	}


	//根据关键字查询商品
	public List<Object> findProductByWord(String word) throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.findProductByWord(word);
	}
	public List<ProductSort> findAllProductSort() {
		ProductDao dao = new ProductDao();
		List<ProductSort> productsortList = null;
		try {
			productsortList = dao.findAllProductSort();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productsortList;
	}
	public PageBean findProductListByProSid(String proSid,int currentPage,int currentCount) {
		ProductDao dao = new ProductDao();
		
		//封装一个pagebean
		PageBean pageBean = new PageBean();
		
		
		//1.封装当前页
		pageBean.setCurrentPage(currentPage);
		//2.封装每页显示的条数
		pageBean.setCurrentCount(currentCount);
		//3.封装总条数
		int totalCount = 0;
		try {
			totalCount = dao.getTotalCount(proSid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		//4.封装总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//5.每页显示的数据
		//select * from product where prosid= ? limit ?,?
		int index = (currentPage-1)*currentCount;
		List<Product> list = null;
		try {
			list = dao.findProdutByPage(proSid,index,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setList(list);
		
		return pageBean;
	}
	public Product findProductInfoById(String proId) {
		ProductDao dao = new ProductDao();
		Product product = null;
		try {
			product = dao.findProductInfoById(proId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}
	//提交订单
	public void submitOrder(Order order) {
		ProductDao dao = new ProductDao();
		
		
		try {
			//1、开启事务
			DataSourceUtils.startTransaction();
			//2、调用dao存储order表数据的方法
			dao.addOrders(order);
			//3、调用dao存储orderitems表数据的方法
			dao.addOrderItems(order);
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void updateOrderAdrr(Order order) {
		ProductDao dao = new ProductDao();
		try {
			dao.updateOrderAdrr(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public List<Order> findAllOrders(String memId) {
		ProductDao dao = new ProductDao();
		List<Order> orderList = null;
		try {
			orderList = dao.findAllOrders(memId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
	public List<Map<String, Object>> findAllOrderItemByOid(String oid) {
		ProductDao dao = new ProductDao();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = dao.findAllOrderItemByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}
	
}
