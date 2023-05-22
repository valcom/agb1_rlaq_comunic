package it.inps.entrate.rlaq.batch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import it.inps.Identity;
import it.inps.entrate.rlaq.batch.ws.IdentityHeader;
import it.inps.entrate.rlaq.batch.ws.WSIconaClient;

@Configuration
public class WsConfig {

	
	@Bean
	public WSIconaClient wsiconaClient(@Value("${ws.icona.url}")String url, @Autowired IdentityHeader identityHeader) {
		Jaxb2Marshaller marshaller =  new Jaxb2Marshaller();
	        marshaller.setContextPath("it.inps.soa.ws01317");
	        
	       
		WSIconaClient client = new WSIconaClient(identityHeader);
	        client.setDefaultUri(url);
	        client.setMarshaller(marshaller);
			client.setUnmarshaller(marshaller);
	        return client;
		
	}
	
	@Bean
	public IdentityHeader identityHeader() {
		return new IdentityHeader(identity());
	}
	
	@Bean
	@ConfigurationProperties(prefix = "ws.identity")
	public Identity identity() {
		return new it.inps.ObjectFactory().createIdentity();
	}
}
