package pe.gob.pj.fallo.dto;

import java.util.Date;

public class PerfilDTO extends AuditoriaDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nPerfil;
    private String xDescripcion;
    private Date fRegistro;
    private String lActivo;
    public PerfilDTO() {
		super();
	}
	public Long getnPerfil() {
		return nPerfil;
	}
	public void setnPerfil(Long nPerfil) {
		this.nPerfil = nPerfil;
	}
	public String getxDescripcion() {
		return xDescripcion;
	}
	public void setxDescripcion(String xDescripcion) {
		this.xDescripcion = xDescripcion;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public String getlActivo() {
		return lActivo;
	}
	public void setlActivo(String lActivo) {
		this.lActivo = lActivo;
	}
    
}