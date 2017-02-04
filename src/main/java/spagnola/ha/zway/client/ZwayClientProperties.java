package spagnola.ha.zway.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("zway.client")
public class ZwayClientProperties {
	private int serverPort;
	private int efServerPort;
	private String efServerHost;
	private int readTimeout;
	private int connectTimeout;
	private String username;
	private char[] password;
	private String zwayServiceUrl;
	private String zautomationServiceUrl;
	
	/**
	 * @return the readTimeout
	 */
	public int getReadTimeout() {
		return readTimeout;
	}
	/**
	 * @param readTimeout the readTimeout to set
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}
	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public char[] getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}
	/**
	 * @return the zwayServiceUrl
	 */
	public String getZwayServiceUrl() {
		return zwayServiceUrl;
	}
	/**
	 * @param zwayServiceUrl the zwayServiceUrl to set
	 */
	public void setZwayServiceUrl(String zwayServiceUrl) {
		this.zwayServiceUrl = zwayServiceUrl;
	}
	/**
	 * @return the zautomationServiceUrl
	 */
	public String getZautomationServiceUrl() {
		return zautomationServiceUrl;
	}
	/**
	 * @param zwayServiceUrl the zwayServiceUrl to set
	 */
	public void setZautomationServiceUrl(String zautomationServiceUrl) {
		this.zautomationServiceUrl = zautomationServiceUrl;
	}
	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}
	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	/**
	 * @return the efServerPort
	 */
	public int getEfServerPort() {
		return efServerPort;
	}
	/**
	 * @param efServerPort the efServerPort to set
	 */
	public void setEfServerPort(int efServerPort) {
		this.efServerPort = efServerPort;
	}
	/**
	 * @return the efServerHost
	 */
	public String getEfServerHost() {
		return efServerHost;
	}
	/**
	 * @param efServerHost the efServerHost to set
	 */
	public void setEfServerHost(String efServerHost) {
		this.efServerHost = efServerHost;
	}
	
	
}
