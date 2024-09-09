package pe.gob.pj.fallo.entity;

import java.io.Serializable;
import javax.persistence.*;

import pe.gob.pj.fallo.dto.ReporteDetalladoDTO;
import pe.gob.pj.fallo.dto.SentidoFalloDTO;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the movSentidofallos database table.
 * 
 */
@Entity
@Table(name="movSentidofallos", schema = "usrSentidoFallo")
@SqlResultSetMapping (
	    name="XInfoSProcMapping1",
	    entities= {
	        @EntityResult(entityClass=SentidoFalloDTO.class,
	           discriminatorColumn="clazz_",
	           fields={
								@FieldResult(name = "nExpediente", column = "nExpediente"),
								@FieldResult(name = "xFormato", column = "xFormato"),
								@FieldResult(name = "xNomDistrito", column = "xNomDistrito"),
								@FieldResult(name = "xDescDistrito", column = "xDescDistrito"),
								@FieldResult(name = "xDescrEspecialidad", column = "xDescrEspecialidad"),
								@FieldResult(name = "nSentidoFallo", column = "nSentidoFallo"),
								@FieldResult(name = "xDescSentido", column = "xDescSentido"),
								@FieldResult(name = "fSentidoFallo", column = "fSentidoFallo"),
								@FieldResult(name = "fRegistro", column = "fRegistro"),
								@FieldResult(name = "xNomOrgJurisd", column = "xNomOrgJurisd"),
								@FieldResult(name = "nSentidoFalloMagistrado", column = "nSentidoFalloMagistrado"),
								@FieldResult(name = "nMagistradoSentido", column = "nMagistradoSentido"),
								@FieldResult(name = "xNomMagistrado", column = "xNomMagistrado"),
								@FieldResult(name = "xDescProceso", column = "xDescProceso"),
								@FieldResult(name = "xDescRecurso", column = "xDescRecuro"),
								@FieldResult(name = "nNumRecurso", column = "nNumRecurso"),
								@FieldResult(name = "nAnioRecurso", column = "nAnioRecurso"),
								@FieldResult(name = "fFechaVisto", column = "fFechaVisto"),
								@FieldResult(name = "cDistrito", column = "cDistrito")
	           }
	        )
	    }
	)
@NamedStoredProcedureQuery(
		name = MovSentidofallo.RECUPERAR_SENTIDO_FALLOS,
		procedureName = "usrSentidoFallo.sp_consulta_sentidos",
		resultSetMappings = {
				"XInfoSProcMapping1"
		},
		parameters = {		   
		   @StoredProcedureParameter(name = MovSentidofallo.P_X_FORMATO, type = String.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovSentidofallo.P_C_TIPO_RECURSO, type = String.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovSentidofallo.P_N_NUM_RECURSO, type = Integer.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovSentidofallo.P_N_ANIO_RECURSO, type = Integer.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovSentidofallo.P_C_ORGANO, type = String.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovSentidofallo.P_N_TIPO, type = Integer.class, mode = ParameterMode.IN ),
	    }
)
@SqlResultSetMapping (
	    name="infoReporteDetalleMapping",
	    entities= {
	        @EntityResult(entityClass=ReporteDetalladoDTO.class,
	           discriminatorColumn="clazz_",
	           fields={
	              @FieldResult(name = "nExpediente", column = "nExpediente"),
	              @FieldResult(name = "xFormato", column = "xFormato"),
	              @FieldResult(name = "anio", column = "anio"),
	              @FieldResult(name = "mes", column = "mes"),
	              @FieldResult(name = "dia", column = "dia"),
	              @FieldResult(name = "fSentidoFallo", column = "fSentidoFallo"),
	              @FieldResult(name = "fRegistro", column = "fRegistro"),
	              @FieldResult(name = "xNomOrgJurisd", column = "xNomOrgJurisd"),
	              @FieldResult(name = "xNomDistrito", column = "xNomDistrito"),	              
	              @FieldResult(name = "xDescSentido", column = "xDescSentido"),
	              @FieldResult(name = "xDescRecuro", column = "xDescRecuro"),
	              @FieldResult(name = "nNumRecurso", column = "nNumRecurso"),
	              @FieldResult(name = "xDescrEspecialidad", column = "xDescrEspecialidad"),	              
	              @FieldResult(name = "xDescProceso", column = "xDescProceso"),
	              @FieldResult(name = "fFechaVisto", column = "fFechaVisto"),
	              @FieldResult(name = "nSentidoFallo", column = "nSentidoFallo"),
	              @FieldResult(name = "xNomMagistrado", column = "xNomMagistrado" )
	           }
	        )
	    }
	)
