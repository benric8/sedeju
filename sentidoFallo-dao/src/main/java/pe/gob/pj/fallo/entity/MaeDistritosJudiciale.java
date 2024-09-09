package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the maeDistritosJudiciales database table.
 * 
 */
@Entity
@Table(name="maeDistritosJudiciales", schema = "usrSentidoFallo")
@NamedQuery(name="MaeDistritosJudiciale.findAll", query="SELECT m FROM MaeDistritosJudiciale m")
@NamedQuery(name="MaeDistritosJudiciale.listar", query="SELECT m FROM MaeDistritosJudiciale m WHERE m.lActivo='1' ")
public class MaeDistritosJudiciale implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String LISTAR = "MaeDistritosJudiciale.listar";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String c_distritoJudicial;

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

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="l_activo")
	private String lActivo;

	private String x_nomDistrito;

	//bi-directional many-to-one association to MaeUsuario
	@OneToMany(mappedBy="maeDistritosJudiciale")
	private List<MaeUsuario> maeUsuarios;

	//bi-directional many-to-one association to MovExpediente
	@OneToMany(mappedBy="maeDistritosJudiciale")
	private List<MovExpediente> movExpedientes;

	//bi-directional many-to-one association to MovSentidofallo
	@OneToMany(mappedBy="maeDistritosJudiciale")
	private List<MovSentidofallo> movSentidofallos;

	public MaeDistritosJudiciale() {
	}

	public String getC_distritoJudicial() {
		return this.c_distritoJudicial;
	}

	public void setC_distritoJudicial(String c_distritoJudicial) {
		this.c_distritoJudicial = c_distritoJudicial;
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

	public String getX_nomDistrito() {
		return this.x_nomDistrito;
	}

	public void setX_nomDistrito(String x_nomDistrito) {
		this.x_nomDistrito = x_nomDistrito;
	}

	public List<MaeUsuario> getMaeUsuarios() {
		return this.maeUsuarios;
	}

	public void setMaeUsuarios(List<MaeUsuario> maeUsuarios) {
		this.maeUsuarios = maeUsuarios;
	}

	public MaeUsuario addMaeUsuario(MaeUsuario maeUsuario) {
		getMaeUsuarios().add(maeUsuario);
		maeUsuario.setMaeDistritosJudiciale(this);

		return maeUsuario;
	}

	public MaeUsuario removeMaeUsuario(MaeUsuario maeUsuario) {
		getMaeUsuarios().remove(maeUsuario);
		maeUsuario.setMaeDistritosJudiciale(null);

		return maeUsuario;
	}

	public List<MovExpediente> getMovExpedientes() {
		return this.movExpedientes;
	}

	public void setMovExpedientes(List<MovExpediente> movExpedientes) {
		this.movExpedientes = movExpedientes;
	}

	public MovExpediente addMovExpediente(MovExpediente movExpediente) {
		getMovExpedientes().add(movExpediente);
		movExpediente.setMaeDistritosJudiciale(this);

		return movExpediente;
	}

	public MovExpediente removeMovExpediente(MovExpediente movExpediente) {
		getMovExpedientes().remove(movExpediente);
		movExpediente.setMaeDistritosJudiciale(null);

		return movExpediente;
	}

	public List<MovSentidofallo> getMovSentidofallos() {
		return this.movSentidofallos;
	}

	public void setMovSentidofallos(List<MovSentidofallo> movSentidofallos) {
		this.movSentidofallos = movSentidofallos;
	}

	public MovSentidofallo addMovSentidofallo(MovSentidofallo movSentidofallo) {
		getMovSentidofallos().add(movSentidofallo);
		movSentidofallo.setMaeDistritosJudiciale(this);

		return movSentidofallo;
	}

	public MovSentidofallo removeMovSentidofallo(MovSentidofallo movSentidofallo) {
		getMovSentidofallos().remove(movSentidofallo);
		movSentidofallo.setMaeDistritosJudiciale(null);

		return movSentidofallo;
	}

}