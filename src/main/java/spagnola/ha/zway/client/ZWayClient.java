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
public class ZWayClient {
	
	private final RestOperations restOperations;
	
	private final String zwayServiceUrl;
	
	public ZWayClient(final RestTemplateBuilder builder, final ZwayClientProperties properties) {
		
		this.restOperations = builder.setReadTimeout(properties.getReadTimeout())
									.setConnectTimeout(properties.getConnectTimeout())
									.basicAuthorization(properties.getUsername(), String.valueOf(properties.getPassword()))
									.build();
		
		this.zwayServiceUrl = properties.getZwayServiceUrl();
		
	}
	
	public String getZWayController() {
		return restOperations.getForObject(zwayServiceUrl, String.class, "controller");
	}
	
	public String getZWayDevices() {
		return restOperations.getForObject(zwayServiceUrl, String.class, "devices");
	}

	public String getZWayDevice(String deviceId) {
		
		String deviceString = "devices[" + deviceId + "]";
		return restOperations.getForObject(zwayServiceUrl, String.class, deviceString);
	}

}
