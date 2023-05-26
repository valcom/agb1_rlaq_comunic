/**
 * 
 */
package it.inps.entrate.rlaq.batch.decider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import it.inps.entrate.rlaq.batch.repository.ConfigRepository;

/**
 * @author vcompagnone01
 *
 */
public class StepExecutionDecider implements JobExecutionDecider {

	private static final Logger log = LoggerFactory.getLogger(StepExecutionDecider.class);

	@Autowired
	private ConfigRepository configRepository;

	public enum StepDecision {
		ENABLED, DISABLED
	}

	private String property;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		String valore = configRepository.findValoreByChiave(property);
		if (log.isDebugEnabled()) {
			log.debug("{} = {}", property, valore);
		}
		StepDecision decision = Boolean.parseBoolean(valore) ? StepDecision.ENABLED : StepDecision.DISABLED;
		return new FlowExecutionStatus(decision.name());

	}

}
