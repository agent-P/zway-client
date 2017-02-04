/**
 * 
 */
package spagnola.ha.websocket.client;

/**
 * @author spagnola
 *
 */
public class SimpleGreetingService implements GreetingService {

	/* (non-Javadoc)
	 * @see spagnola.ha.websocket.client.GreetingService#getGreeting()
	 */
	@Override
	public String getGreeting() {
		
		return "Welcome to Z Way Command and Telemetry Test.";
	}

}
