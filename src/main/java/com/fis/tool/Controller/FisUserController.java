package com.fis.tool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fis.tool.ServiceImp.FisUserServiceImplement;

@RestController
@CrossOrigin("*")
@RequestMapping("/fis-user")
@EnableScheduling
public class FisUserController {

	@Autowired
	FisUserServiceImplement fisUserSrvImp;

	@Scheduled(cron = "0 8 9 ? * *")
	@GetMapping("/autoCheckIn")
	public void autoCheckIn() {
		fisUserSrvImp.autoCallCheckInService();
	}
	
	@Scheduled(cron = "0 40 10 ? * *")
	@GetMapping("/autoCheckOut")
	public void autoCheckOut() {
		fisUserSrvImp.autoCallCheckOutService();
	}

}
