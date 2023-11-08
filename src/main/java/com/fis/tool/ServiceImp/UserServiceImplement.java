package com.fis.tool.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fis.tool.Model.ToolUser;
import com.fis.tool.Repo.UserRepository;
import com.fis.tool.Service.UserService;



@Service
public class UserServiceImplement implements UserService{

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public int checkUsername(String username) {		
		return userRepo.checkUsername(username);
	}

	@Override
	public int checkEmail(String username) {
		return userRepo.checkEmail(username);
	}

	@Override
	public int insertUser(String username, String password, String email) {
		return userRepo.insertUser(username, password, email);
	}

	@Override
	public int getUserId(String username) {
		return userRepo.getUserId(username);
	}

	@Override
	public int insertDefaultRole(int userId) {
		return userRepo.insertDefaultRole(userId);
	}

	@Override
	public ToolUser resetPassword(String newPassword, String username) {
		ToolUser userEdit = userRepo.findByUsername(username).orElse(null);
		userEdit.setPassword(newPassword);
		userEdit = userRepo.save(userEdit);
		return userEdit;
	}

	@Override
	public String getEmail(String username) {
		return userRepo.getEmail(username);
	}

	@Override
	public String changePassword(String username, String newPassword) {
		String encodePassword = encoder.encode(newPassword);
		ToolUser userEdit = userRepo.findByUsername(username).orElse(null);
		userEdit.setPassword(encodePassword);
		userEdit = userRepo.save(userEdit);
		return "Đổi mật khẩu thành công !";
	}
}
