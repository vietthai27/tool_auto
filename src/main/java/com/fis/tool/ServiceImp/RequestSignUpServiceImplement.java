package com.fis.tool.ServiceImp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fis.tool.Model.ToolUser;
import com.fis.tool.Repo.RequestSignupRepository;
import com.fis.tool.Service.RequestSignupService;
import com.fis.tool.Util.GenerateRandomString;
import com.fis.tool.Util.SendEmail;


@Service
public class RequestSignUpServiceImplement implements RequestSignupService {

	@Autowired
	RequestSignupRepository rqSignupRepo;

	@Autowired
	UserServiceImplement userSrvImp;

	@Autowired
	GenerateRandomString ramStrGen;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	SendEmail mail;

	@Override
	public String validateSignupRequest(ToolUser userData, String codeSignup) {
		String getCodeSignup = rqSignupRepo.getCodeSignup(userData.getUsername());
		if (codeSignup.equals(getCodeSignup)) {
			if (userSrvImp.checkUsername(userData.getUsername()) == 1) {
				return "Người dùng đã đăng ký tài khoản với Username này rồi !";
			} else {
				List<Long> listId = rqSignupRepo.getAllIdByUsername(userData.getUsername());
				String passEncode = encoder.encode(userData.getPassword());
				userSrvImp.insertUser(userData.getUsername(), passEncode, userData.getEmail());
				int userId = userSrvImp.getUserId(userData.getUsername());
				userSrvImp.insertDefaultRole(userId);
				for (int i = 0; i < listId.size(); i++) {
					rqSignupRepo.deleteById(listId.get(i));
				}
				mail.sendSuccessSignup(userData.getEmail(), userData.getUsername());
				return "Đăng ký tài khoản thành công";
			}
		} return "Mã xác thực không đúng hoặc hết hạn hãy đăng ký lại !";
	}

	@Override
	public String sendSignupRequest(ToolUser userData) {
		if (userSrvImp.checkUsername(userData.getUsername()) == 1) {
			return "Tên người dùng đã tồn tại !";
		} else if (userSrvImp.checkEmail(userData.getEmail()) == 1) {
			return "Email đã tồn tại !";
		}else {			
			String codeSignup = ramStrGen.generateRandomCode();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String currentdate = dtf.format(now);
			mail.sendCodeSignup(userData.getEmail(), codeSignup);
			rqSignupRepo.insertSignUpRequest(codeSignup, currentdate, userData.getUsername());
			return "Check mail !";
		}
	}
}
