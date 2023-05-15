/**
 * 
 */
package it.inps.entrate.rlaq.batch.entity;

/**
 * @author vcompagnone01
 *
 */
public class Provvedimento {
	
	private Long idProvvedimento;
	
	private Long idDomanda;
	
	private String cfAzienda;

	public Long getIdProvvedimento() {
		return idProvvedimento;
	}

	public void setIdProvvedimento(Long idProvvedimento) {
		this.idProvvedimento = idProvvedimento;
	}

	public Long getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}

	public String getCfAzienda() {
		return cfAzienda;
	}

	public void setCfAzienda(String cfAzienda) {
		this.cfAzienda = cfAzienda;
	}

	
	
}
