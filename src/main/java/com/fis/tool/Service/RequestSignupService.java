package com.fis.tool.Service;

import com.fis.tool.Model.ToolUser;

public interface RequestSignupService {
	
	String sendSignupRequest(ToolUser userData);
	
	String validateSignupRequest(ToolUser userData, String codeSignup);
	
}
