package pe.gob.pj.fallo.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SentidoFalloDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long nExpediente;
	private String xFormato;
	private String xNomDistrito; // Distrito Sentido de Fallo
	private String xDescDistrito; // Distrito Expediente
	private String xDescrEspecialidad;
	@Id
	private long nSentidoFallo;
	private String xDescSentido;
	private Timestamp fSentidoFallo;
	private Timestamp fRegistro;
	private String xNomOrgJurisd;
	private long nSentidoFalloMagistrado;
	private long nMagistradoSentido;
	private String xNomMagistrado;
	private String xDescProceso;

	private String xDescRecurso;
	private Integer nNumRecurso;
	private Integer nAnioRecurso;
	private Timestamp fFechaVisto;

	private String cDistrito;

	public SentidoFalloDTO() {
		super();
	}

	public SentidoFalloDTO(long nExpediente, String xFormato, String xNomDistrito, String xDescDistrito,
			String xDescrEspecialidad, long nSentidoFallo, String xDescSentido, Timestamp fSentidoFallo,
			Timestamp fRegistro, String xNomOrgJurisd, long nSentidoFalloMagistrado, long nMagistradoSentido,
			String xNomMagistrado, String xDescProceso) {
		super();
		this.nExpediente = nExpediente;
		this.xFormato = xFormato;
		this.xNomDistrito = xNomDistrito;
		this.xDescDistrito = xDescDistrito;
		this.xDescrEspecialidad = xDescrEspecialidad;
		this.nSentidoFallo = nSentidoFallo;
		this.xDescSentido = xDescSentido;
		this.fSentidoFallo = fSentidoFallo;
		this.fRegistro = fRegistro;
		this.xNomOrgJurisd = xNomOrgJurisd;
		this.nSentidoFalloMagistrado = nSentidoFalloMagistrado;
		this.nMagistradoSentido = nMagistradoSentido;
		this.xNomMagistrado = xNomMagistrado;
		this.xDescProceso = xDescProceso;
	}

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

	public String getxNomDistrito() {
		return xNomDistrito;
	}

	public void setxNomDistrito(String xNomDistrito) {
		this.xNomDistrito = xNomDistrito;
	}

	public String getxDescDistrito() {
		return xDescDistrito;
	}

	public void setxDescDistrito(String xDescDistrito) {
		this.xDescDistrito = xDescDistrito;
	}

	public String getxDescrEspecialidad() {
		return xDescrEspecialidad;
	}

	public void setxDescrEspecialidad(String xDescrEspecialidad) {
		this.xDescrEspecialidad = xDescrEspecialidad;
	}

	public long getnSentidoFallo() {
		return nSentidoFallo;
	}

	public void setnSentidoFallo(long nSentidoFallo) {
		this.nSentidoFallo = nSentidoFallo;
	}

	public String getxDescSentido() {
		return xDescSentido;
	}

	public void setxDescSentido(String xDescSentido) {
		this.xDescSentido = xDescSentido;
	}

	public Timestamp getfSentidoFallo() {
		return fSentidoFallo;
	}

	public void setfSentidoFallo(Timestamp fSentidoFallo) {
		this.fSentidoFallo = fSentidoFallo;
	}

	public Timestamp getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Timestamp fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getxNomOrgJurisd() {
		return xNomOrgJurisd;
	}

	public void setxNomOrgJurisd(String xNomOrgJurisd) {
		this.xNomOrgJurisd = xNomOrgJurisd;
	}

	public long getnSentidoFalloMagistrado() {
		return nSentidoFalloMagistrado;
	}

	public void setnSentidoFalloMagistrado(long nSentidoFalloMagistrado) {
		this.nSentidoFalloMagistrado = nSentidoFalloMagistrado;
	}

	public long getnMagistradoSentido() {
		return nMagistradoSentido;
	}

	public void setnMagistradoSentido(long nMagistradoSentido) {
		this.nMagistradoSentido = nMagistradoSentido;
	}

	public String getxNomMagistrado() {
		return xNomMagistrado;
	}

	public void setxNomMagistrado(String xNomMagistrado) {
		this.xNomMagistrado = xNomMagistrado;
	}

	public String getxDescProceso() {
		return xDescProceso;
	}

	public void setxDescProceso(String xDescProceso) {
		this.xDescProceso = xDescProceso;
	}

	public String getxDescRecurso() {
		return xDescRecurso;
	}

	public void setxDescRecurso(String xDescRecurso) {
		this.xDescRecurso = xDescRecurso;
	}

	public Integer getnNumRecurso() {
		return nNumRecurso;
	}

	public void setnNumRecurso(Integer nNumRecurso) {
		this.nNumRecurso = nNumRecurso;
	}

	public Integer getnAnioRecurso() {
		return nAnioRecurso;
	}

	public void setnAnioRecurso(Integer nAnioRecurso) {
		this.nAnioRecurso = nAnioRecurso;
	}

	public Timestamp getfFechaVisto() {
		return fFechaVisto;
	}

	public void setfFechaVisto(Timestamp fFechaVisto) {
		this.fFechaVisto = fFechaVisto;
	}

	public String getcDistrito() {
		return cDistrito;
	}

	public void setcDistrito(String cDistrito) {
		this.cDistrito = cDistrito.trim();
	}
}