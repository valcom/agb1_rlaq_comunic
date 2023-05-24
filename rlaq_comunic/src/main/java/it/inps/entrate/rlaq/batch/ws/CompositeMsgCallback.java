package it.inps.entrate.rlaq.batch.ws;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;

public class CompositeMsgCallback implements WebServiceMessageCallback {

	private WebServiceMessageCallback[] delegates;

	public CompositeMsgCallback(WebServiceMessageCallback... delegates) {
		super();
		this.delegates = delegates;
	}

	@Override
	public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
		if (delegates != null) {
			for (WebServiceMessageCallback d : delegates) {
				d.doWithMessage(message);
			}
		}

	}

}
