package com.hemliv.service;

import java.sql.SQLException;
import java.util.List;

import com.hemliv.dao.AdminProductSortDao;
import com.hemliv.domain.ProductSort;

public class AdminProductSortService {

	public List<ProductSort> findAllProductSort() {
		AdminProductSortDao dao = new AdminProductSortDao();
		try {
			return dao.findAllProductSort();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void addProductSort(ProductSort productsort) throws SQLException {
		AdminProductSortDao dao = new AdminProductSortDao();
		dao.addProductSort(productsort);
	}


	public void delProductSort(String proSid) throws SQLException {
		AdminProductSortDao dao = new AdminProductSortDao();
		dao.delProductSort(proSid);
	}
	//编辑类别信息---数据回显
	public ProductSort findProductSortById(String proSid) throws SQLException {
		AdminProductSortDao dao = new AdminProductSortDao();
		return dao.findProductSortById(proSid);
	}
	//编辑类别信息
	public void updateProductSort(ProductSort productsort) throws SQLException {
		AdminProductSortDao dao = new AdminProductSortDao();
		dao.updateProductSort(productsort);
	}

}
