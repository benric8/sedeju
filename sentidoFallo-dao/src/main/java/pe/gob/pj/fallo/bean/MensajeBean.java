package pe.gob.pj.fallo.bean;

import java.io.Serializable;

/**
 * @autor: rventocillam [11-2016]
 * @descripción: {Bean para enviar mensajes}
 */
public class MensajeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcion;
	public MensajeBean() {
		super();
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
