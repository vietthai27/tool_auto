package com.fis.tool.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis.tool.JWT.JwtAuthenProvider;
import com.fis.tool.JWT.JwtUtil;
import com.fis.tool.Model.ToolUser;
import com.fis.tool.Repo.UserRepository;
import com.fis.tool.ServiceImp.RequestResetPassServiceImplement;
import com.fis.tool.ServiceImp.RequestSignUpServiceImplement;
import com.fis.tool.ServiceImp.UserServiceImplement;

@CrossOrigin("*")
@RestController

public class UserController {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	JwtAuthenProvider jwtAuth;

	@Autowired
	RequestSignUpServiceImplement rqSignupSrvImp;

	@Autowired
	RequestResetPassServiceImplement rqResetPassSrvImp;
	
	@Autowired
	UserServiceImplement userSrvImp;
	
	@Autowired
	UserRepository userRepo;

	@PostMapping("/login")
	public String login(@RequestBody ToolUser userData) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userData.getUsername(),
				userData.getPassword());
		jwtAuth.authenticate(token);
		String jwtToken = jwtUtil.generate(userData.getUsername());
		return jwtToken;
	}
	
	@PostMapping("/getUserData")
	public List<ToolUser> getUserData () {
		return userRepo.findAll();
	}

	@PostMapping("/signupRequest")
	public String signupRequest(@RequestBody ToolUser userData) {
		return rqSignupSrvImp.sendSignupRequest(userData);
	}

	@PostMapping("/signupValidate")
	public String signupValidate(@RequestBody ToolUser userData, @RequestParam String codeSignup) {
		return rqSignupSrvImp.validateSignupRequest(userData, codeSignup);
	}

	@PostMapping("/resetPassRequest")
	public String resetPassRequest(@RequestParam String username) throws Exception {
		return rqResetPassSrvImp.sendResetPassRequest(username);
	}

	@PostMapping("/resetPassValidate")
	public String resetPassValidate(@RequestParam String username, @RequestParam String codeResetPass) {
		return rqResetPassSrvImp.validateResetPassRequest(username, codeResetPass);
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam String username, @RequestParam String newPassword) {
		return userSrvImp.changePassword(username, newPassword);		 
	}

}
