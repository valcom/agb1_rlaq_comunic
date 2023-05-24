package it.inps.entrate.rlaq.batch.stats;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class StatisticheJobBean extends StatisticheBean {

	private static final String TEMPLATE_MSG = "STATISTICHE JOB %s:\t[TEMPO DI ESECUZIONE JOB: %s]";;

	private Collection<StatisticheStepBean> statisticheStep;

	public Collection<StatisticheStepBean> getStatisticheStep() {
		return statisticheStep;
	}

	public void setStatisticheStep(Collection<StatisticheStepBean> statisticheStep) {
		this.statisticheStep = statisticheStep;
	}

	@Override
	public String toString() {
		long duration = getDataInizio().until(getDataFine(), ChronoUnit.MILLIS);

		String timeExec = DurationFormatUtils.formatDuration(duration, "HH:mm:ss.SSS");
		StringBuilder sb = new StringBuilder(String.format(TEMPLATE_MSG, getName(), timeExec));
		if (statisticheStep != null) {
			statisticheStep.forEach(stat -> sb.append("\n").append(stat.toString()));
		}
		return sb.toString();
	}
}
