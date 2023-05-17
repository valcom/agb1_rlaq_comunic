package it.inps.entrate.rlaq.batch.stats;

import java.time.temporal.Temporal;


public abstract class StatisticheBean {
	/**
	 * 
	 */

	private Temporal dataInizio;
	private Temporal dataFine;
	
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Temporal getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Temporal dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Temporal getDataFine() {
		return dataFine;
	}

	public void setDataFine(Temporal dataFine) {
		this.dataFine = dataFine;
	}





}
