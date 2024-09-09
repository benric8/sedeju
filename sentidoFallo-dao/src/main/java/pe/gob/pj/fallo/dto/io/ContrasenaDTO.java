package pe.gob.pj.fallo.dto.io;

import java.io.Serializable;

public class ContrasenaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long nUsuario;
	private String contrasenaActual;
	private String nuevaContrasena;
	private long nUsuarioAdmin;
	
	public ContrasenaDTO() {
		super();
	}
	public String getContrasenaActual() {
		return contrasenaActual;
	}
	public void setContrasenaActual(String contrasenaActual) {
		this.contrasenaActual = contrasenaActual;
	}
	public String getNuevaContrasena() {
		return nuevaContrasena;
	}
	public void setNuevaContrasena(String nuevaContrasena) {
		this.nuevaContrasena = nuevaContrasena;
	}
	public long getnUsuario() {
		return nUsuario;
	}
	public void setnUsuario(long nUsuario) {
		this.nUsuario = nUsuario;
	}
	public long getnUsuarioAdmin() {
		return nUsuarioAdmin;
	}
	public void setnUsuarioAdmin(long nUsuarioAdmin) {
		this.nUsuarioAdmin = nUsuarioAdmin;
	}
}
