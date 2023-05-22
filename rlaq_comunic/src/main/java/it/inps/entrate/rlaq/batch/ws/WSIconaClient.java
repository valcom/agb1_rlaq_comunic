package it.inps.entrate.rlaq.batch.ws;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import it.inps.soa.ws01317.GetRicevutePec;
import it.inps.soa.ws01317.GetRicevutePecResponse;
import it.inps.soa.ws01317.InvioEmailExt;
import it.inps.soa.ws01317.InvioEmailExtResponse;

public class WSIconaClient extends WebServiceGatewaySupport{
	
	private IdentityHeader identityHeader;
	
	public WSIconaClient(IdentityHeader identityHeader) {
		super();
		this.identityHeader = identityHeader;
	}

	public InvioEmailExtResponse invioPEC(InvioEmailExt request) {
		
		return (InvioEmailExtResponse) getWebServiceTemplate().marshalSendAndReceive(request,identityHeader);
		 	
	}

	public GetRicevutePecResponse getRicevutePEC(GetRicevutePec request) {
		return (GetRicevutePecResponse) getWebServiceTemplate().marshalSendAndReceive(request,identityHeader);
	}
	
}
