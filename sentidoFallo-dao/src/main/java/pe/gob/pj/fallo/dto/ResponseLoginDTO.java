package pe.gob.pj.fallo.dto;

import java.io.Serializable;

public class ResponseLoginDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String token;
	private String tokenAdmin;
	private String exededReload;
	private String exededReloadAdmin;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenAdmin() {
		return tokenAdmin;
	}

	public void setTokenAdmin(String tokenAdmin) {
		this.tokenAdmin = tokenAdmin;
	}

	public String isExededReload() {
		return exededReload;
	}

	public void setExededReload(String exededReload) {
		this.exededReload = exededReload;
	}

	public String getExededReloadAdmin() {
		return exededReloadAdmin;
	}

	public void setExededReloadAdmin(String exededReloadAdmin) {
		this.exededReloadAdmin = exededReloadAdmin;
	}
}