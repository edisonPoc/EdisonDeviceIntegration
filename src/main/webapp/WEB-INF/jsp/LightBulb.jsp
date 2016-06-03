<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Light Bulb POC</title>
</head>
<body>
	<script>
		/* $(document).ready(function()
		 {
		 deviceCounter=0;
		 deviceCounter=${devices};
		 } */
		flag = 0;
	</script>
	<div align="center">
		<h1>Simulated device integration with Azure IOT Hub</h1>
		<script type="text/javascript" src="js/jquery-1.12.3.js"></script>
		<div>
			<c:forEach items="${devices}" var="device" varStatus="loop">
				<div id="container${device}">
				<h3 id="header${loop.index}">
					<b id="device${loop.index}"></b> <img style="margin-left: 0%;"
						class="${loop.index}" id="${device}"
						src="images/pic_bulboff.gif"
						width="100" height="180">
				</h3>
				</div>
				<br />
			</c:forEach>
		</div>
		<br>
		<p>
			<b>Click the light bulb to turn on/off the light.</b>
		</p>
		<input id="deviceList" type="hidden" value="${devices}" /> <br>
		<input id="gladiusChildList" type="hidden" value="${gladiusChildDevices}" /> <br>
		<br>
		<!-- <p>The status of light bulb is <input type="text" id="flagValue" value="0"></p>
 -->
	</div>
	<script>
		$(document)
				.ready(
						function() {
							console.log("Document Ready!");
							deviceListElements = document
									.getElementById("deviceList");
							deviceListElements = deviceListElements.value;
							console.log(deviceListElements);
							deviceListElements = deviceListElements.substring(
									1, deviceListElements.length - 1);
							deviceListElements = deviceListElements.split(", ");
							console.log(deviceListElements);
							deviceList = [];
							for (var i = 0; i < deviceListElements.length; i++) {
								var deviceListElementBreak = deviceListElements[i]
										.split("=");
								console.log(deviceListElementBreak[0] + " "
										+ deviceListElementBreak[1]);
								deviceList[i] = deviceListElementBreak[0];
								deviceName = document.getElementById("device"
										+ i);
								deviceName.innerHTML = deviceListElementBreak[0];
								headerItem = document.getElementById("header"
										+ i);
								console.log(headerItem);
								if (deviceListElementBreak[1] === 'Simulated') {
									headerItem.style.background = "#74fcf5";
								} else if (deviceListElementBreak[1] === 'Rasberry') {
									headerItem.style.background = "#d5ff80";
								}
								 else if (deviceListElementBreak[1] === 'Gateway') {
										headerItem.style.background = "#ff6666";
									}
								$
										.ajax({
											type : 'GET',
											url : 'http://lediotpoc.cloudapp.net/IotDeviceSwitchIntegrationApplication/getDeviceStatus',
											data : {
												deviceID : deviceListElementBreak[0]
											},
											async : false,
											headers : {
												"Access-Control-Expose-Headers" : "Content-Disposition",
												"Access-Control-Allow-Credentials" : "true"
											},
											success : function(data) {
												if($.trim(data))
												{
												console
														.log("Device Status Recieved");
												console.log(data);
												dataParsed=JSON.parse(data);
												deviceStatus = dataParsed.status;
												if ((deviceStatus != "")
														&& (deviceStatus != " ")) {
													var inputType = document
															.getElementById(deviceListElements[i]);
													if (deviceStatus === '1') {
														inputType.src = "images/pic_bulbon.gif";
														
														if (deviceListElementBreak[1] === 'Gateway') {
															gladiusDevices=document.getElementById("gladiusChildList");
															console.log(gladiusDevices.value);
															gladiusValues=gladiusDevices.value;
															gladiusValues=gladiusValues.substring(1,gladiusValues.length-1);
															gladiusValues=gladiusValues.split(", ");
															containerValue=document.getElementById("container"+deviceListElements[i]);
															newContainer=document.createElement("div");
															newContainer.id="completeDiv"+deviceListElementBreak[0];
															containerValue.appendChild(newContainer);
															console.log(containerValue);
															newContainer=document.getElementById("completeDiv"+deviceListElementBreak[0]);
															counterGladiusDev=0;
															for(var k=0;k<gladiusValues.length;k++)
																{
																gladiusArrayBreak=gladiusValues[k].split("=");
																if(gladiusArrayBreak[1]==deviceListElementBreak[0])
																{
																var label = document.createElement("label");
																label.id="label"+k;
																label.innerHTML=gladiusArrayBreak[0];
																console.log("value of label is"+label);
																newContainer.appendChild(label);
												        
																var input = document.createElement("input");
												                input.type = "text";
												                input.name = gladiusValues[k];
												                
												                input.id="gladiusChild"+gladiusArrayBreak[0];
												                console.log("value of input is"+input);
												                newContainer.appendChild(input);
												                // Append a line break 
												                newContainer.appendChild(document.createElement("br"));
												                ++counterGladiusDev;
																}
																}
															if(counterGladiusDev>0)
																{
															newContainer.appendChild(document.createElement("br"));
															
															var newButton = document.createElement("button");
															
															
															newButton.id="gladiusChildButton";
															newButton.setAttribute('onClick','sendGladiusTelemetry("'+gladiusArrayBreak[1]+'")');
															newButton.innerHTML="Send Telemetry";
											                console.log("value of button is"+newButton);
											                newContainer.appendChild(newButton);
																}
															
														}
													} else if (deviceStatus === '0') {
														inputType.src = "images/pic_bulboff.gif";
														
													}
													else
														{
														if (deviceListElementBreak[1] === 'Gateway') {
															gladiusChildStatus=JSON.parse(deviceStatus);
															console.log(gladiusChildStatus);
															for (key in gladiusChildStatus) {
															    if (gladiusChildStatus.hasOwnProperty(key)) {
															        console.log(key + " = " + gladiusChildStatus[key]);
															        gladiusChildDev=document.getElementById("gladiusChild"+key);
																	if(gladiusChildDev!=null)
																		{
																		gladiusChildDev.value=gladiusChildStatus[key];
																		}
															    }
															}
													
															
														}
														}
												}
												}
											},
											error : function(error) {
												console
														.log("Could not send the command");
											}
										});
							}
						});
		deviceStatus = 0;
		var myVar = setInterval(myTimer, 5000);

		function myTimer() {

			$.ajax({
				type : 'GET',
				url : 'getCommand',
				data : {},
				success : function(response) {
					console.log(response);
					commands = JSON.parse(response);
					console.log(commands.length);
					for (var i = 0; i < commands.length; i++) {
						console.log(commands[i]);
						if ((commands[i] !== " ") && (commands[i] !== "")) {
							console.log("Inside");
							var deviceValue = document
									.getElementsByClassName(i);
							console.log(deviceValue[0].id);
							changeImageFromButton(commands[i],
									deviceValue[0].id);
						}
					}
					console.log("Command successfully recieved");
				},
				error : function(error) {
					console.log("Could not recieve the command");
				}
			});
		}
		function sendGladiusTelemetry()
		{
			console.log("Gateway Value is"+arguments[0]);
			console.log(gladiusValues);
			telemetryData='{';
			for(var k=0;k<gladiusValues.length;k++)
				{
				gladiusArrayBreak=gladiusValues[k].split("=");
				if(gladiusArrayBreak[1]==arguments[0])
					{
				telemetryData=telemetryData+'"'+gladiusArrayBreak[0]+'":"';
				gladiusInput=document.getElementById("gladiusChild"+gladiusArrayBreak[0]);
				console.log(gladiusInput.value);
				if((gladiusInput.value>50)||(gladiusInput.value<10))
						{
					sendAlarmData(gladiusInput.value,gladiusArrayBreak[0],arguments[0]);
						}
				telemetryData=telemetryData+gladiusInput.value+'",';
					}
				}
			console.log(telemetryData);
			telemetryData=telemetryData.substring(0,telemetryData.length-1);
			telemetryData=telemetryData+'}';
			console.log(telemetryData);
			deviceId="gladius1";
			console.log("Making ajax request to send gateway Child device values");
			$.ajax({
				type : 'POST',
				url : 'sendDeviceData',
				data : {
					deviceData : telemetryData,
					deviceId : deviceId,
					isGatewayChild: true,
					alarmFlag:false
				},
				success : function(data) {
					console.log("Data successfully sent");
				},
				error : function(error) {
					console.log("Could not make the api call");
				},
				async : false
			});
		}
		function sendAlarmData()
		{
			deviceStatus=arguments[0];
			deviceId=arguments[1];
			combinedString='{"deviceId":"'+deviceId+'","data":"'+deviceStatus+'"}';
			
			gladiusParentId=arguments[2];
			$.ajax({
				type : 'POST',
				url : 'sendDeviceData',
				data : {
					deviceData : combinedString,
					deviceId : gladiusParentId,
					isGatewayChild: true,
					alarmFlag:true
				},
				success : function(data) {
					console.log("Data successfully sent");
				},
				error : function(error) {
					console.log("Could not make the api call");
				},
				async : false
			});
		}
		function changeImageFromButton() {
			deviceStatus = arguments[0];
			deviceComplete = arguments[1];
			var deviceDataBreak = deviceComplete.split("=");
			deviceId = deviceDataBreak[0];
	
			var imageObject = document.getElementById(deviceComplete);
			if ((deviceStatus == 0) || (deviceStatus === 0)) {
				imageObject.src = "images/pic_bulboff.gif";
				//document.getElementById("flagValue").value="0";
				deviceStatus = 0;
				if (deviceDataBreak[1] === 'Gateway') {
					gladiusDevices=document.getElementById("gladiusChildList");
					console.log(gladiusDevices.value);
					gladiusValues=gladiusDevices.value;
					gladiusValues=gladiusValues.substring(1,gladiusValues.length-1);
					gladiusValues=gladiusValues.split(", ");
					containerValue=document.getElementById("container"+deviceComplete);
					console.log(containerValue);
						newContainer=document.getElementById("completeDiv"+deviceId);
						if(newContainer!== undefined)
						{
							containerValue.removeChild(newContainer);
						}

				}
				console.log("Making ajax request to send device values");
				sendDeviceTelemetry(deviceStatus,deviceId);
			} else if ((deviceStatus == 1) || (deviceStatus === 1)) {

				imageObject.src = "images/pic_bulbon.gif";
				//document.getElementById("flagValue").value="1";  
				deviceStatus = 1;
				if (deviceDataBreak[1] === 'Gateway') {
					gladiusDevices=document.getElementById("gladiusChildList");
					console.log(gladiusDevices.value);
					gladiusValues=gladiusDevices.value;
					gladiusValues=gladiusValues.substring(1,gladiusValues.length-1);
					gladiusValues=gladiusValues.split(", ");
					containerValue=document.getElementById("container"+deviceComplete);
					newContainer=document.createElement("div");
					newContainer.id="completeDiv"+deviceDataBreak[0];
					containerValue.appendChild(newContainer);
					console.log(containerValue);
					newContainer=document.getElementById("completeDiv"+deviceDataBreak[0]);
					counterGladiusDev=0;
					for(var k=0;k<gladiusValues.length;k++)
						{
						gladiusArrayBreak=gladiusValues[k].split("=");
						if(gladiusArrayBreak[1]==deviceDataBreak[0])
						{
						var label = document.createElement("label");
						label.id="label"+k;
						label.innerHTML=gladiusArrayBreak[0];
						console.log("value of label is"+label);
						newContainer.appendChild(label);
		        
						var input = document.createElement("input");
		                input.type = "text";
		                input.name = gladiusValues[k];
		                
		                input.id="gladiusChild"+gladiusArrayBreak[0];
		                console.log("value of input is"+input);
		                newContainer.appendChild(input);
		                // Append a line break 
		                newContainer.appendChild(document.createElement("br"));
		                ++counterGladiusDev;
						}
						}
					if(counterGladiusDev>0)
						{
					newContainer.appendChild(document.createElement("br"));
					
					var newButton = document.createElement("button");
					
					
					newButton.id="gladiusChildButton";
					newButton.setAttribute('onClick','sendGladiusTelemetry("'+gladiusArrayBreak[1]+'")');
					newButton.innerHTML="Send Telemetry";
	                console.log("value of button is"+newButton);
	                newContainer.appendChild(newButton);
						}
					
				}
				console.log("Making ajax request to send device values");
				sendDeviceTelemetry(deviceStatus,deviceId);
			}
			else
			{
			if (deviceDataBreak[1] === 'Gateway') {
				gladiusChildStatus=JSON.parse(deviceStatus);
				console.log(gladiusChildStatus);
				for (key in gladiusChildStatus) {
				    if (gladiusChildStatus.hasOwnProperty(key)) {
				        console.log(key + " = " + gladiusChildStatus[key]);
				        gladiusChildDev=document.getElementById("gladiusChild"+key);
						if(gladiusChildDev!=null)
							{
							gladiusChildDev.value=gladiusChildStatus[key];
							}
				    }
				}
			
				
			}
			}
		
		}
		
		function sendDeviceTelemetry()
		{
			deviceStatus=arguments[0];
			deviceId=arguments[1];
			$.ajax({
				type : 'POST',
				url : 'sendDeviceData',
				data : {
					deviceData : deviceStatus,
					deviceId : deviceId,
					isGatewayChild : false,
					alarmFlag:false
				},
				success : function(data) {
					console.log("Data successfully sent");
				},
				error : function(error) {
					console.log("Could not make the api call");
				},
				async : false
			});
		}
			</script>

</body>
</html>