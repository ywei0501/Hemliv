package com.hemliv.service;

import java.sql.SQLException;
import java.util.List;

import com.hemliv.dao.AdminProductDao;
import com.hemliv.domain.Product;
import com.hemliv.domain.ProductSort;
import com.hemliv.vo.Condition;

public class AdminProductService {

	//查询所有的商品
	public List<Product> findAllProduct() throws SQLException {
		// TODO Auto-generated method stub
		//没有复杂业务，直接传递请求到dao层
		AdminProductDao dao =new AdminProductDao();
		return dao.findAllProduct();
	}

	public List<ProductSort> findAllProductSort() throws SQLException {
		//获取所有类别
		AdminProductDao dao =new AdminProductDao();
		return dao.findAllProductSort();
	}

	//添加商品
	public void addProduct(Product product) throws SQLException {
		AdminProductDao dao =new AdminProductDao();
		dao.addProduct(product);
	}

	//根据proId删除商品
	public void delProductByid(String proId) throws SQLException {
		AdminProductDao dao =new AdminProductDao();
		dao.delProductByproId(proId);		
	}

	//根据proID 查询商品
	public Product findProductByproId(String proId) throws SQLException {
		AdminProductDao dao =new AdminProductDao();
		return dao.findProductByproId(proId);
	}

	public void updateProduct(Product product) throws SQLException {
		AdminProductDao dao =new AdminProductDao();
	    dao.updateProduct(product);		
	}
	//根据条件查询商品
	public List<Product> findProductListByCondition(Condition condition) throws SQLException {
		AdminProductDao dao =new AdminProductDao();
		return dao.findProductListByCondition(condition);
	}

	public void saveProduct(Product product) throws SQLException {
		AdminProductDao dao =new AdminProductDao();
		dao.saveProduct(product);
	}

}
