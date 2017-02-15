package spagnola.ha.zway;

import spagnola.ha.websocket.CmdTlmWebSocketHandler;
import spagnola.ha.zway.client.ZwayClientProperties;
import spagnola.ha.zway.service.ZWayControllerService;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NetworkTrafficServerConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.client.RestTemplate;

@EnableWebSocket
@SpringBootApplication
public class ZwayClientApplication extends SpringBootServletInitializer implements WebSocketConfigurer {
	
	@Autowired
	private ZwayClientProperties zwayClientProperties;

	public static void main(String[] args) {
				
		SpringApplication.run(ZwayClientApplication.class, args);
	}

	@Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() throws Exception {
        final String absoluteKeystoreFile = ResourceUtils.getFile(zwayClientProperties.getKeystoreFile()).getAbsolutePath();
        
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer factory) {
                Assert.state(factory instanceof JettyEmbeddedServletContainerFactory, "Use Jetty for this server");
                JettyEmbeddedServletContainerFactory jettyFactory = (JettyEmbeddedServletContainerFactory) factory;
                jettyFactory.addServerCustomizers(new JettyServerCustomizer() {

                    @Override
                    public void customize(Server server) {
                        SslContextFactory sslContextFactory = new SslContextFactory();
                        sslContextFactory.setKeyStorePath(absoluteKeystoreFile);
                        sslContextFactory.setKeyStorePassword(zwayClientProperties.getKeystorePass());
                        sslContextFactory.setKeyStoreType("PKCS12");

                        ServerConnector sslConnector = new ServerConnector(	server, sslContextFactory);
                        sslConnector.setPort(zwayClientProperties.getServerPort());
                        server.setConnectors(new Connector[] { sslConnector });
                        
                        final NetworkTrafficServerConnector connector = new NetworkTrafficServerConnector(server);
                        connector.setHost(zwayClientProperties.getEfServerHost());
                        connector.setPort(Integer.valueOf(zwayClientProperties.getEfServerPort()));
                        server.addConnector(connector);
                        
                    }
                });
            }
        };
    }

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(cmdTlmWebSocketHandler(), "/zway-socket").withSockJS();
	}
	
	@Bean
	public ZWayControllerService zWayControllerService() {
		return new ZWayControllerService();
		
	}
	
	@Bean
	public CmdTlmWebSocketHandler cmdTlmWebSocketHandler() {
		return new CmdTlmWebSocketHandler();
	}

	@Bean 
	public RestTemplate restTemplate(RestTemplateBuilder builder) { 
		return builder.setReadTimeout(zwayClientProperties.getReadTimeout())
				.setConnectTimeout(zwayClientProperties.getConnectTimeout())
				.basicAuthorization(zwayClientProperties.getUsername(), String.valueOf(zwayClientProperties.getPassword()))
				.build(); 
		
	}
}
