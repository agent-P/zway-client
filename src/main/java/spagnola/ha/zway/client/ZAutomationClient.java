/**
 * 
 */
package spagnola.ha.zway.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

/**
 * @author spagnola
 *
 */
@Component
public class ZAutomationClient {

	private final RestOperations restOperations;
	
	private final String zautomationServiceUrl;
	
	public ZAutomationClient(final RestTemplateBuilder builder, final ZwayClientProperties properties) {
		
		this.restOperations = builder.setReadTimeout(properties.getReadTimeout())
									.setConnectTimeout(properties.getConnectTimeout())
									.basicAuthorization(properties.getUsername(), String.valueOf(properties.getPassword()))
									.build();
		
		this.zautomationServiceUrl = properties.getZautomationServiceUrl();
		
	}
	
	public String getZWayVirtualDevices() {
		return restOperations.getForObject(zautomationServiceUrl, String.class, "devices");
	}

	public String commandZWayVirtualDevices(String commandPath) {
		String commandPathString = "devices/" + commandPath;
		return restOperations.getForObject(zautomationServiceUrl, String.class, commandPathString);
	}

	
}
