/**
 * 
 */
package it.inps.entrate.rlaq.batch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import it.inps.entrate.rlaq.batch.item.StepDecision;

/**
 * @author vcompagnone01
 *
 */
public class StepExecutionDecider implements JobExecutionDecider {
	
	private String property;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		boolean decision = true;
		// TODO Auto-generated method stub
		return decision ? new FlowExecutionStatus(StepDecision.ENABLED.name()):new FlowExecutionStatus(StepDecision.DISABLED.name());

	}

}
