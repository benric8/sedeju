package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import pe.gob.pj.fallo.dto.ReporteDTO;

@Entity
@Table(name="movResumen", schema = "usrSentidoFallo")
@SqlResultSetMapping (
	    name="infoReporteMapping",
	    entities= {
	        @EntityResult(entityClass=ReporteDTO.class,
	           discriminatorColumn="clazz_",
	           fields={
	              @FieldResult(name="nCarga", column="n_carga"),
	              @FieldResult(name="anio", column="anio"),
	              @FieldResult(name="mes", column="mes"),
	              @FieldResult(name="dia", column="dia"),
	              @FieldResult(name="xDesOrgano", column="x_nomOrgjurisd"),
	              @FieldResult(name="cDistritoJudicial", column="c_distritoJudicial"),
	              @FieldResult(name="xNomDistrito", column="x_nomDistrito"),
	              @FieldResult(name="totalReg", column="n_total_reg_correctos")
	           }
	        )
	    }
	)
@NamedStoredProcedureQuery(
		name = MovResumen.RECUPERA_INFO_REPORTE_GENERICO,
		procedureName = "usrSentidoFallo.sp_reporte_generico",
		resultSetMappings = {
				"infoReporteMapping"
		},
		parameters = {		   
		   @StoredProcedureParameter(name = MovResumen.P_FECHA_DESDE, type = Timestamp.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovResumen.P_FECHA_HASTA, type = Timestamp.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovResumen.P_C_DISTRITO, type = String.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovResumen.P_C_ORGANO, type = String.class, mode = ParameterMode.IN )
	    }
)
public class MovResumen implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RECUPERA_INFO_REPORTE_GENERICO = "MovResumen.ReporteGenerico";
	public static final String P_FECHA_DESDE = "@f_desdes";
	public static final String P_FECHA_HASTA = "@f_hasta";
	public static final String P_C_DISTRITO = "@c_distrito";
	public static final String P_C_ORGANO = "@c_organo";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_carga")
	private Long nCarga;
	
	@Column(name="n_total_registros")
	private Integer nTotalRegistros;
		
	@Column(name="n_total_reg_incorrectos")
	private Integer nTotalRegIncorrectos;
		
	@Column(name="n_total_reg_correctos")
	private Integer nTotalRegCorrectos;
	
	@Column(name="f_carga")
	private Timestamp fCarga;
	
	@Column(name="l_estado")
	private String lEstado;
	      
	@Column(name="n_usuario")
	private Long nUsuario;
	
	@Column(name="x_ip")
	private String xIp;
	
	@Column(name="b_aud")
	private String bAud;

	@Column(name="f_aud")
	private Timestamp fAud;
	
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
	
	//bi-directional many-to-one association to MovExpediente
	@OneToMany(mappedBy="movResumen")
	private List<MovSentidofallo> movSentidofallo;
	
	public MovResumen() {
		super();
	}
	public Long getNCarga() {
		return nCarga;
	}
	public void setNCarga(Long nCarga) {
		this.nCarga = nCarga;
	}
	public Integer getNTotalRegistros() {
		return nTotalRegistros;
	}

	public void setNTotalRegistros(Integer nTotalRegistros) {
		this.nTotalRegistros = nTotalRegistros;
	}

	public Integer getNTotalRegIncorrectos() {
		return nTotalRegIncorrectos;
	}

	public void setNTotalRegIncorrectos(Integer nTotalRegIncorrectos) {
		this.nTotalRegIncorrectos = nTotalRegIncorrectos;
	}

	public Integer getNTotalRegCorrectos() {
		return nTotalRegCorrectos;
	}

	public void setNTotalRegCorrectos(Integer nTotalRegCorrectos) {
		this.nTotalRegCorrectos = nTotalRegCorrectos;
	}

	public Timestamp getFCarga() {
		return fCarga;
	}

	public void setFCarga(Timestamp fCarga) {
		this.fCarga = fCarga;
	}

	public String getLEstado() {
		return lEstado;
	}

	public void setLEstado(String lEstado) {
		this.lEstado = lEstado;
	}

	public Long getNUsuario() {
		return nUsuario;
	}

	public void setNUsuario(Long nUsuario) {
		this.nUsuario = nUsuario;
	}

	public String getXIp() {
		return xIp;
	}

	public void setXIp(String xIp) {
		this.xIp = xIp;
	}

	public String getBAud() {
		return bAud;
	}

	public void setBAud(String bAud) {
		this.bAud = bAud;
	}

	public Timestamp getFAud() {
		return fAud;
	}

	public void setFAud(Timestamp fAud) {
		this.fAud = fAud;
	}

	public String getCAudIp() {
		return cAudIp;
	}

	public void setCAudIp(String cAudIp) {
		this.cAudIp = cAudIp;
	}

	public String getCAudMcaddr() {
		return cAudMcaddr;
	}

	public void setCAudMcaddr(String cAudMcaddr) {
		this.cAudMcaddr = cAudMcaddr;
	}

	public String getCAudPc() {
		return cAudPc;
	}

	public void setCAudPc(String cAudPc) {
		this.cAudPc = cAudPc;
	}

	public String getCAudUid() {
		return cAudUid;
	}

	public void setCAudUid(String cAudUid) {
		this.cAudUid = cAudUid;
	}

	public String getCAudUidred() {
		return cAudUidred;
	}

	public void setCAudUidred(String cAudUidred) {
		this.cAudUidred = cAudUidred;
	}
	public List<MovSentidofallo> getMovSentidofallo() {
		return movSentidofallo;
	}
	public void setMovSentidofallo(List<MovSentidofallo> movSentidofallo) {
		this.movSentidofallo = movSentidofallo;
	}
}
