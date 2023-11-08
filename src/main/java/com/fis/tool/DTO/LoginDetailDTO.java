package com.fis.tool.DTO;

import lombok.Data;

@Data
public class LoginDetailDTO {
	private String username;
	private String password;
	private String loginDevice = "3356e246-5cb4-4585-a575-b17593858e2a";
	private String buildNumber = "15";
	private String version = "1.69.10885";
	private String deviceIP = "10.15.180.57";
	private String deviceModel = "IPhone XSMAX";
	private String osVersion = "15";


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getLoginDevice() {
		return loginDevice;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public String getVersion() {
		return version;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public LoginDetailDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

}
