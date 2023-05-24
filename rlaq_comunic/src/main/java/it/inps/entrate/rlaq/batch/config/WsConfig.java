package it.inps.entrate.rlaq.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import it.inps.Identity;
import it.inps.entrate.rlaq.batch.ws.CompositeMsgCallback;
import it.inps.entrate.rlaq.batch.ws.HeaderAdder;
import it.inps.entrate.rlaq.batch.ws.WSIconaClient;

@Configuration
public class WsConfig {

	@Bean
	public WSIconaClient wsiconaClient(@Value("${ws.icona.url}") String url) {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("it.inps.soa.ws01317");

		WSIconaClient client = new WSIconaClient();
		client.setDefaultUri(url);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		client.setInvioCallback(invioCallback());
		client.setGetRicevutePecCallback(getRicevutePecCallback());
		return client;

	}

	@Bean
	public HeaderAdder<Identity> identityHeader() {
		return new HeaderAdder<>(identity());
	}

	@Bean
	@ConfigurationProperties(prefix = "ws.identity")
	public Identity identity() {
		return new it.inps.ObjectFactory().createIdentity();
	}

	@Bean
	public WebServiceMessageCallback invioCallback() {

		return new CompositeMsgCallback(new SoapActionCallback("http://soa.inps.it/WS01317/IWSIcona20/InvioEmailExt"),
				identityHeader());

	}

	@Bean
	public WebServiceMessageCallback getRicevutePecCallback() {

		return new CompositeMsgCallback(new SoapActionCallback("http://soa.inps.it/WS01317/IWSIcona20/GetRicevutePec"),
				identityHeader());

	}
}
