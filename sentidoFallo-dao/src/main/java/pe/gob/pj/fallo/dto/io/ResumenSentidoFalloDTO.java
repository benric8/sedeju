package pe.gob.pj.fallo.dto.io;

import java.util.ArrayList;
import java.util.List;

import pe.gob.pj.fallo.dto.AuditoriaDTO;
import pe.gob.pj.fallo.dto.ExpedienteDTO;

public class ResumenSentidoFalloDTO extends AuditoriaDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long nCarga;
	private int totalRegistros;
	private int totalRegistrosIncorrectos;
	private int totalRegistrosCorrectos;
	private String fecha;
	private String estado;
	private long usuario;
	private String nroIP;
	List<FormatoSentidoFallo> datos;
	List<ExpedienteDTO> listaExpedientes;
	
	

	public ResumenSentidoFalloDTO() {
		super();
		datos = new ArrayList<FormatoSentidoFallo>();
	}
	public int getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	public int getTotalRegistrosIncorrectos() {
		return totalRegistrosIncorrectos;
	}
	public void setTotalRegistrosIncorrectos(int totalRegistrosIncorrectos) {
		this.totalRegistrosIncorrectos = totalRegistrosIncorrectos;
	}
	public int getTotalRegistrosCorrectos() {
		return totalRegistrosCorrectos;
	}
	public void setTotalRegistrosCorrectos(int totalRegistrosCorrectos) {
		this.totalRegistrosCorrectos = totalRegistrosCorrectos;
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
	public long getUsuario() {
		return usuario;
	}
	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}
	public String getNroIP() {
		return nroIP;
	}
	public void setNroIP(String nroIP) {
		this.nroIP = nroIP;
	}
	public List<ExpedienteDTO> getListaExpedientes() {
		return listaExpedientes;
	}
	public void setListaExpedientes(List<ExpedienteDTO> listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}
	public List<FormatoSentidoFallo> getDatos() {
		return datos;
	}
	public void setDatos(List<FormatoSentidoFallo> datos) {
		this.datos = datos;
	}
}
