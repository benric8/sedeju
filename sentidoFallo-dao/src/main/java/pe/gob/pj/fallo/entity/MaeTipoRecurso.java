package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "maeTipoRecurso", schema = "usrSentidoFallo")
@NamedQuery(name = MaeTipoRecurso.RECUPERAR_TODOS, query="SELECT m FROM MaeTipoRecurso m WHERE m.lActivo = '1' ORDER BY m.xTipoRecurso ")
public class MaeTipoRecurso implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String RECUPERAR_TODOS = "MaeTipoRecurso.findAll";

	@Id
	@Column(name = "c_tipoRecurso")
	private String cTipoRecurso;

	@Column(name = "b_aud")
	private String bAud;

	@Column(name = "c_aud_ip")
	private String cAudIp;

	@Column(name = "c_aud_mcaddr")
	private String cAudMcaddr;

	@Column(name = "c_aud_pc")
	private String cAudPc;

	@Column(name = "c_aud_uid")
	private String cAudUid;

	@Column(name = "c_aud_uidred")
	private String cAudUidred;

	@Column(name = "f_aud")
	private Timestamp fAud;

	@Column(name = "f_registro")
	private Timestamp fRegistro;

	@Column(name = "l_activo")
	private String lActivo;

	@Column(name = "x_tipoRecurso")
	private String xTipoRecurso;

	public String getcTipoRecurso() {
		return cTipoRecurso;
	}

	public void setcTipoRecurso(String cTipoRecurso) {
		this.cTipoRecurso = cTipoRecurso;
	}

	public String getbAud() {
		return bAud;
	}

	public void setbAud(String bAud) {
		this.bAud = bAud;
	}

	public String getcAudIp() {
		return cAudIp;
	}

	public void setcAudIp(String cAudIp) {
		this.cAudIp = cAudIp;
	}

	public String getcAudMcaddr() {
		return cAudMcaddr;
	}

	public void setcAudMcaddr(String cAudMcaddr) {
		this.cAudMcaddr = cAudMcaddr;
	}

	public String getcAudPc() {
		return cAudPc;
	}

	public void setcAudPc(String cAudPc) {
		this.cAudPc = cAudPc;
	}

	public String getcAudUid() {
		return cAudUid;
	}

	public void setcAudUid(String cAudUid) {
		this.cAudUid = cAudUid;
	}

	public String getcAudUidred() {
		return cAudUidred;
	}

	public void setcAudUidred(String cAudUidred) {
		this.cAudUidred = cAudUidred;
	}

	public Timestamp getfAud() {
		return fAud;
	}

	public void setfAud(Timestamp fAud) {
		this.fAud = fAud;
	}

	public Timestamp getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Timestamp fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getlActivo() {
		return lActivo;
	}

	public void setlActivo(String lActivo) {
		this.lActivo = lActivo;
	}

	public String getxTipoRecurso() {
		return xTipoRecurso;
	}

	public void setxTipoRecurso(String xTipoRecurso) {
		this.xTipoRecurso = xTipoRecurso;
	}

}
