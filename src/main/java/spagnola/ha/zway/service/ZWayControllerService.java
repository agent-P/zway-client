/**
 * 
 */
package spagnola.ha.zway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import spagnola.ha.websocket.echo.EchoWebSocketHandler;
import spagnola.ha.zway.client.ZAutomationClient;
import spagnola.ha.zway.client.ZWayClient;

/**
 * @author spagnola
 *
 */
@Component
public class ZWayControllerService {
	private static Logger logger = LoggerFactory.getLogger(ZWayControllerService.class);
	
	@Autowired
	private ZWayClient client;
	@Autowired
	private ZAutomationClient zautomationClient;
	
	public String getZWayController() {
		logger.info("Get Controller Response from ZWay server...");
		return client.getZWayController();
	}

	public String getZWayDevices() {
		logger.info("Get Devices Response from ZWay server...");
		return client.getZWayDevices();
	}

	public String getZWayDevice(String deviceNumber) {
		logger.info("Get Devices Response for device: " + deviceNumber + " from ZWay server...");
		return client.getZWayDevice(deviceNumber);
	}

	public String getZWayVirtualDevices() {
		logger.info("Get Virtual Devices Response from ZWay server...");
		return zautomationClient.getZWayVirtualDevices();
	}
	
	public String commandZWayVirtualDevices(String deviceId, String command) {
		logger.info("Command Virtual Device: " + deviceId + " to: "+ command);
		
		String commandPath = deviceId + "/command/" + command;
		return zautomationClient.commandZWayVirtualDevices(commandPath);
	}
	
	public void updateZWayVirtualDevice(String deviceId, String deviceInfo) {
		logger.info("Device: " + deviceId + " -- message: " + deviceInfo);
        try {
        	this.echoWebSocketHandler().broadcastMessage(deviceId + ": " + deviceInfo);
        }
        catch(Exception error) {
        	logger.info(error.getMessage());
        }

	}

    @Bean
    EchoWebSocketHandler echoWebSocketHandler() {
		return new EchoWebSocketHandler(null);
    	
    }

}
