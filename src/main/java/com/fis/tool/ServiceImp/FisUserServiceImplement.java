package com.fis.tool.ServiceImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.tool.Model.CheckModel;
import com.fis.tool.Model.FisUser;
import com.fis.tool.Model.UserModel;
import com.fis.tool.Repo.FisUserRepository;
import com.fis.tool.Service.FisUserService;
import com.fis.tool.Util.SetTimeout;

@Service
public class FisUserServiceImplement implements FisUserService {

	@Autowired
	FisUserRepository fisUserRepo;

	@Autowired
	SetTimeout setTimeout;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public void autoCheckService(String username, String password, String checkType) {
		String today = LocalDate.now().getDayOfWeek().toString();
		System.out.println(today);
		if (today.equals("SATURDAY") || today.equals("SUNDAY")) {
			System.out.println("Nghỉ!!!!!!!!!");
		} else {
			Random r = new Random();
			int random = r.nextInt(1200000 - 300000) + 300000;
			SetTimeout.setTimeout(() -> {
				UserModel userModel = new UserModel(username, password);
				String token = login(userModel);
				if (token != null) {
					System.out.println("_______________________________________________________");
					System.out.println(username + "đăng nhập thành công");
					SetTimeout.setTimeout(() -> {
						CheckModel checkModel = new CheckModel();
						String checkModelToJson;
						try {
							checkModelToJson = new ObjectMapper().writeValueAsString(checkModel);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						headers.set("Authorization", "Bearer " + token);
						@SuppressWarnings({ "rawtypes", "unchecked" })
						HttpEntity entity = new HttpEntity(checkModelToJson, headers);
						String resString = restTemplate
								.postForEntity("https://api.fis.vn:9999/fis/api/" + checkType, entity, String.class)
								.getBody();
						JSONObject resObj = new JSONObject(resString);
						System.out.println("User: " + username);
						System.out.println(resObj);
						System.out.println("_______________________________________________________");
					}, 5000);
				} else
					System.out.println(username + " đăng nhập không thành công");
			}, random);
		}
	}

	@Override
	public void autoCallCheckInService() {
		List<FisUser> listAutoUser = fisUserRepo.getAllAutoUser();
		List<String> listUsername = new ArrayList<>();
		List<String> listPassword = new ArrayList<>();
		for (FisUser s : listAutoUser) {
			listUsername.add(s.getUsername());
		}
		for (FisUser s : listAutoUser) {
			listPassword.add(s.getPassword());
		}
		for (int i = 0; i < listAutoUser.size(); i++) {
			autoCheckService(listUsername.get(i), listPassword.get(i), "checkin_all");
		}
	}

	@Override
	public String login(UserModel userModel) {
		String resString = restTemplate.postForEntity("https://api.fis.vn:9999/fis/api/login", userModel, String.class)
				.getBody();
		JSONObject resObj = new JSONObject(resString);
		String resToken = resObj.getJSONObject("data").getString("token");
		return resToken;
	}

	@Override
	public void autoCallCheckOutService() {
		List<FisUser> listAutoUser = fisUserRepo.getAllAutoUser();
		List<String> listUsername = new ArrayList<>();
		List<String> listPassword = new ArrayList<>();
		for (FisUser s : listAutoUser) {
			listUsername.add(s.getUsername());
		}
		for (FisUser s : listAutoUser) {
			listPassword.add(s.getPassword());
		}
		for (int i = 0; i < listAutoUser.size(); i++) {
			autoCheckService(listUsername.get(i), listPassword.get(i), "checkout_all");
		}
	}
}
