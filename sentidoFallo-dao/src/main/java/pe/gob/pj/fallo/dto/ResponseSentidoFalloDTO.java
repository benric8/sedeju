package pe.gob.pj.fallo.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseSentidoFalloDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private List<SentidoFalloDTO> data;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<SentidoFalloDTO> getData() {
		return data;
	}

	public void setData(List<SentidoFalloDTO> data) {
		this.data = data;
	}

}
