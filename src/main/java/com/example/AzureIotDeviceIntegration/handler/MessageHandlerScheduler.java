package com.example.AzureIotDeviceIntegration.handler;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.AzureIotDeviceIntegration.entity.Counter;
import com.example.AzureIotDeviceIntegration.serviceImpl.MessageCallback;
import com.example.AzureIotDeviceIntegration.serviceImpl.MessageCallbackMqtt;
import com.microsoft.azure.iot.service.sdk.Device;
import com.microsoft.azure.iothub.DeviceClient;
import com.microsoft.azure.iothub.IotHubClientProtocol;
@Component
public class MessageHandlerScheduler {
	@Scheduled(fixedDelay = 5000)
	public void demoServiceMethod() throws IOException, InterruptedException {
		System.out.println("Starting...");
		System.out.println("Beginning setup.");
		String[] args = new String[2];
		args[1] = "mqtt";

		if (args.length <= 0 || 3 <= args.length) {
			System.out.format(
					"Expected 1 or 2 arguments but received:\n%d. " + "The program should be called with the: "
							+ "following args: " + "[IoT Hub connection string] (https | amqps | mqtt | amqps_ws).\n",
					args.length);
			return;
		}

		// String connString = args[0];
		List<Device> devices=DeviceClientSingleton.getDeviceList();
		for (int a = 0; a < devices.size(); a++) {
			IotHubClientProtocol protocol;
			if (args.length == 1) {
				protocol = IotHubClientProtocol.HTTPS;
			} else {
				String protocolStr = args[1];
				if (protocolStr.equals("https")) {
					protocol = IotHubClientProtocol.HTTPS;
				} else if (protocolStr.equals("amqps")) {
					protocol = IotHubClientProtocol.AMQPS;
				} else if (protocolStr.equals("mqtt")) {
					protocol = IotHubClientProtocol.MQTT;
				} else {
					System.out.format(
							"Expected argument 1 to be one of 'https', 'amqps', 'mqtt' or 'amqps_ws'"
									+ "but received %s. The program should be " + "called with the: following args: "
									+ "[IoT Hub connection string] (https | amqps | mqtt | amqps_ws)." + "\n",
							protocolStr);
					return;
				}
			}

			System.out.println("Successfully read input parameters for client " + a);
			System.out.format("Using communication protocol %s.\n", protocol.name());

			DeviceClient client = DeviceClientSingleton.getInstance(devices.get(a).getDeviceId());
			System.out.println("Successfully created an IoT Hub client " + a + ".");

			if (protocol == IotHubClientProtocol.MQTT) {
				MessageCallbackMqtt callback = new MessageCallbackMqtt();
				callback.setDeviceID(devices.get(a).getDeviceId());
				Counter counter = new Counter(0);
				client.setMessageCallback(callback, counter);
			} else {
				MessageCallback callback = new MessageCallback();
				callback.setDeviceID(devices.get(a).getDeviceId());
				Counter counter = new Counter(0);
				client.setMessageCallback(callback, counter);
			}
			System.out.println("Successfully set message callback.");
			// client.open();
			DeviceClientSingleton.openClient(devices.get(a).getDeviceId(),a);

			System.out.println("Opened connection to IoT Hub.");

			System.out.println("Beginning to receive messages for Client " + a + "...");
		}
		System.out.println("Press any key to exit...");
		
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		for (int a = 0; a < devices.size(); a++) {
		DeviceClientSingleton.closeClient(devices.get(a).getDeviceId(),a);
		}
		System.out.println("Shutting down...");
	}
}
