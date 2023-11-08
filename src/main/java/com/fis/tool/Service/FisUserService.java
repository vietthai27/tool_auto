package com.fis.tool.Service;

import com.fis.tool.Model.UserModel;

public interface FisUserService {
	
	void autoCheckService(String username, String password, String checkType);
	
	void autoCallCheckInService();
	
	void autoCallCheckOutService();
	
	String login(UserModel userModel);

}
