package pe.gob.pj.fallo.dto;

import java.io.Serializable;
import java.util.List;

import pe.gob.pj.fallo.dto.io.ComboDTO;

public class ResponseComboDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private List<ComboDTO> data;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<ComboDTO> getData() {
		return data;
	}

	public void setData(List<ComboDTO> data) {
		this.data = data;
	}

}
