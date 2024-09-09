package pe.gob.pj.fallo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @autor: rventocillam [11-2016]
 * @descripción: {Bean para enviar respuesta}
 */
public class RespuestaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcion;
	private List<Object> data;
	private MensajeBean error;
	
	public RespuestaBean() {
		super();
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	public MensajeBean getError() {
		return error;
	}
	public void setError(MensajeBean error) {
		this.error = error;
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
