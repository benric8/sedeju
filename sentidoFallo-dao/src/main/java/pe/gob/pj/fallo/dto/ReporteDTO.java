package pe.gob.pj.fallo.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReporteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long nCarga;
	private Integer anio;
	private String mes;
	private String dia;
	private String xDesOrgano;
	private String cDistritoJudicial;
	private String xNomDistrito;
	private Integer totalReg;

	public long getnCarga() {
		return nCarga;
	}

	public void setnCarga(long nCarga) {
		this.nCarga = nCarga;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getxDesOrgano() {
		return xDesOrgano;
	}

	public void setxDesOrgano(String xDesOrgano) {
		this.xDesOrgano = xDesOrgano;
	}

	public String getcDistritoJudicial() {
		return cDistritoJudicial;
	}

	public void setcDistritoJudicial(String cDistritoJudicial) {
		this.cDistritoJudicial = cDistritoJudicial;
	}

	public String getxNomDistrito() {
		return xNomDistrito;
	}

	public void setxNomDistrito(String xNomDistrito) {
		this.xNomDistrito = xNomDistrito;
	}

	public Integer getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(Integer totalReg) {
		this.totalReg = totalReg;
	}

}