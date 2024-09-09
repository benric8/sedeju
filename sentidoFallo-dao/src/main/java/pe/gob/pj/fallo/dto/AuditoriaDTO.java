package pe.gob.pj.fallo.dto;

import java.io.Serializable;
import java.util.Date;

public class AuditoriaDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fechaOperacion;
	private String tipoOperacion;
	private String usuarioOperacion;
	private String usuarioRedOperacion;
	private String pcOperacion;
	private String ipOperacion;
	private String macOperacion;
	public Date getFechaOperacion() {
		return fechaOperacion;
	}
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	public String getUsuarioOperacion() {
		return usuarioOperacion;
	}
	public void setUsuarioOperacion(String usuarioOperacion) {
		this.usuarioOperacion = usuarioOperacion;
	}
	public String getUsuarioRedOperacion() {
		return usuarioRedOperacion;
	}
	public void setUsuarioRedOperacion(String usuarioRedOperacion) {
		this.usuarioRedOperacion = usuarioRedOperacion;
	}

	public String getPcOperacion() {
		return pcOperacion;
	}
	public void setPcOperacion(String pcOperacion) {
		this.pcOperacion = pcOperacion;
	}
	public String getIpOperacion() {
		return ipOperacion;
	}
	public void setIpOperacion(String ipOperacion) {
		this.ipOperacion = ipOperacion;
	}
	public String getMacOperacion() {
		return macOperacion;
	}
	public void setMacOperacion(String macOperacion) {
		this.macOperacion = macOperacion;
	}
	
	
}
