package pe.gob.pj.fallo.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReporteDetalladoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long nSentidoFallo;
	private long nExpediente;
	private String xFormato;
	private Integer anio;
	private String mes;
	private String dia;
	private String fSentidoFallo;
	private String fRegistro;
	private String xNomOrgJurisd;
	private String xNomDistrito;
	private String xDescSentido;
	private String xDescRecuro;
	private Integer nNumRecurso;
	private String xDescrEspecialidad;
	private String xDescProceso;
	private String fFechaVisto;	
	private String xNomMagistrado;

	public long getnExpediente() {
		return nExpediente;
	}

	public void setnExpediente(long nExpediente) {
		this.nExpediente = nExpediente;
	}

	public String getxFormato() {
		return xFormato;
	}

	public void setxFormato(String xFormato) {
		this.xFormato = xFormato;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getfSentidoFallo() {
		return fSentidoFallo;
	}

	public void setfSentidoFallo(String fSentidoFallo) {
		this.fSentidoFallo = fSentidoFallo;
	}

	public String getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(String fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getxNomOrgJurisd() {
		return xNomOrgJurisd;
	}

	public void setxNomOrgJurisd(String xNomOrgJurisd) {
		this.xNomOrgJurisd = xNomOrgJurisd;
	}

	public String getxNomDistrito() {
		return xNomDistrito;
	}

	public void setxNomDistrito(String xNomDistrito) {
		this.xNomDistrito = xNomDistrito;
	}

	public String getxDescSentido() {
		return xDescSentido;
	}

	public void setxDescSentido(String xDescSentido) {
		this.xDescSentido = xDescSentido;
	}

	public String getxDescRecuro() {
		return xDescRecuro;
	}

	public void setxDescRecuro(String xDescRecuro) {
		this.xDescRecuro = xDescRecuro;
	}

	public Integer getnNumRecurso() {
		return nNumRecurso;
	}

	public void setnNumRecurso(Integer nNumRecurso) {
		this.nNumRecurso = nNumRecurso;
	}

	public String getxDescrEspecialidad() {
		return xDescrEspecialidad;
	}

	public void setxDescrEspecialidad(String xDescrEspecialidad) {
		this.xDescrEspecialidad = xDescrEspecialidad;
	}

	public String getxDescProceso() {
		return xDescProceso;
	}

	public void setxDescProceso(String xDescProceso) {
		this.xDescProceso = xDescProceso;
	}

	public String getfFechaVisto() {
		return fFechaVisto;
	}

	public void setfFechaVisto(String fFechaVisto) {
		this.fFechaVisto = fFechaVisto;
	}

	public long getnSentidoFallo() {
		return nSentidoFallo;
	}

	public void setnSentidoFallo(long nSentidoFallo) {
		this.nSentidoFallo = nSentidoFallo;
	}

	public String getxNomMagistrado() {
		return xNomMagistrado;
	}

	public void setxNomMagistrado(String xNomMagistrado) {
		this.xNomMagistrado = xNomMagistrado;
	}

}
