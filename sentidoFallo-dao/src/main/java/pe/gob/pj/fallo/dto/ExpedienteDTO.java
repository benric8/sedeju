package pe.gob.pj.fallo.dto;

import java.util.List;

public class ExpedienteDTO extends AuditoriaDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String numero;
	private String anio;
	private String incidente;
	private String distrito;
	private String instancia;
	private String codEspecialidad;
	private String organo;
	private String formato;
	private String descDistritoJudicial;
	private String descproceso;
	private String desEspecialidad;
	private List<SentidoDeFalloDTO> sentidoFallo ;
	private Integer resultados;
	private String codDistritoJudicial;
	private String codOrganoJuris;
	
	public ExpedienteDTO() {
		super();
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getIncidente() {
		return incidente;
	}
	public void setIncidente(String incidente) {
		this.incidente = incidente;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getInstancia() {
		return instancia;
	}
	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	
	public String getOrgano() {
		return organo;
	}
	public void setOrgano(String organo) {
		this.organo = organo;
	}
	public String getCodEspecialidad() {
		return codEspecialidad;
	}
	public void setCodEspecialidad(String codEspecialidad) {
		this.codEspecialidad = codEspecialidad;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getDesEspecialidad() {
		return desEspecialidad;
	}
	public void setDesEspecialidad(String desEspecialidad) {
		this.desEspecialidad = desEspecialidad;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescDistritoJudicial() {
		return descDistritoJudicial;
	}
	public void setDescDistritoJudicial(String descDistritoJudicial) {
		this.descDistritoJudicial = descDistritoJudicial;
	}
	public String getDescproceso() {
		return descproceso;
	}
	public void setDescproceso(String descproceso) {
		this.descproceso = descproceso;
	}
	public Integer getResultados() {
		return resultados;
	}
	public void setResultados(Integer resultados) {
		this.resultados = resultados;
	}
	public String getCodDistritoJudicial() {
		return codDistritoJudicial;
	}
	public void setCodDistritoJudicial(String codDistritoJudicial) {
		this.codDistritoJudicial = codDistritoJudicial;
	}
	public String getCodOrganoJuris() {
		return codOrganoJuris;
	}
	public void setCodOrganoJuris(String codOrganoJuris) {
		this.codOrganoJuris = codOrganoJuris;
	}
	public List<SentidoDeFalloDTO> getSentidoFallo() {
		return sentidoFallo;
	}
	public void setSentidoFallo(List<SentidoDeFalloDTO> sentidoFallo) {
		this.sentidoFallo = sentidoFallo;
	}

}
