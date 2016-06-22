package com.dmcc.dmcc_crm.bean;

import java.io.Serializable;

public class AuthContext implements Serializable {

	public static final long serialVersionUID = 1L;
	public String username="";// 用户名
	public String token="";// 安全字符
	public String tenantId="";//
	public String channel="";//
	public String deviceType="";// 设备类型 1：ios 2： android
	public String deviceId="";// 设备id uuid

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "AuthContext [username=" + username + ", token=" + token
				+ ", tenantId=" + tenantId + ", channel=" + channel
				+ ", deviceType=" + deviceType + ", deviceId=" + deviceId + "]";
	}

}
