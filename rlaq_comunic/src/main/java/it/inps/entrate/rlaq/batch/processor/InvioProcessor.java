/**
 * 
 */
package it.inps.entrate.rlaq.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import it.inps.entrate.rlaq.batch.entity.Notifica;
import it.inps.entrate.rlaq.batch.ws.WSIconaClient;
import it.inps.soa.ws01317.InvioEmailExt;
import it.inps.soa.ws01317.InvioEmailExtResponse;

/**
 * @author vcompagnone01
 *
 */
public class InvioProcessor implements ItemProcessor<Notifica, Notifica> {

	@Autowired
	private WSIconaClient wsIconaClient;

	@Autowired
	private Converter<Notifica, InvioEmailExt> requestConverter;

	@Autowired
	private Converter<InvioEmailExtResponse, Notifica> responseConverter;

	@Override
	public Notifica process(Notifica notifica) throws Exception {

		InvioEmailExt request = requestConverter.convert(notifica);

		InvioEmailExtResponse response = wsIconaClient.invioPEC(request);

		return responseConverter.convert(response);
	}

}
