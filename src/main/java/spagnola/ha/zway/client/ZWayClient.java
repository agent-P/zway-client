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
public class ZWayClient {
	
	@Autowired RestTemplate restTemplate;
	
	private final String zwayServiceUrl;
	
	
	public ZWayClient(final ZwayClientProperties properties) {
		this.zwayServiceUrl = properties.getZwayServiceUrl();
	}
	
	public String getZWayController() {
		return restTemplate.getForObject(zwayServiceUrl, String.class, "controller");
	}
	
	public String getZWayDevices() {
		return restTemplate.getForObject(zwayServiceUrl, String.class, "devices");
	}

	public String getZWayDevice(String deviceId) {
		
		String deviceString = "devices[" + deviceId + "]";
		return restTemplate.getForObject(zwayServiceUrl, String.class, deviceString);
	}
	
}
