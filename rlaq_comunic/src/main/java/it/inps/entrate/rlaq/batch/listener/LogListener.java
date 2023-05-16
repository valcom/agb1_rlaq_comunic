package it.inps.entrate.rlaq.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.inps.entrate.rlaq.batch.stats.StatisticheFactory;


@Component
public class LogListener implements JobExecutionListener, StepExecutionListener {
	private static final Logger logger = LoggerFactory.getLogger(LogListener.class);
	
	@Value("${version}")
	private String version;

	@Autowired
	private StatisticheFactory statisticheFactory;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		if(logger.isInfoEnabled()) {
			logger.info("------------ START BATCH {}  VERSIONE: {} ------------", jobExecution.getJobInstance().getJobName(),version);
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(logger.isInfoEnabled()) {
			logger.info("---------- END BATCH {} VERSIONE: {} STATUS: {} ----------",
				 jobExecution.getJobInstance().getJobName(), version, jobExecution.getExitStatus().getExitCode());
			logger.info("---------- STATISTICHE\n{}",statisticheFactory.getStatisticheJob(jobExecution));
			logger.info("---------- FINE STATISTICHE");
		}
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		if(logger.isInfoEnabled()) {
			logger.info("---------- START STEP {} ----------", stepExecution.getStepName());
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if(logger.isInfoEnabled()) {
			logger.info("---------- END STEP {} STATUS: {} ----------", stepExecution.getStepName(), stepExecution.getExitStatus().getExitCode());
		}
		return stepExecution.getExitStatus();
	}
}
