package it.inps.entrate.rlaq.batch.stats;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class StatisticheFactory {

	public StatisticheJobBean getStatisticheJob(JobExecution jobExecution) {

		StatisticheJobBean statistiche = new StatisticheJobBean();
		statistiche.setName(jobExecution.getJobInstance().getJobName());
		statistiche.setDataInizio(jobExecution.getStartTime());
		statistiche.setDataFine(jobExecution.getEndTime());
		statistiche.setStatisticheStep(jobExecution.getStepExecutions().stream().map(this::getStatisticheStep).toList());

		return statistiche;
	}

	public StatisticheStepBean getStatisticheStep(StepExecution stepExecution) {
		StatisticheStepBean statistiche = new StatisticheStepBean();

		statistiche.setName(stepExecution.getStepName());
		statistiche.setDataInizio(stepExecution.getStartTime());
		statistiche.setDataFine(stepExecution.getEndTime());
		statistiche.setTotaleElementiLetti(stepExecution.getReadCount());
		statistiche.setTotaleElementiScritti(stepExecution.getWriteCount());
		statistiche.setTotaleElementiInErrore(stepExecution.getSkipCount());

		return statistiche;
	}

}
