package com.hemliv.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.hemliv.domain.Product;
import com.hemliv.domain.ProductSort;
import com.hemliv.utils.C3P0Utils;

public class AdminProductSortDao {

	//查询类别所有信息
	public List<ProductSort> findAllProductSort() throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from productsort";
		List<ProductSort> productsortList = runner.query(sql, new BeanListHandler<ProductSort>(ProductSort.class));
		return productsortList;
	}
	//添加类别信息
	public void addProductSort(ProductSort productsort) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into productsort values(?,?)";
		runner.update(sql,productsort.getProSid(),productsort.getProSName());
		
	}
	//根据proSid删除某类别
	public void delProductSort(String proSid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "delete from productsort where prosid=?";
		runner.update(sql, proSid);
		
	}
	//根据proSid查询某类别信息-----数据回显
	public ProductSort findProductSortById(String proSid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select *from productsort where prosid=?";
		ProductSort productsort = runner.query(sql, new BeanHandler<ProductSort>(ProductSort.class), proSid);
		return productsort;
	}
	//编辑类别信息
	public void updateProductSort(ProductSort productsort) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update productsort set prosname=? where prosid=?";
		runner.update(sql,productsort.getProSName(),productsort.getProSid());
	}

}
