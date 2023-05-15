/**
 * 
 */
package it.inps.entrate.rlaq.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import it.inps.entrate.rlaq.batch.entity.Notifica;
import it.inps.entrate.rlaq.batch.entity.Provvedimento;

/**
 * @author vcompagnone01
 *
 */
public class PreparazioneNotificaProvvedimentoProcessor implements ItemProcessor<Provvedimento, Notifica>{

	@Override
	public Notifica process(Provvedimento item) throws Exception {
		return null;
		
	}

}
