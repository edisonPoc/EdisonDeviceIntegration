package com.example.AzureIotDeviceIntegration.entity;

import com.google.gson.Gson;

public class DeviceDataCounter {
		public int counter;
		public String deviceId;
		  public String status;
		public int getCounter() {
			return counter;
		}
		public void setCounter(int counter) {
			this.counter = counter;
		}
		public String getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status =status;
		}

		}

