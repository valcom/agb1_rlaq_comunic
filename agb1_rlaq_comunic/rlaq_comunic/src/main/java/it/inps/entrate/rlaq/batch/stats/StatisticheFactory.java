package it.inps.entrate.rlaq.batch.stats;

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
		statistiche.setDataInizio(jobExecution.getStartTime());
		statistiche.setDataFine(jobExecution.getEndTime());
		statistiche.setTemplateMsg(TEMPLATE_JOB_MSG);

		return statistiche;
	}

	public StatisticheBean getStatisticheStep(StepExecution stepExecution) {
		StatisticheBean statistiche = new StatisticheBean();

		statistiche.setName(stepExecution.getStepName());
		statistiche.setDataInizio(stepExecution.getStartTime());
		statistiche.setDataFine(stepExecution.getEndTime());
		statistiche.setTotaleElementiLetti(stepExecution.getReadCount());
		statistiche.setTotaleElementiScritti(stepExecution.getWriteCount());
		statistiche.setTotaleElementiInErrore(stepExecution.getSkipCount());
		statistiche.setTemplateMsg(TEMPLATE_STEP_MSG);

		return statistiche;
	}

}
