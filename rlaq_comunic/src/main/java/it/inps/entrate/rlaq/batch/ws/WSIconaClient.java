package it.inps.entrate.rlaq.batch.ws;

import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import it.inps.soa.ws01317.GetRicevutePec;
import it.inps.soa.ws01317.GetRicevutePecResponse;
import it.inps.soa.ws01317.InvioEmailExt;
import it.inps.soa.ws01317.InvioEmailExtResponse;

public class WSIconaClient extends WebServiceGatewaySupport {

	private WebServiceMessageCallback invioCallback;

	private WebServiceMessageCallback getRicevutePecCallback;

	public WebServiceMessageCallback getInvioCallback() {
		return invioCallback;
	}

	public void setInvioCallback(WebServiceMessageCallback invioCallback) {
		this.invioCallback = invioCallback;
	}

	public WebServiceMessageCallback getGetRicevutePecCallback() {
		return getRicevutePecCallback;
	}

	public void setGetRicevutePecCallback(WebServiceMessageCallback getRicevutePecCallback) {
		this.getRicevutePecCallback = getRicevutePecCallback;
	}

	public InvioEmailExtResponse invioPEC(InvioEmailExt request) {

		return (InvioEmailExtResponse) getWebServiceTemplate().marshalSendAndReceive(request, invioCallback);

	}

	public GetRicevutePecResponse getRicevutePEC(GetRicevutePec request) {
		return (GetRicevutePecResponse) getWebServiceTemplate().marshalSendAndReceive(request, getRicevutePecCallback);
	}

}
