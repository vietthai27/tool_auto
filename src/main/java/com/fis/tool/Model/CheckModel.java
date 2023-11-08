package com.fis.tool.Model;

public class CheckModel {
	
	String deviceId = "42940546-D218-4ABF-958B-FB87AE0FC10A";
	String reason = "";
	String ssid = "FIS-user";
	String ipGateway = "10.15.180.1";
	int type = 0;
	
	public CheckModel() {
		super();
	}

	public CheckModel(String deviceId, String reason, String ssid, String ipGateway, int type) {
		super();
		this.deviceId = deviceId;
		this.reason = reason;
		this.ssid = ssid;
		this.ipGateway = ipGateway;
		this.type = type;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getIpGateway() {
		return ipGateway;
	}

	public void setIpGateway(String ipGateway) {
		this.ipGateway = ipGateway;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
	
	
	
}
