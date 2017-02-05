/**
 * 
 */
package spagnola.ha.zway.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author spagnola
 *
 */
@Component
public class ZAutomationClient {

	@Autowired RestTemplate restTemplate;

	private final String zautomationServiceUrl;
	
	
	public ZAutomationClient(final ZwayClientProperties properties) {				
		this.zautomationServiceUrl = properties.getZautomationServiceUrl();
	}
	
	public String getZWayVirtualDevices() {
		return restTemplate.getForObject(zautomationServiceUrl, String.class, "devices");
	}

	public String commandZWayVirtualDevices(String commandPath) {
		String commandPathString = "devices/" + commandPath;
		return restTemplate.getForObject(zautomationServiceUrl, String.class, commandPathString);
	}

	
}
