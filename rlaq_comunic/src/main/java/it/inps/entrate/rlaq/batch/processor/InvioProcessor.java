/**
 * 
 */
package it.inps.entrate.rlaq.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import it.inps.entrate.rlaq.batch.entity.Notifica;

/**
 * @author vcompagnone01
 *
 */
public class InvioProcessor implements ItemProcessor<Notifica, Notifica> {

	@Override
	public Notifica process(Notifica item) throws Exception {
		throw new RuntimeException();
	}

}
