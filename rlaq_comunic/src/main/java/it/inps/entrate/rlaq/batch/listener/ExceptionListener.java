/**
 * 
 */
package it.inps.entrate.rlaq.batch.listener;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author vcompagnone
 *
 */
@Component
public class ExceptionListener<I, O> extends StepListenerSupport<I, O> {

	private static final Logger log = LoggerFactory.getLogger(ExceptionListener.class);

	@Override
	public void onSkipInRead(Throwable t) {
		log.error("ERRORE in fase di lettura elementi");
		log.error(t.getMessage(), t);
	}

	@Override
	public void onSkipInProcess(I item, Throwable t) {
		if (log.isErrorEnabled()) {
			log.error("ERRORE in fase di elaborazione dell' elemento: {}", ToStringBuilder.reflectionToString(item));
			log.error(t.getMessage(), t);
		}
	}

	@Override
	public void onSkipInWrite(O item, Throwable t) {
		if (log.isErrorEnabled()) {
			log.error("ERRORE in fase di scrittura dell' elemento: {}", ToStringBuilder.reflectionToString(item));
			log.error(t.getMessage(), t);
		}
	}

}
