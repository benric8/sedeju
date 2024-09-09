package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the maePerfil database table.
 * 
 */
@Entity
@Table(name="maePerfil", schema = "usrSentidoFallo")
@NamedQuery(name="MaePerfil.findAll", query="SELECT m FROM MaePerfil m")
@NamedQuery(name="MaePerfil.listar", query="SELECT m FROM MaePerfil m WHERE m.lActivo='1' ")
public class MaePerfil implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String LISTAR = "MaePerfil.listar";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_perfil")
	private Long nPerfil;

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

	@Column(name="f_registro")
	private Timestamp fRegistro;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="x_descripcion")
	private String xDescripcion;

	//bi-directional many-to-one association to MaeUsuario
	@OneToMany(mappedBy="maePerfil")
	private List<MaeUsuario> maeUsuarios;

	public MaePerfil() {
	}

	public Long getNPerfil() {
		return this.nPerfil;
	}

	public void setNPerfil(Long nPerfil) {
		this.nPerfil = nPerfil;
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

	public String getXDescripcion() {
		return this.xDescripcion;
	}

	public void setXDescripcion(String xDescripcion) {
		this.xDescripcion = xDescripcion;
	}

	public List<MaeUsuario> getMaeUsuarios() {
		return this.maeUsuarios;
	}

	public void setMaeUsuarios(List<MaeUsuario> maeUsuarios) {
		this.maeUsuarios = maeUsuarios;
	}

	public MaeUsuario addMaeUsuario(MaeUsuario maeUsuario) {
		getMaeUsuarios().add(maeUsuario);
		maeUsuario.setMaePerfil(this);

		return maeUsuario;
	}

	public MaeUsuario removeMaeUsuario(MaeUsuario maeUsuario) {
		getMaeUsuarios().remove(maeUsuario);
		maeUsuario.setMaePerfil(null);

		return maeUsuario;
	}

}