package pe.gob.pj.fallo.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseListaUsuariosDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private List<UsuarioDTO> data;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<UsuarioDTO> getData() {
		return data;
	}

	public void setData(List<UsuarioDTO> data) {
		this.data = data;
	}

}
