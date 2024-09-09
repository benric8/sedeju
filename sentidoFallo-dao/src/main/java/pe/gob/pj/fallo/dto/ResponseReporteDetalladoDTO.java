package pe.gob.pj.fallo.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseReporteDetalladoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descripcion;

	private List<ReporteDetalladoDTO> data;

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

	public List<ReporteDetalladoDTO> getData() {
		return data;
	}

	public void setData(List<ReporteDetalladoDTO> data) {
		this.data = data;
	}

}
