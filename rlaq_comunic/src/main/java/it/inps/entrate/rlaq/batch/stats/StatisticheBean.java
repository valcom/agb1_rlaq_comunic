package it.inps.entrate.rlaq.batch.stats;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.time.DurationFormatUtils;


public class StatisticheBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8313159445444229215L;

	private Date dataInizio;
	private Date dataFine;
	private long totaleElementiLetti;
	private long totaleElementiScritti;
	private long totaleElementiInErrore;
	private String name;
	private String templateMsg;
	
	private Collection<StatisticheBean> statisticheStep;

	public Collection<StatisticheBean> getStatisticheStep() {
		return statisticheStep;
	}

	public void setStatisticheStep(Collection<StatisticheBean> statisticheStep) {
		this.statisticheStep = statisticheStep;
	}

	public void setTemplateMsg(String template) {
		this.templateMsg = template;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	@Override
	public String toString() {
		long dataInizioMillis = dataInizio.getTime();
		long dataFineMillis = dataFine != null ? dataFine.getTime() : System.currentTimeMillis();
		String timeExec = DurationFormatUtils.formatDuration(dataFineMillis - dataInizioMillis, "HH:mm:ss");
		StringBuilder sb = new StringBuilder(String.format(templateMsg, name, timeExec, getTotaleElementiLetti(), getTotaleElementiScritti(), getTotaleElementiInErrore()));
		if(statisticheStep!= null) {
			statisticheStep.forEach(stat->sb.append("\n").append(stat.toString()));
		}
		return sb.toString();
	}

	public String getTemplateMsg() {
		return templateMsg;
	}

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

}
