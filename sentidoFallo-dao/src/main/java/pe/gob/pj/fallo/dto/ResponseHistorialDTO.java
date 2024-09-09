package pe.gob.pj.fallo.dto;

import java.io.Serializable;
import java.util.List;

import pe.gob.pj.fallo.dto.io.ResumenSentidoFalloDTO;

public class ResponseHistorialDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private List<ResumenSentidoFalloDTO> data;

	public List<ResumenSentidoFalloDTO> getData() {
		return data;
	}

	public void setData(List<ResumenSentidoFalloDTO> data) {
		this.data = data;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
