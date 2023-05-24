package it.inps.entrate.rlaq.batch.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import it.inps.entrate.rlaq.batch.entity.Notifica;
import it.inps.soa.ws01317.InvioEmailExt;
import it.inps.soa.ws01317.data.ArrayOfDestinatarioInvio;
import it.inps.soa.ws01317.data.DestinatarioInvio;
import it.inps.soa.ws01317.data.EnTipoDestinatario;
import it.inps.soa.ws01317.data.InvioEmailExtRequest;

@Component
public class Notifica2InvioRequestConverter implements Converter<Notifica, InvioEmailExt> {

	@Override
	public InvioEmailExt convert(Notifica source) {

		InvioEmailExt request = new it.inps.soa.ws01317.ObjectFactory().createInvioEmailExt();
		InvioEmailExtRequest req = new it.inps.soa.ws01317.data.ObjectFactory().createInvioEmailExtRequest();
		req.setBody("PIPPO");
		req.setPEC(true);
		req.setMittente("pippo@pec.it");
		req.setSubject("Oggetto Test");

		ArrayOfDestinatarioInvio dest = new it.inps.soa.ws01317.data.ObjectFactory().createArrayOfDestinatarioInvio();

		DestinatarioInvio d = new it.inps.soa.ws01317.data.ObjectFactory().createDestinatarioInvio();
		d.setIndirizzoDestinatario("pluto@pec.it");
		d.setTipo(EnTipoDestinatario.TO);
		dest.getDestinatarioInvio().add(d);
		req.setListaDestinatari(dest);

		request.setRequest(req);
		return request;
	}

}
