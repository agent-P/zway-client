/**
 * 
 */
package spagnola.ha.websocket.echo;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author spagnola
 *
 */
public class EchoWebSocketHandler extends TextWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(EchoWebSocketHandler.class);
	
	private static ArrayList<WebSocketSession> sessions = new ArrayList<>();

	private final EchoService echoService;

	public EchoWebSocketHandler(EchoService echoService) {
		this.echoService = echoService;
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
		String echoMessage = this.echoService.getMessage(message.getPayload());
		logger.info(session.getId() + ": " + echoMessage);
		
		for(WebSocketSession sessionInstance : sessions) {
			logger.info("Sending message to: " + sessionInstance.getId());
			sessionInstance.sendMessage(new TextMessage(echoMessage));
		}
		
		//session.sendMessage(new TextMessage(echoMessage));
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
