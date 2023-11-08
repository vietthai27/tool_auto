package com.fis.tool.Service;

public interface RequestResetPassService {

    String sendResetPassRequest(String username) throws Exception;
	
	String validateResetPassRequest(String username, String codeResetPass);
	
}
