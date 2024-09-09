package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the movExpedientes database table.
 * 
 */
@Entity
@Table(name="movExpedientes", schema = "usrSentidoFallo")
@NamedQuery(name="MovExpediente.findAll", query="SELECT m FROM MovExpediente m")
@NamedQuery(name=MovExpediente.BUSCAR_POR_CODIGO, query="SELECT m FROM MovExpediente m WHERE m.xFormato = :xFormato ")
public class MovExpediente implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String BUSCAR_POR_CODIGO = "buscarPorCodigo";
	public static final String P_X_FORMATO = "xFormato";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_expediente")
	private long nExpediente;

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

	@Column(name="c_especialidad")
	private String cEspecialidad;

	@Column(name="c_orgjurisd")
	private String cOrgjurisd;

	@Column(name="c_proceso")
	private String cProceso;

	@Column(name="c_provincia")
	private String cProvincia;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="f_registro")
	private Timestamp fRegistro;

	@Column(name="l_activo")
	private String lActivo;

	private String x_descEspecialidad;

	private String x_descProceso;

	@Column(name="x_formato")
	private String xFormato;

	private String x_nomOrgjurisd;

	//bi-directional many-to-one association to MaeDistritosJudiciale
	@ManyToOne
	@JoinColumn(name="c_distritoJudicial")
	private MaeDistritosJudiciale maeDistritosJudiciale;

	//bi-directional many-to-one association to MovSentidofallo
	@OneToMany(mappedBy="movExpediente")
	private List<MovSentidofallo> movSentidofallos;
	
	public MovExpediente() {
	}

	public long getNExpediente() {
		return this.nExpediente;
	}

	public void setNExpediente(long nExpediente) {
		this.nExpediente = nExpediente;
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

	public String getCEspecialidad() {
		return this.cEspecialidad;
	}

	public void setCEspecialidad(String cEspecialidad) {
		this.cEspecialidad = cEspecialidad;
	}

	public String getCOrgjurisd() {
		return this.cOrgjurisd;
	}

	public void setCOrgjurisd(String cOrgjurisd) {
		this.cOrgjurisd = cOrgjurisd;
	}

	public String getCProceso() {
		return this.cProceso;
	}

	public void setCProceso(String cProceso) {
		this.cProceso = cProceso;
	}

	public String getCProvincia() {
		return this.cProvincia;
	}

	public void setCProvincia(String cProvincia) {
		this.cProvincia = cProvincia;
	}

	public Timestamp getFAud() {
		return this.fAud;
	}

	public void setFAud(Timestamp fAud) {
		this.fAud = fAud;
	}

	public Timestamp getFRegistro() {
		return this.fRegistro;
	}

	public void setFRegistro(Timestamp fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getLActivo() {
		return this.lActivo;
	}

	public void setLActivo(String lActivo) {
		this.lActivo = lActivo;
	}

	public String getX_descEspecialidad() {
		return this.x_descEspecialidad;
	}

	public void setX_descEspecialidad(String x_descEspecialidad) {
		this.x_descEspecialidad = x_descEspecialidad;
	}

	public String getX_descProceso() {
		return this.x_descProceso;
	}

	public void setX_descProceso(String x_descProceso) {
		this.x_descProceso = x_descProceso;
	}

	public String getXFormato() {
		return this.xFormato;
	}

	public void setXFormato(String xFormato) {
		this.xFormato = xFormato;
	}

	public String getX_nomOrgjurisd() {
		return this.x_nomOrgjurisd;
	}

	public void setX_nomOrgjurisd(String x_nomOrgjurisd) {
		this.x_nomOrgjurisd = x_nomOrgjurisd;
	}

	public MaeDistritosJudiciale getMaeDistritosJudiciale() {
		return this.maeDistritosJudiciale;
	}

	public void setMaeDistritosJudiciale(MaeDistritosJudiciale maeDistritosJudiciale) {
		this.maeDistritosJudiciale = maeDistritosJudiciale;
	}

	public List<MovSentidofallo> getMovSentidofallos() {
		return this.movSentidofallos;
	}

	public void setMovSentidofallos(List<MovSentidofallo> movSentidofallos) {
		this.movSentidofallos = movSentidofallos;
	}

	public MovSentidofallo addMovSentidofallo(MovSentidofallo movSentidofallo) {
		getMovSentidofallos().add(movSentidofallo);
		movSentidofallo.setMovExpediente(this);

		return movSentidofallo;
	}

	public MovSentidofallo removeMovSentidofallo(MovSentidofallo movSentidofallo) {
		getMovSentidofallos().remove(movSentidofallo);
		movSentidofallo.setMovExpediente(null);

		return movSentidofallo;
	}

}