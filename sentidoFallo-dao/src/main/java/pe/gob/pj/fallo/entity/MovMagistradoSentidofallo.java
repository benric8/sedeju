package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the movMagistradoSentidofallos database table.
 * 
 */
@Entity
@Table(name="movMagistradoSentidofallos", schema = "usrSentidoFallo")
@NamedQuery(name=MovMagistradoSentidofallo.RECUPERA_MAGISTRADOS, query="SELECT m.n_magistradoSentido, m.x_nomMagistrado FROM MovMagistradoSentidofallo m WHERE m.movSentidofallo.n_sentidoFallo= :idSentido AND m.lActivo='1'")
@NamedQuery(name="MovMagistradoSentidofallo.findAll", query="SELECT m FROM MovMagistradoSentidofallo m")
public class MovMagistradoSentidofallo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String RECUPERA_MAGISTRADOS= "MovMagistradoSentidofallo.FindByIdSentido";
	public static final String P_SENTIDO= "idSentido";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long n_magistradoSentido;

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

	private String x_nomMagistrado;
	
	@Column(name="x_dni")
	private String xDni;

	//bi-directional many-to-one association to MovSentidofallo
	@ManyToOne
	@JoinColumn(name="n_sentidoFallo")
	private MovSentidofallo movSentidofallo;

	public MovMagistradoSentidofallo() {
	}

	public long getN_magistradoSentido() {
		return this.n_magistradoSentido;
	}

	public void setN_magistradoSentido(long n_magistradoSentido) {
		this.n_magistradoSentido = n_magistradoSentido;
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

	public String getX_nomMagistrado() {
		return this.x_nomMagistrado;
	}

	public void setX_nomMagistrado(String x_nomMagistrado) {
		this.x_nomMagistrado = x_nomMagistrado;
	}
	
	public String getXDni() {
		return xDni;
	}

	public void setXDni(String xDni) {
		this.xDni = xDni;
	}

	public MovSentidofallo getMovSentidofallo() {
		return this.movSentidofallo;
	}

	public void setMovSentidofallo(MovSentidofallo movSentidofallo) {
		this.movSentidofallo = movSentidofallo;
	}

}