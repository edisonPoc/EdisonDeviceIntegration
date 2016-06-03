package com.example.AzureIotDeviceIntegration.serviceImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.microsoft.azure.iot.service.sdk.Device;
import com.microsoft.azure.storage.StorageException;

public interface DeviceService {

	void sendDeviceData(String data, String deviceId, boolean isGatewayChild, boolean alarmFlag)
			throws URISyntaxException, IOException, InterruptedException, StorageException, InvalidKeyException,
			ParseException;

	String getDeviceType(String deviceId) throws InvalidKeyException, URISyntaxException, StorageException;

	List<Object> getAllDevices() throws Exception;

	List<Device> getNonChildDevices() throws Exception;

	List<String> getCommandStatus();

}