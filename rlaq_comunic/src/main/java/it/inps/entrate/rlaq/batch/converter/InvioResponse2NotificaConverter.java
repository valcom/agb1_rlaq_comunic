package it.inps.entrate.rlaq.batch.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import it.inps.entrate.rlaq.batch.entity.Notifica;
import it.inps.soa.ws01317.InvioEmailExtResponse;

@Component
public class InvioResponse2NotificaConverter implements Converter<InvioEmailExtResponse, Notifica> {

	@Override
	public Notifica convert(InvioEmailExtResponse source) {
		// TODO Auto-generated method stub
		return null;
	}

}
