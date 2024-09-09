package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the maeUsuarios database table.
 * 
 */
@Entity
@Table(name="maeUsuarios", schema = "usrSentidoFallo")
@NamedQuery(name = "MaeUsuario.findAll", query="SELECT m FROM MaeUsuario m")
@NamedQuery(name = MaeUsuario.LOGIN_USER, query="SELECT m FROM MaeUsuario m WHERE m.cUsuario = :usuario AND m.cClave = :clave AND m.lActivo = '1' ")
@NamedQuery(name = MaeUsuario.FIND_BY_EMAIL, query="SELECT m FROM MaeUsuario m WHERE upper(m.xCorreo) = :email AND m.lActivo = '1' ")
@NamedQuery(name = MaeUsuario.FIND_BY_TOKEN, query="SELECT m FROM MaeUsuario m WHERE m.xTokenRecuperacion = :token AND m.fExpiracionToken > :fActual AND m.lActivo = '1' ")
@NamedQuery(name = MaeUsuario.UPD_CAMBIO_CLAVE, query = "UPDATE MaeUsuario u SET u.cClave= :nueva, u.bAud= 'U', u.cAudUidred= :usuario, u.cAudPc= :pcname, u.cAudIp= :numip, u.cAudMcaddr= :mcaddr, u.lReseteo = :reseteo WHERE u.nUsuario= :nUsuario AND u.cClave= :actual")
@NamedQuery(name = MaeUsuario.UPD_GENERA_TOKEN_RESETEO, query = "UPDATE MaeUsuario u SET u.bAud= 'U', u.cAudUidred= :usuario, u.cAudPc= :pcname, u.cAudIp= :numip, u.cAudMcaddr= :mcaddr, u.xTokenRecuperacion = :token, u.fExpiracionToken= :fechaToken WHERE u.nUsuario= :nUsuario")
@NamedQuery(name = MaeUsuario.UPD_CONFIRMA_TOKEN_RESETEO, query = "UPDATE MaeUsuario u SET u.cClave= :nueva, u.bAud= 'U', u.cAudUidred= :usuario, u.cAudPc= :pcname, u.cAudIp= :numip, u.cAudMcaddr= :mcaddr, u.xTokenRecuperacion = null, u.fExpiracionToken= null WHERE u.nUsuario= :nUsuario")
@NamedQuery(name = MaeUsuario.BUSCAR_POR_ID, query="SELECT m FROM MaeUsuario m WHERE m.nUsuario=:id ")
@NamedQuery(name = MaeUsuario.BUSCAR_POR_USUARIO, query="SELECT m FROM MaeUsuario m WHERE m.cUsuario=:usuario ")
@NamedQuery(name = MaeUsuario.BUSCAR_POR_DNI, query="SELECT m FROM MaeUsuario m WHERE m.xDocIdentidad=:dni ")
@NamedQuery(name = MaeUsuario.LISTA_ORGANOS_JURIS, query="SELECT m.xNomOrgjurisd FROM MaeUsuario m WHERE m.maeDistritosJudiciale.c_distritoJudicial =:cDistrito GROUP BY m.xNomOrgjurisd ")
public class MaeUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String LOGIN_USER = "MaeUsuario.login";
	public static final String FIND_BY_EMAIL = "MaeUsuario.findByEmail";
	public static final String FIND_BY_TOKEN = "MaeUsuario.findByToken";
	public static final String LISTA_ORGANOS_JURIS = "MaeUsuario.listOrganos";
	public static final String P_EMAIL = "email";
	public static final String P_USUARIO = "usuario";
	public static final String P_CLAVE = "clave";
	public static final String P_TOKEN = "token";
	public static final String P_FACTUAL = "fActual";
	public static final String C_DISTRITO = "cDistrito";
	
	public static final String BUSCAR_POR_ID = "buscarPorId";
	public static final String P_ID = "id";
	public static final String BUSCAR_POR_USUARIO = "MaeUsuario.buscarPorDNI";
	public static final String BUSCAR_POR_DNI = "MaeUsuario.buscarPorUsuario";
	public static final String P_DNI = "dni";
	
	public static final String UPD_CAMBIO_CLAVE = "MaeUsuario.cambiaClave";
	public static final String UPD_GENERA_TOKEN_RESETEO = "MaeUsuario.genraReseteoClave";
	public static final String UPD_CONFIRMA_TOKEN_RESETEO = "MaeUsuario.confirmaReseteoClave";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_usuario")
	private Long nUsuario;

	@Column(name="b_aud")
	private String bAud;

	@Column(name="c_aud_ip")
	private String cAudIp;

	@Column(name="c_aud_mcaddr")
	private String cAudMcaddr;

	@Column(name="c_aud_pc")
	private String cAudPc;

	@Column(name="c_aud_uid")
	private String cAudUid;

	@Column(name="c_aud_uidred")
	private String cAudUidred;

	@Column(name="c_clave")
	private String cClave;

	@Column(name="c_usuario")
	private String cUsuario;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="x_apeMaterno")
	private String xApeMaterno;

	@Column(name="x_apePaterno")
	private String xApePaterno;

	@Column(name="x_docIdentidad")
	private String xDocIdentidad;

	@Column(name="x_nomOrgjurisd")
	private String xNomOrgjurisd;

	@Column(name="x_nomUsuario")
	private String xNomUsuario;

	//bi-directional many-to-one association to MaeDistritosJudiciale
	@ManyToOne
	@JoinColumn(name="c_distritoJudicial")
	private MaeDistritosJudiciale maeDistritosJudiciale;

	//bi-directional many-to-one association to MaePerfil
	@ManyToOne
	@JoinColumn(name="n_perfil")
	private MaePerfil maePerfil;
	
	@Column(name="l_reseteo")
	private String lReseteo;
	
	@Column(name="l_reniec")
	private String lReniec;
	
	@Column(name="x_correo")
	private String xCorreo;
	
	@Column(name="x_tokenRecuperacion")
	private String xTokenRecuperacion;
	
	@Column(name="f_expiracionToken")
	private Timestamp fExpiracionToken;

	public MaeUsuario() {
	}

	public Long getNUsuario() {
		return this.nUsuario;
	}

	public void setNUsuario(Long nUsuario) {
		this.nUsuario = nUsuario;
	}

	public String getBAud() {
		return this.bAud;
	}

	public void setBAud(String bAud) {
		this.bAud = bAud;
	}

	public String getCAudIp() {
		return this.cAudIp;
	}

	public void setCAudIp(String cAudIp) {
		this.cAudIp = cAudIp;
	}

	public String getCAudMcaddr() {
		return this.cAudMcaddr;
	}

	public void setCAudMcaddr(String cAudMcaddr) {
		this.cAudMcaddr = cAudMcaddr;
	}

	public String getCAudPc() {
		return this.cAudPc;
	}

	public void setCAudPc(String cAudPc) {
		this.cAudPc = cAudPc;
	}

	public String getCAudUid() {
		return this.cAudUid;
	}

	public void setCAudUid(String cAudUid) {
		this.cAudUid = cAudUid;
	}

	public String getCAudUidred() {
		return this.cAudUidred;
	}

	public void setCAudUidred(String cAudUidred) {
		this.cAudUidred = cAudUidred;
	}

	public String getCClave() {
		return this.cClave;
	}

	public void setCClave(String cClave) {
		this.cClave = cClave;
	}

	public String getCUsuario() {
		return this.cUsuario;
	}

	public void setCUsuario(String cUsuario) {
		this.cUsuario = cUsuario;
	}

	public Timestamp getFAud() {
		return this.fAud;
	}

	public void setFAud(Timestamp fAud) {
		this.fAud = fAud;
	}

	public String getLActivo() {
		return this.lActivo;
	}

	public void setLActivo(String lActivo) {
		this.lActivo = lActivo;
	}

	public String getXApeMaterno() {
		return xApeMaterno;
	}

	public void setXApeMaterno(String xApeMaterno) {
		this.xApeMaterno = xApeMaterno;
	}

	public String getXApePaterno() {
		return xApePaterno;
	}

	public void setXApePaterno(String xApePaterno) {
		this.xApePaterno = xApePaterno;
	}

	public String getXDocIdentidad() {
		return xDocIdentidad;
	}

	public void setXDocIdentidad(String xDocIdentidad) {
		this.xDocIdentidad = xDocIdentidad;
	}

	public String getXNomOrgjurisd() {
		return xNomOrgjurisd;
	}

	public void setXNomOrgjurisd(String xNomOrgjurisd) {
		this.xNomOrgjurisd = xNomOrgjurisd;
	}

	public String getXNomUsuario() {
		return xNomUsuario;
	}

	public void setXNomUsuario(String xNomUsuario) {
		this.xNomUsuario = xNomUsuario;
	}

	public MaeDistritosJudiciale getMaeDistritosJudiciale() {
		return this.maeDistritosJudiciale;
	}

	public void setMaeDistritosJudiciale(MaeDistritosJudiciale maeDistritosJudiciale) {
		this.maeDistritosJudiciale = maeDistritosJudiciale;
	}

	public MaePerfil getMaePerfil() {
		return this.maePerfil;
	}

	public void setMaePerfil(MaePerfil maePerfil) {
		this.maePerfil = maePerfil;
	}

	public String getLReseteo() {
		return lReseteo;
	}

	public void setLReseteo(String lReseteo) {
		this.lReseteo = lReseteo;
	}

	public String getLReniec() {
		return lReniec;
	}

	public void setLReniec(String lReniec) {
		this.lReniec = lReniec;
	}

	public String getXCorreo() {
		return xCorreo;
	}

	public void setXCorreo(String xCorreo) {
		this.xCorreo = xCorreo;
	}

	public String getXTokenRecuperacion() {
		return xTokenRecuperacion;
	}

	public void setXTokenRecuperacion(String xTokenRecuperacion) {
		this.xTokenRecuperacion = xTokenRecuperacion;
	}

	public Timestamp getFExpiracionToken() {
		return fExpiracionToken;
	}

	public void setFExpiracionToken(Timestamp fExpiracionToken) {
		this.fExpiracionToken = fExpiracionToken;
	}	
	
}