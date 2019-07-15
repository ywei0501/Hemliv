package com.hemliv.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.hemliv.domain.MemberInfo;
import com.hemliv.utils.DataSourceUtils;

public class AdminMemberInfoDao {

	public List<MemberInfo> findAllMemberInfoList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from memberinfo";
		List<MemberInfo> list = runner.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class));
		return list;
	}

	public void delMemberInfo(String memId) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from memberinfo where memid=?";	
		runner.update(sql,memId);
	}

}
