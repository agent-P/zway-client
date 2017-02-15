/**
 * 
 */
package spagnola.ha.websocket;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CmdTlmWebSocketHandler extends TextWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(CmdTlmWebSocketHandler.class);
	
	private static ArrayList<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	
	@Autowired
	private ZWayControllerService zWayControllerService;

	public CmdTlmWebSocketHandler() {
		
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
		logger.info("message size: " + message.getPayload().length());
		logger.info("Received JSON: " + message.getPayload());
		
		try {
			logger.warn("trying to create JSONObject...");
			commandMessage = new JSONObject(message.getPayload());
			logger.warn("trying to send a command message: " + commandMessage.getString("command"));
			
			if(commandMessage.getString("type").equals("zway-command")) {
				logger.info("Received zway-command: " + commandMessage.getString("device-id") + " : " + commandMessage.getString("command"));
				
				this.zWayControllerService.commandZWayVirtualDevices(commandMessage.getString("device-id"), commandMessage.getString("command"));
			}
			else {
				logger.warn("didn't get a command message...");
			}
		}
		catch(Exception exception) {
			logger.warn("Message Error: " + exception.getMessage());
			logger.warn(exception.getStackTrace().toString());
		}
		
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
