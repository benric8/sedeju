package pe.gob.pj.fallo.dto.io;

import java.io.Serializable;

public class ParametroUsuarioDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
    private String nombreCompleto;
    private String docIdentidad;
    private String activo;
    private String perfil;

    public ParametroUsuarioDTO() {
		super();
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getDocIdentidad() {
		return docIdentidad;
	}
	public void setDocIdentidad(String docIdentidad) {
		this.docIdentidad = docIdentidad;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}