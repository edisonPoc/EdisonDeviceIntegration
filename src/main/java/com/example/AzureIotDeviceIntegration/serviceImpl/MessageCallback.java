package com.example.AzureIotDeviceIntegration.serviceImpl;
import com.example.AzureIotDeviceIntegration.entity.Counter;
import com.example.AzureIotDeviceIntegration.entity.DeviceDataCounter;
import com.example.AzureIotDeviceIntegration.handler.DeviceClientSingleton;
import com.microsoft.azure.iothub.IotHubMessageResult;
import com.microsoft.azure.iothub.Message;

public class MessageCallback implements com.microsoft.azure.iothub.MessageCallback
    {
    	Message recievedMesg=null;
    	String deviceID="";
		int extraCounter=0;
    	@Override
        public IotHubMessageResult execute(Message msg, Object context)
        {
        	recievedMesg=new Message();
        	recievedMesg=msg;
        	
            Counter counter = (Counter) context;
            System.out.println(
                    "Received message " + counter.toString()
                            + " with content: " + new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET));
            int switchVal = counter.get() % 3;
            IotHubMessageResult res;
            switch (switchVal)
            {
                case 0:
                    res = IotHubMessageResult.COMPLETE;
                    break;
                case 1:
                    res = IotHubMessageResult.ABANDON;
                    break;
                case 2:
                    res = IotHubMessageResult.REJECT;
                    break;
                default:
                    // should never happen.
                    throw new IllegalStateException("Invalid message result specified.");
            }
            if(counter.get() % 3 !=2)
            {
            System.out.println("Responding to message " + counter.toString() + " with " + res.name());
            DeviceDataCounter deviceData=new DeviceDataCounter();
            deviceData.setCounter((counter.get()-extraCounter));
            deviceData.setDeviceId(deviceID);
            deviceData.setStatus(new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET));
            DeviceClientSingleton.addDeviceData(deviceData);
               }
            else
            {
            	++extraCounter;
            }
            counter.increment();
            return res;
        }
    	public String getDeviceID() {
			return deviceID;
		}
		public void setDeviceID(String deviceID) {
			this.deviceID = deviceID;
		}
    }