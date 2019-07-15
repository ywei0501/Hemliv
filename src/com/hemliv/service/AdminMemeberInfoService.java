package com.hemliv.service;

import java.sql.SQLException;
import java.util.List;

import com.hemliv.dao.AdminMemberInfoDao;
import com.hemliv.domain.MemberInfo;

public class AdminMemeberInfoService {

	public List<MemberInfo> findAllMemberInfoList() {
		AdminMemberInfoDao dao = new AdminMemberInfoDao();
		List<MemberInfo> membreinfoList = null;
		try {
			membreinfoList = dao.findAllMemberInfoList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return membreinfoList;
	}

	public void delMemberInfo(String memId) {
		AdminMemberInfoDao dao = new AdminMemberInfoDao();
		try {
			dao.delMemberInfo(memId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
