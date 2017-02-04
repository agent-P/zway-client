/**
 * 
 */
package spagnola.ha.websocket.echo;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.json.JSONObject;
import spagnola.ha.zway.service.ZWayControllerService;

/**
 * @author spagnola
 *
 */
public class EchoWebSocketHandler extends TextWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(EchoWebSocketHandler.class);
	
	private static ArrayList<WebSocketSession> sessions = new ArrayList<>();
	
	private final ZWayControllerService zWayControllerService;

	public EchoWebSocketHandler(ZWayControllerService zWayControllerService) {
		this.zWayControllerService = zWayControllerService;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session);
		logger.info("Opened new session in instance " + this);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		logger.info(session.getId() + ": connection closed...");
		sessions.remove(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		JSONObject commandMessage;
		
		logger.info("Received message from: " + session.getId());
		
//		try {
			commandMessage = new JSONObject(message.getPayload());
			
			if(commandMessage.getString("type").equals("zway-command")) {
				logger.info("Received zway-command: " + commandMessage.getString("device-id") + " : " + commandMessage.getString("command"));
				
				this.zWayControllerService.commandZWayVirtualDevices(commandMessage.getString("device-id"), commandMessage.getString("command"));
			}
//		}
//		catch(Exception exception) {
//			//logger.warn("Message Error: " + exception.getMessage());
//			logger.warn(exception.getStackTrace().toString());
//		}
		
		broadcastMessage(message.getPayload());
		
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception)
			throws Exception {
		session.close(CloseStatus.SERVER_ERROR);
	}
	
	public void broadcastMessage(String message) throws Exception {
		for(WebSocketSession sessionInstance : sessions) {
			logger.info("Sending message to: " + sessionInstance.getId());
			sessionInstance.sendMessage(new TextMessage(message));
		}

	}


}
