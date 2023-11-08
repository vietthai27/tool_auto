package com.fis.tool.Service;

import com.fis.tool.Model.ToolUser;

public interface UserService {

	int checkUsername(String username);
	
	int checkEmail(String username);
	
	int insertUser(String username, String password, String email);
	
	int getUserId(String username);
	
	int insertDefaultRole(int userId);
	
	String getEmail(String username);
	
	String changePassword(String username, String newPassword);
	
	ToolUser resetPassword(String newPassword, String username);
	
}
