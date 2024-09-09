package pe.gob.pj.fallo.dto.io;

import java.io.Serializable;

public class ParametroResumenDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long nCarga;
	private String fecha;
	private String estado;
	private long nUsuario;

    public ParametroResumenDTO() {
		super();
	}

	public long getnCarga() {
		return nCarga;
	}

	public void setnCarga(long nCarga) {
		this.nCarga = nCarga;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public long getnUsuario() {
		return nUsuario;
	}

	public void setnUsuario(long nUsuario) {
		this.nUsuario = nUsuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}