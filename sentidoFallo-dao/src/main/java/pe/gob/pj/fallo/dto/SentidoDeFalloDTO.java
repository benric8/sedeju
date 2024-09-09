package pe.gob.pj.fallo.dto;

import java.util.List;

public class SentidoDeFalloDTO extends AuditoriaDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String descripcion;
	private ExpedienteDTO expediente;
	private List<MagistradoDTO> listaMagistrados;
	private String fechaDecision;
	private String fechaPublicacion;
	private String juzgado;
	private Long idExpediente;
	private String magistrados;
	private Long idUsuario;
	private String codDistritoJudicial;
	private String organo;
	private Long nCarga;
	
	public SentidoDeFalloDTO() {
		super();
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ExpedienteDTO getExpediente() {
		return expediente;
	}
	public void setExpediente(ExpedienteDTO expediente) {
		this.expediente = expediente;
	}
	public List<MagistradoDTO> getListaMagistrados() {
		return listaMagistrados;
	}
	public void setListaMagistrados(List<MagistradoDTO> listaMagistrados) {
		this.listaMagistrados = listaMagistrados;
	}

	public String getFechaDecision() {
		return fechaDecision;
	}
	public void setFechaDecision(String fechaDecision) {
		this.fechaDecision = fechaDecision;
	}
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getJuzgado() {
		return juzgado;
	}
	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMagistrados() {
		return magistrados;
	}
	public void setMagistrados(String magistrados) {
		this.magistrados = magistrados;
	}
	public String getCodDistritoJudicial() {
		return codDistritoJudicial;
	}
	public void setCodDistritoJudicial(String codDistritoJudicial) {
		this.codDistritoJudicial = codDistritoJudicial;
	}
	public String getOrgano() {
		return organo;
	}
	public void setOrgano(String organo) {
		this.organo = organo;
	}
	public Long getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getnCarga() {
		return nCarga;
	}
	public void setnCarga(Long nCarga) {
		this.nCarga = nCarga;
	}
}
