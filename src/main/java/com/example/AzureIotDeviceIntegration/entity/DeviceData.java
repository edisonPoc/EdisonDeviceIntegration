package com.example.AzureIotDeviceIntegration.entity;

import com.google.gson.Gson;

public class DeviceData {
	public String deviceId;
	public String n;
	public String s;
	public String so;
	public String v;
	public String deviceData;

	public String serialize() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}