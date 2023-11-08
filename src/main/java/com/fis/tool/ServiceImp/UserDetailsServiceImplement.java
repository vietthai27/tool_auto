package com.fis.tool.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fis.tool.DTO.UserDetail;
import com.fis.tool.Model.ToolUser;
import com.fis.tool.Repo.UserRepository;



@Service
public class UserDetailsServiceImplement implements UserDetailsService {

	@Autowired
	PasswordEncoder passencode;

	@Autowired
	UserRepository userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ToolUser userInfo = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Tài khoản " + username + " không tồn tại trong hệ thống"));
		return UserDetail.build(userInfo);
	}

}
