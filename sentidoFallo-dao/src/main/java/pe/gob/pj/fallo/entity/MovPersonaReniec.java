package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "movPersonaReniec", schema = "usrSentidoFallo")
@NamedQuery(name = MovPersonaReniec.RECUPERAR_TODOS, query="SELECT m FROM MovPersonaReniec m WHERE m.lActivo = '1' ")
@NamedQuery(name = MovPersonaReniec.VALIDAR_PERSONA, query="SELECT m FROM MovPersonaReniec m WHERE m.lActivo = '1' AND m.xDni = :dni ")
public class MovPersonaReniec implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String RECUPERAR_TODOS = "MovPersonaReniec.findAll";
	public static final String VALIDAR_PERSONA = "MovPersonaReniec.validarPersona";
	
	public static final String P_DNI = "dni";

	@Id
	@Column(name = "x_dni")
	private String xDni;

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

	@Column(name = "l_activo")
	private String lActivo;
	
	@Column(name = "x_primer_apellido")
	private String xPrimerApellido;
	 
	@Column(name = "x_segundo_apellido")
	private String xSegundoApellido;
	
	@Column(name = "x_nombre")
	private String xNombre;
	
	@Column(name = "x_nombre_completo")
		private String xNombreCompleto;

	

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

	public String getlActivo() {
		return lActivo;
	}

	public void setlActivo(String lActivo) {
		this.lActivo = lActivo;
	}

	public String getxDni() {
		return xDni;
	}

	public void setxDni(String xDni) {
		this.xDni = xDni;
	}

	public String getxPrimerApellido() {
		return xPrimerApellido;
	}

	public void setxPrimerApellido(String xPrimerApellido) {
		this.xPrimerApellido = xPrimerApellido;
	}

	public String getxSegundoApellido() {
		return xSegundoApellido;
	}

	public void setxSegundoApellido(String xSegundoApellido) {
		this.xSegundoApellido = xSegundoApellido;
	}

	public String getxNombre() {
		return xNombre;
	}

	public void setxNombre(String xNombre) {
		this.xNombre = xNombre;
	}

	public String getxNombreCompleto() {
		return xNombreCompleto;
	}

	public void setxNombreCompleto(String xNombreCompleto) {
		this.xNombreCompleto = xNombreCompleto;
	}
}
