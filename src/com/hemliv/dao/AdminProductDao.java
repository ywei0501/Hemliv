package com.hemliv.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.taglibs.standard.lang.jstl.BeanInfoManager;

import com.hemliv.domain.Product;
import com.hemliv.domain.ProductSort;
import com.hemliv.utils.C3P0Utils;
import com.hemliv.vo.Condition;

public class AdminProductDao {

	//查询所有商品信息
	public List<Product> findAllProduct() throws SQLException {
		QueryRunner runner =new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product";
		List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class));
		return productList;
	}
	//查询所有类别信息
	public List<ProductSort> findAllProductSort() throws SQLException{
		QueryRunner runner =new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from productsort";
		List<ProductSort> productsortList = runner.query(sql, new BeanListHandler<ProductSort>(ProductSort.class));
		return productsortList;
	}
	//添加商品信息
	public void addProduct(Product product) throws SQLException {
		QueryRunner runner =new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?)";
		runner.update(sql, product.getProId(),product.getProName(),product.getProFormat(),product.getProPrice(),
				product.getProductsort().getProSid(),product.getProImage(),product.getProdesc());
	}
	//根据proId删除商品信息
	public void delProductByproId(String proId) throws SQLException {
		QueryRunner runner =new QueryRunner(C3P0Utils.getDataSource());
		String sql = "delete from product where proid=?";	
		runner.update(sql, proId);
	}
	//根据proId查询某个商品的信息
	public Product findProductByproId(String proId) throws SQLException {
		QueryRunner runner =new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where proid=?";
		Product product = runner.query(sql, new BeanHandler<Product>(Product.class), proId);
		return product;
	}
	//编辑某个商品的信息
	public void updateProduct(Product product) throws SQLException {
		QueryRunner runner =new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update product set proname=?,proFormat=?,proPrice=?,proSid=?,proImage=?,prodesc=? where proId=?";
		runner.update(sql,product.getProName(),product.getProFormat(),product.getProPrice(),
				product.getProductsort().getProSid(),product.getProImage(),product.getProdesc(),product.getProId());
	}
	//根据条件查询商品
	public List<Product> findProductListByCondition(Condition condition) throws SQLException {
		QueryRunner runner =new QueryRunner(C3P0Utils.getDataSource());
		//定义一个存储实际参数的容器
		List<String> list = new ArrayList<String>();
		String sql = "select * from product where 1=1 ";
		if(condition.getProName()!=null && !condition.getProName().trim().equals("")) {
			sql+=" and proname like ? ";
			list.add("%"+condition.getProName().trim()+"%");
		}if(condition.getProFormat()!=null && !condition.getProFormat().trim().equals("")) {
			sql+=" and proformat like ? ";
			list.add("%"+condition.getProFormat().trim()+"%");
		}
		if(condition.getProSid()!=null && !condition.getProSid().trim().equals("")) {
			sql+=" and prosid=? ";
			list.add(condition.getProSid().trim());
		}
		List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class), list.toArray());
		return productList;
	}
	public void saveProduct(Product product) throws SQLException {
		QueryRunner runner =new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?)";
		runner.update(sql, product.getProId(),product.getProName(),product.getProFormat(),product.getProPrice(),
				product.getProductsort().getProSid(),product.getProImage(),product.getProdesc());
	}	
}	
