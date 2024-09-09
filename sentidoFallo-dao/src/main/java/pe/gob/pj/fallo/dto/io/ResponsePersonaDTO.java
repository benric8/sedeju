package pe.gob.pj.fallo.dto.io;

import java.io.Serializable;

public class ResponsePersonaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombres;
	private String paterno;
	private String materno;
	
	public ResponsePersonaDTO() {
		super();
	}
	public String getPaterno() {
		return paterno;
	}
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}
	public String getMaterno() {
		return materno;
	}
	public void setMaterno(String materno) {
		this.materno = materno;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
}
