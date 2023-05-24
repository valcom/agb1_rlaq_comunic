package it.inps.entrate.rlaq.batch.stats;

import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class StatisticheStepBean extends StatisticheBean {

	private static final String TEMPLATE_MSG = "STATISTICHE STEP %s:\t[TEMPO DI ESECUZIONE STEP: %s, TOTALE ELEMENTI LETTI : %s, TOTALE ELEMENTI SCRITTI : %s, TOTALE ELEMENTI IN ERRORE : %s]";

	private long totaleElementiLetti;
	private long totaleElementiScritti;
	private long totaleElementiInErrore;

	public long getTotaleElementiLetti() {
		return totaleElementiLetti;
	}

	public void setTotaleElementiLetti(long totaleElementiLetti) {
		this.totaleElementiLetti = totaleElementiLetti;
	}

	public long getTotaleElementiScritti() {
		return totaleElementiScritti;
	}

	public void setTotaleElementiScritti(long totaleElementiScritti) {
		this.totaleElementiScritti = totaleElementiScritti;
	}

	public long getTotaleElementiInErrore() {
		return totaleElementiInErrore;
	}

	public void setTotaleElementiInErrore(long totaleElementiInErrore) {
		this.totaleElementiInErrore = totaleElementiInErrore;
	}

	@Override
	public String toString() {

		long duration = getDataInizio().until(getDataFine(), ChronoUnit.MILLIS);

		String timeExec = DurationFormatUtils.formatDuration(duration, "HH:mm:ss.SSS");
		StringBuilder sb = new StringBuilder(String.format(TEMPLATE_MSG, getName(), timeExec, getTotaleElementiLetti(),
				getTotaleElementiScritti(), getTotaleElementiInErrore()));

		return sb.toString();
	}
}