@NamedStoredProcedureQuery(
		name = MovSentidofallo.RECUPERA_INFO_REPORTE_DETALLADO,
		procedureName = "usrSentidoFallo.sp_reporte_publi_sentidos",
		resultSetMappings = {
				"infoReporteDetalleMapping"
		},
		parameters = {		   
		   @StoredProcedureParameter(name = MovResumen.P_FECHA_DESDE, type = Timestamp.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovResumen.P_FECHA_HASTA, type = Timestamp.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovResumen.P_C_DISTRITO, type = String.class, mode = ParameterMode.IN ),
		   @StoredProcedureParameter(name = MovResumen.P_C_ORGANO, type = String.class, mode = ParameterMode.IN )
	    }
)
@NamedQuery(name="MovSentidofallo.findAll", query="SELECT m FROM MovSentidofallo m")
@NamedQuery(name=MovSentidofallo.VALIDAR_SENTIDO_FALLOS, query="SELECT m FROM MovSentidofallo m INNER JOIN m.movExpediente e where e.xFormato = :xFormato and m.x_nomOrgjurisd = :organo ")
@NamedQuery(name=MovSentidofallo.DETALLAR_SENTIDO_FALLOS, query="SELECT m FROM MovSentidofallo m INNER JOIN m.movExpediente e where m.movResumen.nCarga = :carga ")
@NamedQuery(name=MovSentidofallo.RECUPERA_JUZGADOS, query="SELECT m.cOrgjurisd, m.x_nomOrgjurisd  FROM MovSentidofallo m WHERE m.maeDistritosJudiciale.c_distritoJudicial='70' AND m.cTipoRecurso <> NULL AND m.nNumeroRecurso <> NULL AND m.nAnioRecurso <> NULL  AND m.lActivo = '1' GROUP BY m.cOrgjurisd, m.x_nomOrgjurisd ")
@NamedQuery(name=MovSentidofallo.RECUPERA_JUZGADOS_TODOS, query="SELECT m.x_nomOrgjurisd  FROM MovSentidofallo m WHERE m.maeDistritosJudiciale.c_distritoJudicial=:cDistrito AND m.lActivo = '1' GROUP BY m.x_nomOrgjurisd ")
public class MovSentidofallo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String RECUPERAR_SENTIDO_FALLOS = "recuperaSentidosFallos";
	public static final String P_X_FORMATO = "@x_formato";
	public static final String P_C_TIPO_RECURSO = "@c_tipo_recurso";
	public static final String P_N_NUM_RECURSO = "@n_num_recurso";
	public static final String P_N_ANIO_RECURSO = "@n_anio_recurso";
	public static final String P_C_ORGANO = "@c_organo";
	public static final String P_N_TIPO = "@n_tipo";
	public final static String P_CURSOR="p_resultado";
	public static final String P_X_FORMATO_SIN = "xFormato";
	
	public static final String VALIDAR_SENTIDO_FALLOS = "MovSentidofallo.validarSentidoFallos";
	public static final String P_ORGANO = "organo";
	
	public static final String DETALLAR_SENTIDO_FALLOS="MovSentidofallo.detallarSentidoFallos";
	public static final String P_CARGA = "carga";
	
	public static final String RECUPERA_JUZGADOS="MovSentidofallo.recuperaJuzgados";
	public static final String RECUPERA_JUZGADOS_TODOS="MovSentidofallo.recuperaJuzgadosTodos";
	
	public static final String RECUPERA_INFO_REPORTE_DETALLADO = "MovSentidoFallos.ReporteDetallado";
	public static final String P_FECHA_DESDE = "@f_desdes";
	public static final String P_FECHA_HASTA = "@f_hasta";
	public static final String P_C_DISTRITO = "@c_distrito";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long n_sentidoFallo;

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

	@Column(name="c_orgjurisd")
	private String cOrgjurisd;

	@Column(name="c_provincia")
	private String cProvincia;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="f_registro")
	private Timestamp fRegistro;

	private Timestamp f_sentidoFallo;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="n_usuario")
	private Long nUsuario;

	private String x_descSentido;

	private String x_nomOrgjurisd;
	
	@Column(name="c_tipoRecurso")
	private String cTipoRecurso;
	
	@Column(name="n_numeroRecurso")
	private Integer nNumeroRecurso;
	
	@Column(name="n_anioRecurso")
	private Long nAnioRecurso;

	//bi-directional many-to-one association to MovMagistradoSentidofallo
	@OneToMany(mappedBy="movSentidofallo")
	private List<MovMagistradoSentidofallo> movMagistradoSentidofallos;

	//bi-directional many-to-one association to MaeDistritosJudiciale
	@ManyToOne
	@JoinColumn(name="c_distritoJudicial")
	private MaeDistritosJudiciale maeDistritosJudiciale;

	//bi-directional many-to-one association to MovExpediente
	@ManyToOne
	@JoinColumn(name="n_expediente")
	private MovExpediente movExpediente;
	
	//bi-directional many-to-one association to MovResumen
	@ManyToOne
	@JoinColumn(name="n_carga")
	private MovResumen movResumen;

	public MovSentidofallo() {
	}

	public long getN_sentidoFallo() {
		return this.n_sentidoFallo;
	}

	public void setN_sentidoFallo(long n_sentidoFallo) {
		this.n_sentidoFallo = n_sentidoFallo;
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

	public String getCOrgjurisd() {
		return this.cOrgjurisd;
	}

	public void setCOrgjurisd(String cOrgjurisd) {
		this.cOrgjurisd = cOrgjurisd;
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

	public Timestamp getF_sentidoFallo() {
		return this.f_sentidoFallo;
	}

	public void setF_sentidoFallo(Timestamp f_sentidoFallo) {
		this.f_sentidoFallo = f_sentidoFallo;
	}

	public String getLActivo() {
		return this.lActivo;
	}

	public void setLActivo(String lActivo) {
		this.lActivo = lActivo;
	}

	public Long getNUsuario() {
		return this.nUsuario;
	}

	public void setNUsuario(Long nUsuario) {
		this.nUsuario = nUsuario;
	}

	public String getX_descSentido() {
		return this.x_descSentido;
	}

	public void setX_descSentido(String x_descSentido) {
		this.x_descSentido = x_descSentido;
	}

	public String getX_nomOrgjurisd() {
		return this.x_nomOrgjurisd;
	}

	public void setX_nomOrgjurisd(String x_nomOrgjurisd) {
		this.x_nomOrgjurisd = x_nomOrgjurisd;
	}

	public List<MovMagistradoSentidofallo> getMovMagistradoSentidofallos() {
		return this.movMagistradoSentidofallos;
	}

	public void setMovMagistradoSentidofallos(List<MovMagistradoSentidofallo> movMagistradoSentidofallos) {
		this.movMagistradoSentidofallos = movMagistradoSentidofallos;
	}

	public MovMagistradoSentidofallo addMovMagistradoSentidofallo(MovMagistradoSentidofallo movMagistradoSentidofallo) {
		getMovMagistradoSentidofallos().add(movMagistradoSentidofallo);
		movMagistradoSentidofallo.setMovSentidofallo(this);

		return movMagistradoSentidofallo;
	}

	public MovMagistradoSentidofallo removeMovMagistradoSentidofallo(MovMagistradoSentidofallo movMagistradoSentidofallo) {
		getMovMagistradoSentidofallos().remove(movMagistradoSentidofallo);
		movMagistradoSentidofallo.setMovSentidofallo(null);

		return movMagistradoSentidofallo;
	}

	public MaeDistritosJudiciale getMaeDistritosJudiciale() {
		return this.maeDistritosJudiciale;
	}

	public void setMaeDistritosJudiciale(MaeDistritosJudiciale maeDistritosJudiciale) {
		this.maeDistritosJudiciale = maeDistritosJudiciale;
	}

	public MovExpediente getMovExpediente() {
		return this.movExpediente;
	}

	public void setMovExpediente(MovExpediente movExpediente) {
		this.movExpediente = movExpediente;
	}

	public MovResumen getMovResumen() {
		return movResumen;
	}

	public void setMovResumen(MovResumen movResumen) {
		this.movResumen = movResumen;
	}

	public String getcTipoRecurso() {
		return cTipoRecurso;
	}

	public void setcTipoRecurso(String cTipoRecurso) {
		this.cTipoRecurso = cTipoRecurso;
	}

	public Integer getnNumeroRecurso() {
		return nNumeroRecurso;
	}

	public void setnNumeroRecurso(Integer nNumeroRecurso) {
		this.nNumeroRecurso = nNumeroRecurso;
	}

	public Long getnAnioRecurso() {
		return nAnioRecurso;
	}

	public void setnAnioRecurso(Long nAnioRecurso) {
		this.nAnioRecurso = nAnioRecurso;
	}
}