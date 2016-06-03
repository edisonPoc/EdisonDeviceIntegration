package com.example.AzureIotDeviceIntegration.serviceImpl;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.AzureIotDeviceIntegration.entity.Counter;
import com.example.AzureIotDeviceIntegration.entity.DeviceDataCounter;
import com.example.AzureIotDeviceIntegration.handler.DeviceClientSingleton;
import com.microsoft.azure.iothub.IotHubMessageResult;
import com.microsoft.azure.iothub.Message;
import com.microsoft.azure.storage.StorageException;

public class MessageCallbackMqtt implements com.microsoft.azure.iothub.MessageCallback {
	String deviceID = "";
	DeviceService deviceService=null;
	@Override
	public IotHubMessageResult execute(Message msg, Object context) {
		Counter counter = (Counter) context;
		String recievedMessage = new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET);
		JSONParser parser = new JSONParser();
		String ledState = "";
		JSONObject object = null;
		JSONObject object1 = null;
		
		try {
		object = (JSONObject) parser.parse(recievedMessage);
		deviceService=new DeviceServiceImpl();
		String deviceType=deviceService.getDeviceType(this.deviceID);
			object1 = (JSONObject) object.get("Parameters");
			DeviceDataCounter deviceData = new DeviceDataCounter();
			System.out.println("adding data to list for index" + counter.get() + " for device ID" + deviceID);
			deviceData.setCounter(counter.get());
			deviceData.setDeviceId(deviceID);
			if(deviceType.equals("Gateway")&&(object1==null))
			{
				System.out.println(object.get("GladiusParameters"));
			//object1=(JSONObject) object.get("GladiusParameters");
			ledState=(String)object.get("GladiusParameters");
			deviceData.setStatus(ledState);	
			}
			else
			{
			ledState = String.valueOf(object1.get("LEDState"));
			deviceData.setStatus(ledState);
			}

			System.out.println("the command recieved asks for state " + ledState);
			System.out.println("Received message " + counter.toString() + " with content: " + recievedMessage);
			System.out.println("Responding to message " + counter.toString() + " with " + IotHubMessageResult.COMPLETE);
						
			DeviceClientSingleton.addDeviceData(deviceData);
			System.out.println("added data with value " + DeviceClientSingleton.getDeviceData(deviceID));
			counter.increment();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return IotHubMessageResult.COMPLETE;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
}