package it.inps.entrate.rlaq.batch.stats;

import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class StatisticheFactory {

	private static final String TEMPLATE_JOB_MSG = "STATISTICHE JOB %s:\t[TEMPO DI ESECUZIONE JOB: %s]";
	private static final String TEMPLATE_STEP_MSG = "STATISTICHE STEP %s:\t[TEMPO DI ESECUZIONE STEP: %s, TOTALE ELEMENTI LETTI : %s, TOTALE ELEMENTI SCRITTI : %s, TOTALE ELEMENTI IN ERRORE : %s]";

	public StatisticheBean getStatisticheJob(JobExecution jobExecution) {

		StatisticheBean statistiche = new StatisticheBean();
		statistiche.setName(jobExecution.getJobInstance().getJobName());
		statistiche.setDataInizio(Date.from(jobExecution.getStartTime().toInstant(ZoneOffset.UTC)));
		statistiche.setDataFine(Date.from(jobExecution.getEndTime().toInstant(ZoneOffset.UTC)));
		statistiche.setTemplateMsg(TEMPLATE_JOB_MSG);
		statistiche.setStatisticheStep(jobExecution.getStepExecutions().stream().map(this::getStatisticheStep).toList());

		return statistiche;
	}

	public StatisticheBean getStatisticheStep(StepExecution stepExecution) {
		StatisticheBean statistiche = new StatisticheBean();

		statistiche.setName(stepExecution.getStepName());
		statistiche.setDataInizio(Date.from(stepExecution.getStartTime().toInstant(ZoneOffset.UTC)));
		statistiche.setDataFine(Date.from(stepExecution.getEndTime().toInstant(ZoneOffset.UTC)));
		statistiche.setTotaleElementiLetti(stepExecution.getReadCount());
		statistiche.setTotaleElementiScritti(stepExecution.getWriteCount());
		statistiche.setTotaleElementiInErrore(stepExecution.getSkipCount());
		statistiche.setTemplateMsg(TEMPLATE_STEP_MSG);

		return statistiche;
	}

}
