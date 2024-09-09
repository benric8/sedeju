package pe.gob.pj.fallo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.fallo.dao.SentidoFalloDao;
import pe.gob.pj.fallo.dto.ExpedienteDTO;
import pe.gob.pj.fallo.dto.MagistradoDTO;
import pe.gob.pj.fallo.dto.SentidoDeFalloDTO;
import pe.gob.pj.fallo.dto.SentidoFalloDTO;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.dto.io.ResumenSentidoFalloDTO;
import pe.gob.pj.fallo.entity.MovMagistradoSentidofallo;
import pe.gob.pj.fallo.entity.MovSentidofallo;
import pe.gob.pj.fallo.entity.MovExpediente;
import pe.gob.pj.fallo.entity.MovResumen;
import pe.gob.pj.fallo.entity.MaeDistritosJudiciale;
import pe.gob.pj.fallo.entity.MaeTipoRecurso;
import pe.gob.pj.fallo.utils.ConstantesSF;
import pe.gob.pj.fallo.utils.ParametrosDeBusqueda;
import pe.gob.pj.fallo.utils.Utils;
import pe.gob.pj.fallo.utils.ValidarUtil;

@Component
public class SentidoFalloDaoImpl implements SentidoFalloDao {

	private static final Logger logger = LogManager.getLogger(SentidoFalloDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionPrincipal")
	private SessionFactory em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SentidoFalloDTO> listaSentidosFallo(String cuo, String xFormato, String cTipoRecurso, Integer numRecurso, Integer anioRecurso, String cOrgano, Integer tipo) throws Exception {
		List<SentidoFalloDTO> lista = new ArrayList<SentidoFalloDTO>();

		try{
			StringBuilder stringQuery = new StringBuilder("SELECT se FROM MovSentidofallo as se");
			stringQuery.append(" INNER JOIN se.movExpediente as ex");
			stringQuery.append(" LEFT OUTER JOIN se.maeDistritosJudiciale as ds");
			stringQuery.append(" WHERE se.lActivo = '1' AND ex.lActivo = '1'");

			TypedQuery<MovSentidofallo> query = buildQuery(tipo, xFormato, cTipoRecurso, numRecurso, anioRecurso, cOrgano, stringQuery);

			query.getResultStream().forEach(instanciaSentidoFallo -> {
				if (instanciaSentidoFallo != null) {
					SentidoFalloDTO dto = mapToSentidoFalloDTO(instanciaSentidoFallo);
					lista.add(dto);
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long consultarExpedienteFallo(ExpedienteDTO expediente) throws Exception {
		Long resultado = null;
		Query query = em.getCurrentSession().createNamedQuery(MovExpediente.BUSCAR_POR_CODIGO);
		query.setParameter(MovExpediente.P_X_FORMATO, expediente.getFormato());
		List<MovExpediente> lista = (List<MovExpediente>) query.list();
		MovExpediente registro;
		if(lista!=null && lista.size()>0) {
			registro = lista.get(0);
			resultado=registro.getNExpediente();
		}
		return resultado;
	}
	
	@Override
	public Long grabarExpediente(ExpedienteDTO expediente, String cuo) throws Exception {
		MovExpediente registro = new MovExpediente();
		registro.setXFormato(expediente.getFormato().trim());
		registro.setCProceso(null);
		registro.setX_descProceso(Utils.validateStr(expediente.getDescproceso()).trim());
		registro.setCEspecialidad(null);
		registro.setX_descEspecialidad(expediente.getDesEspecialidad().trim());
		MaeDistritosJudiciale distrito = new MaeDistritosJudiciale();
		distrito.setC_distritoJudicial(expediente.getCodDistritoJudicial());
		registro.setMaeDistritosJudiciale(distrito);
		registro.setCProvincia(null);
		registro.setCOrgjurisd(expediente.getCodOrganoJuris());
		registro.setX_nomOrgjurisd(expediente.getOrgano().trim());
		registro.setFRegistro(new java.sql.Timestamp((new Date()).getTime()));
		registro.setLActivo(ConstantesSF.ACTIVO);
		registro.setFAud(new java.sql.Timestamp((new Date()).getTime()));
		registro.setBAud(ConstantesSF.Operacion.INSERTAR);
		registro.setCAudUid(ConstantesSF.Operacion.UC_CONEXION);
		registro.setCAudUidred(expediente.getUsuarioOperacion());
		registro.setCAudPc(expediente.getPcOperacion());
		registro.setCAudIp(expediente.getIpOperacion());
		registro.setCAudMcaddr(expediente.getMacOperacion());
		Long pk = (Long) em.getCurrentSession().save(registro);
		logger.info("Entidad {}", registro.toString());
		return pk;
	}
	
	@Override
	public Long grabarSentidoFallo(SentidoDeFalloDTO sentidoFallo, String cuo) throws Exception {
		
		MovSentidofallo registro = new MovSentidofallo();
		MovExpediente movExpediente = new MovExpediente();
		movExpediente.setNExpediente(sentidoFallo.getIdExpediente());
		registro.setMovExpediente(movExpediente);
		registro.setX_descSentido(sentidoFallo.getDescripcion().trim());
		Date fSentido=new SimpleDateFormat("dd/MM/yyyy").parse(sentidoFallo.getFechaDecision());  
		registro.setF_sentidoFallo(new java.sql.Timestamp(fSentido.getTime()));
		registro.setNUsuario(sentidoFallo.getIdUsuario());
		registro.setFRegistro(new java.sql.Timestamp((new Date()).getTime()));
		MaeDistritosJudiciale distrito = new MaeDistritosJudiciale();
		distrito.setC_distritoJudicial(sentidoFallo.getCodDistritoJudicial());
		registro.setMaeDistritosJudiciale(distrito);
		registro.setX_nomOrgjurisd(sentidoFallo.getOrgano().trim());
		registro.setLActivo(ConstantesSF.ACTIVO);
		MovResumen resumen = new MovResumen();
		resumen.setNCarga(sentidoFallo.getnCarga());
		registro.setMovResumen(resumen);

		registro.setFAud(new java.sql.Timestamp((new Date()).getTime()));
		registro.setBAud(ConstantesSF.Operacion.INSERTAR);
		registro.setCAudUid(ConstantesSF.Operacion.UC_CONEXION);
		registro.setCAudUidred(sentidoFallo.getUsuarioOperacion());
		registro.setCAudPc(sentidoFallo.getPcOperacion());
		registro.setCAudIp(sentidoFallo.getIpOperacion());
		registro.setCAudMcaddr(sentidoFallo.getMacOperacion());
		Long pk = (Long) em.getCurrentSession().save(registro);
		logger.info("Entidad {}", registro.toString());
		return pk;
	}
	
	@Override
	public Long grabarMagistrado(MagistradoDTO magistrado, String cuo) throws Exception {
		
		MovMagistradoSentidofallo registro = new MovMagistradoSentidofallo();
		registro.setX_nomMagistrado(magistrado.getNombre());
		registro.setXDni(magistrado.getDni());
		MovSentidofallo movSentidofallo = new MovSentidofallo();
		movSentidofallo.setN_sentidoFallo(magistrado.getIdSentidoFallo());
		registro.setMovSentidofallo(movSentidofallo);
		registro.setLActivo(ConstantesSF.ACTIVO);
		registro.setFAud(new java.sql.Timestamp((new Date()).getTime()));
		registro.setBAud(ConstantesSF.Operacion.INSERTAR);
		registro.setCAudUid(ConstantesSF.Operacion.UC_CONEXION);
		registro.setCAudUidred(magistrado.getUsuarioOperacion());
		registro.setCAudPc(magistrado.getPcOperacion());
		registro.setCAudIp(magistrado.getIpOperacion());
		registro.setCAudMcaddr(magistrado.getMacOperacion());
		Long pk = (Long) em.getCurrentSession().save(registro);
		logger.info("Entidad {}", registro.toString());
		return pk;
	}
	
	@Override
	public Long grabarResumen(ResumenSentidoFalloDTO resumen, String cuo) throws Exception {
		MovResumen registro = new MovResumen();
		registro.setNTotalRegistros(resumen.getTotalRegistros());
		registro.setNTotalRegIncorrectos(resumen.getTotalRegistrosIncorrectos());
		registro.setNTotalRegCorrectos(resumen.getTotalRegistrosCorrectos());
		//Date fCarga=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(resumen.getFecha());  
		registro.setFCarga(new java.sql.Timestamp((new Date()).getTime()));
		registro.setLEstado(resumen.getEstado());
		registro.setNUsuario(resumen.getUsuario());
		registro.setXIp(resumen.getNroIP());
		registro.setFAud(new java.sql.Timestamp((new Date()).getTime()));
		registro.setBAud(ConstantesSF.Operacion.INSERTAR);
		registro.setCAudUid(ConstantesSF.Operacion.UC_CONEXION);
		registro.setCAudUidred(resumen.getUsuarioOperacion());
		registro.setCAudPc(resumen.getPcOperacion());
		registro.setCAudIp(resumen.getIpOperacion());
		registro.setCAudMcaddr(resumen.getMacOperacion());
		Long pk = (Long) em.getCurrentSession().save(registro);
		logger.info("Entidad {}", registro.toString());
		return pk;
	}
	
	@Override
	public List<ResumenSentidoFalloDTO> consultarResumen(Map<String, Object> params, String cuo) throws Exception {
		
		logger.info(cuo+" SentidoFalloDaoImpl.consultarResumen ");
		List<ResumenSentidoFalloDTO> resultado = new ArrayList<ResumenSentidoFalloDTO>();
		List<Entry<String, Object>> listaParams = new ArrayList<Entry<String, Object>>();
		StringBuilder hql = new StringBuilder();
		hql.append("select o from MovResumen as o ");
		for (Map.Entry<String, Object> map : params.entrySet()) {
			if (ValidarUtil.validaValor(map, ParametrosDeBusqueda.RESU_USUARIO)) {
				hql.append((hql.indexOf(" WHERE") > 0) ? " AND" : " WHERE");
				hql.append(" o.nUsuario= :").append(ParametrosDeBusqueda.RESU_USUARIO);
				listaParams.add(map);				
			} else if (ValidarUtil.validaValor(map, ParametrosDeBusqueda.RESU_ID)) {
				hql.append((hql.indexOf(" WHERE") > 0) ? " AND" : " WHERE");
				hql.append(" o.nCarga= :").append(ParametrosDeBusqueda.RESU_ID);
				listaParams.add(map);
			} else if (ValidarUtil.validaValor(map, ParametrosDeBusqueda.RESU_ESTADO)) {
				hql.append((hql.indexOf(" WHERE") > 0) ? " AND" : " WHERE");
				hql.append(" o.lEstado= :").append(ParametrosDeBusqueda.RESU_ESTADO);
				listaParams.add(map);	
			}  
		}
		Query<MovResumen> query = em.getCurrentSession().createQuery(hql.toString(), MovResumen.class);
		for (Map.Entry<String, Object> map : listaParams) {
			query.setParameter( map.getKey(), map.getValue() );
		}
		List<MovResumen> list = query.getResultList();
		for (MovResumen res : list) {
			ResumenSentidoFalloDTO resumenDto = new ResumenSentidoFalloDTO();
			resumenDto.setnCarga(res.getNCarga());
			resumenDto.setTotalRegistros(res.getNTotalRegistros());
			resumenDto.setTotalRegistrosIncorrectos(res.getNTotalRegIncorrectos());
			resumenDto.setTotalRegistrosCorrectos(res.getNTotalRegCorrectos());
			resumenDto.setFecha(Utils.validateStr(res.getFCarga()));
			resumenDto.setEstado(res.getLEstado());
			resumenDto.setUsuario(res.getNUsuario());
			resumenDto.setNroIP(res.getXIp());
			resumenDto.setFechaOperacion(new Date(res.getFAud().getTime()));
			resumenDto.setUsuarioRedOperacion(res.getCAudUidred());
			resumenDto.setUsuarioOperacion(res.getCAudUid());
			resumenDto.setTipoOperacion(res.getBAud());
			resumenDto.setIpOperacion(res.getCAudUid());
			resumenDto.setMacOperacion(res.getCAudMcaddr());
			resumenDto.setPcOperacion(res.getCAudPc());
			resultado.add(resumenDto);
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int validarSentidoFallos(String formato, String fecha, String organo, String cuo) throws Exception {
		int cantRegistros=0;
		try {
			SimpleDateFormat conversor = new SimpleDateFormat("dd/MM/yyyy");
			Query<MovSentidofallo> query = em.getCurrentSession().createNamedQuery(MovSentidofallo.VALIDAR_SENTIDO_FALLOS);
			query.setParameter(MovSentidofallo.P_X_FORMATO_SIN, formato);
			query.setParameter(MovSentidofallo.P_ORGANO, organo);
			List<MovSentidofallo> lista = query.getResultList();
			String temp;
			for (MovSentidofallo sentido: lista) {
				temp = conversor.format(new Date(sentido.getF_sentidoFallo().getTime()));
				if(fecha.equals(temp)) { 
					cantRegistros++;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cantRegistros;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SentidoFalloDTO> detallarSentidosFallo(Long nCarga, String cuo) throws Exception {
		List<SentidoFalloDTO> lista = new ArrayList<SentidoFalloDTO>();
		Query<MovSentidofallo> query = em.getCurrentSession().createNamedQuery(MovSentidofallo.DETALLAR_SENTIDO_FALLOS);
		query.setParameter(MovSentidofallo.P_CARGA, nCarga);
		List<MovSentidofallo> resultado = query.getResultList();
		for(MovSentidofallo o : resultado) {
			SentidoFalloDTO s = new SentidoFalloDTO();
			s.setnExpediente(o.getMovExpediente().getNExpediente());
			s.setfRegistro(o.getFRegistro());
			s.setxFormato(o.getMovExpediente().getXFormato());
			s.setfSentidoFallo(o.getF_sentidoFallo());
			if(o.getMovExpediente().getMaeDistritosJudiciale()!=null) 
				s.setxDescDistrito(o.getMovExpediente().getMaeDistritosJudiciale().getX_nomDistrito());	
			else 
				s.setxDescDistrito("");
			s.setxDescrEspecialidad(o.getMovExpediente().getX_descEspecialidad());
			s.setnSentidoFallo(o.getN_sentidoFallo());
			s.setxDescSentido(o.getX_descSentido());
			s.setxNomOrgJurisd(o.getMovExpediente().getX_nomOrgjurisd());
			s.setxNomDistrito(o.getMaeDistritosJudiciale().getX_nomDistrito());
			s.setxDescProceso(o.getMovExpediente().getX_descProceso());
			
			if(o.getMovMagistradoSentidofallos() != null) {
				StringBuilder mag = new StringBuilder();
				for (MovMagistradoSentidofallo m : o.getMovMagistradoSentidofallos()) {
					if(mag.toString().length()> 0) {
						mag.append(", ");
					}
					mag.append(m.getX_nomMagistrado());
				}
				s.setxNomMagistrado(mag.toString());
			}
			lista.add(s);				
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComboDTO> recuperarTiposDeRecursos(String cuo) throws Exception {
		List<ComboDTO> lista = new ArrayList<ComboDTO>();
		try {			
			Query<MaeTipoRecurso> query = em.getCurrentSession().createNamedQuery(MaeTipoRecurso.RECUPERAR_TODOS);
			List<MaeTipoRecurso> resultado = query.getResultList();
			for (MaeTipoRecurso r : resultado) {
				ComboDTO c = new ComboDTO();
				c.setCodigo(r.getcTipoRecurso());
				c.setDescripcion(r.getxTipoRecurso());
				lista.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(cuo.concat("Error al recuperar tipos de recursos"));
			return null;
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComboDTO> recuperarOrganosJurisdiccionales(String cuo) throws Exception {
		List<ComboDTO> lista = new ArrayList<ComboDTO>();
		try {			
			Query<Object[]> query = em.getCurrentSession().createNamedQuery(MovSentidofallo.RECUPERA_JUZGADOS);
			List<Object[]> resultado = query.getResultList();
			for (Object[] r : resultado) {
				ComboDTO c = new ComboDTO();	
				c.setCodigo(r[0].toString());
				c.setDescripcion(r[1].toString());
				lista.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(cuo.concat("Error al recuperar tipos de recursos"));
			return null;
		}
		return lista;
	}

	private TypedQuery<MovSentidofallo> buildQuery(Integer tipo, String xFormato, String cTipoRecurso, Integer numRecurso, Integer anioRecurso, String cOrgano, StringBuilder stringQuery) {
		TypedQuery<MovSentidofallo> query;

		if (tipo == 0) {
			stringQuery.append(" AND ex.xFormato = :"+MovSentidofallo.P_X_FORMATO);
			query = em.getCurrentSession().createQuery(stringQuery.toString(), MovSentidofallo.class);
			query.setParameter(MovSentidofallo.P_X_FORMATO, xFormato);
		} else {
			stringQuery.append(" AND se.cTipoRecurso = :"+MovSentidofallo.P_C_TIPO_RECURSO);
			stringQuery.append(" AND se.nNumeroRecurso = :"+MovSentidofallo.P_N_NUM_RECURSO);
			stringQuery.append(" AND se.nAnioRecurso = :"+MovSentidofallo.P_N_ANIO_RECURSO);
			stringQuery.append(" AND se.cOrgjurisd = :"+MovSentidofallo.P_C_ORGANO);
			query = em.getCurrentSession().createQuery(stringQuery.toString(), MovSentidofallo.class);
			query.setParameter(MovSentidofallo.P_C_TIPO_RECURSO, cTipoRecurso);
			query.setParameter(MovSentidofallo.P_N_NUM_RECURSO, numRecurso);
			query.setParameter(MovSentidofallo.P_N_ANIO_RECURSO, anioRecurso);
			query.setParameter(MovSentidofallo.P_C_ORGANO, cOrgano);
		}

		return query;
	}


	private SentidoFalloDTO mapToSentidoFalloDTO(MovSentidofallo instanciaSentidoFallo) {
		SentidoFalloDTO dto = new SentidoFalloDTO();

		dto.setnExpediente(instanciaSentidoFallo.getMovExpediente().getNExpediente());
		dto.setxFormato(instanciaSentidoFallo.getMovExpediente().getXFormato());
		dto.setxNomDistrito(instanciaSentidoFallo.getMaeDistritosJudiciale().getX_nomDistrito());
		dto.setxDescDistrito(instanciaSentidoFallo.getMaeDistritosJudiciale().getX_nomDistrito());
		dto.setxDescrEspecialidad(instanciaSentidoFallo.getMovExpediente().getX_descEspecialidad());
		dto.setnSentidoFallo(instanciaSentidoFallo.getN_sentidoFallo());
		dto.setxDescSentido(instanciaSentidoFallo.getX_descSentido());
		dto.setfSentidoFallo(instanciaSentidoFallo.getF_sentidoFallo());
		dto.setfRegistro(instanciaSentidoFallo.getFRegistro());
		dto.setxNomOrgJurisd(instanciaSentidoFallo.getX_nomOrgjurisd());
		dto.setnSentidoFalloMagistrado(0);
		dto.setnMagistradoSentido(0);

		// Obtener magistrados
		String magistrados = instanciaSentidoFallo.getMovMagistradoSentidofallos()
				.stream().map(MovMagistradoSentidofallo::getX_nomMagistrado)
				.collect(Collectors.joining(", "));
		dto.setxNomMagistrado(magistrados);

		dto.setxDescProceso(instanciaSentidoFallo.getMovExpediente().getX_descProceso());

		// Establecer el recurso
		dto.setxDescRecurso(obtenerDescripcionRecurso(instanciaSentidoFallo.getcTipoRecurso()));

		dto.setnNumRecurso(instanciaSentidoFallo.getnNumeroRecurso());
		dto.setnAnioRecurso(instanciaSentidoFallo.getnAnioRecurso() != null ? instanciaSentidoFallo.getnAnioRecurso().intValue() : null);
		dto.setfFechaVisto(instanciaSentidoFallo.getfFechaVisto());
		dto.setcDistrito(instanciaSentidoFallo.getMaeDistritosJudiciale().getC_distritoJudicial());

		return dto;
	}


	private String obtenerDescripcionRecurso(String cTipoRecurso) {
		StringBuilder sbTipoRecurso = new StringBuilder("SELECT m FROM MaeTipoRecurso m WHERE m.cTipoRecurso = :c_tipo_recurso AND m.lActivo = '1'");
		TypedQuery<MaeTipoRecurso> qTipoRecurso = em.getCurrentSession().createQuery(sbTipoRecurso.toString(), MaeTipoRecurso.class);
		qTipoRecurso.setParameter("c_tipo_recurso", cTipoRecurso);

		MaeTipoRecurso maeTipoRecurso = qTipoRecurso.getResultStream().findFirst().orElse(null);
		return maeTipoRecurso != null ? maeTipoRecurso.getxTipoRecurso() : "";
	}

}
