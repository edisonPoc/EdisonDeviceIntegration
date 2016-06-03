package com.example.AzureIotDeviceIntegration.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.AzureIotDeviceIntegration.serviceImpl.DeviceService;
import com.example.AzureIotDeviceIntegration.serviceImpl.DeviceServiceImpl;
import com.google.gson.Gson;
import com.microsoft.azure.iothub.DeviceClient;
import com.microsoft.azure.storage.StorageException;
@Controller
public class DeviceController {
	@Autowired
	DeviceService deviceService=null;
	
	@RequestMapping("/")
    public String index() {
        return "index";
    }
	
	@RequestMapping(value = "/sendDeviceData", method = RequestMethod.POST)
	public ModelAndView sendDeviceData(@RequestParam("deviceData") String data,@RequestParam("deviceId") String deviceId,@RequestParam("isGatewayChild") boolean isGatewayChild,@RequestParam("alarmFlag")boolean alarmFlag) throws URISyntaxException, IOException, InterruptedException, InvalidKeyException, StorageException, ParseException {
		ModelAndView model = new ModelAndView();
		System.out.println("Sending data to Azure IOT Hub");
		deviceService.sendDeviceData(data,deviceId,isGatewayChild,alarmFlag);
		model.addObject("controldata","");
		model.setViewName("LightBulb");
		return model;
	}
	@RequestMapping(value = "/getDeviceList", method = RequestMethod.GET)
	public ModelAndView getDeviceList() throws Exception{
		ModelAndView model = new ModelAndView();
		System.out.println("Getting list of devices running on the Azure IOT Hub");
		List<Object> deviceList=deviceService.getAllDevices();
		HashMap<String,String> devices=(HashMap<String, String>) deviceList.get(0);
		HashMap<String,String> gladiusChildDevices=(HashMap<String,String>) deviceList.get(1);
		model.addObject("devices",devices);
		model.addObject("gladiusChildDevices",gladiusChildDevices);
		model.setViewName("LightBulb");
		return model;
	}
	@ResponseBody
	@RequestMapping(value = "/getCommand", method = RequestMethod.GET)
	public String getCommand() throws URISyntaxException, IOException, InterruptedException {
		System.out.println("Getting command from Azure IOT Hub");
		List<String> data=deviceService.getCommandStatus();
		System.out.println(data);
		String json = new Gson().toJson(data);
		return json;
	}
}
