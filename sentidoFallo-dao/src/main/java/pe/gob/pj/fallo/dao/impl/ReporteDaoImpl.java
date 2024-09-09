package pe.gob.pj.fallo.dao.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.StoredProcedureQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.fallo.dao.ReporteDao;
import pe.gob.pj.fallo.dto.ReporteDTO;
import pe.gob.pj.fallo.dto.ReporteDetalladoDTO;
import pe.gob.pj.fallo.entity.MaeUsuario;
import pe.gob.pj.fallo.entity.MovResumen;
import pe.gob.pj.fallo.entity.MovSentidofallo;

@Component("reporteDao")
public class ReporteDaoImpl implements ReporteDao, Serializable {

	private static final Logger logger = LogManager.getLogger(ReporteDaoImpl.class);
	private static final long serialVersionUID = 1L;	
	
	@Autowired
	@Qualifier("sessionPrincipal")
	private SessionFactory em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteDTO> recuperaDatosReporte(Timestamp fDesde, Timestamp fHasta, String cDistrito, String cOrgano, String cuo) throws Exception {
		List<ReporteDTO> lista = new ArrayList<ReporteDTO>();
		try {
			StoredProcedureQuery denunciaQuery = em.getCurrentSession().createNamedStoredProcedureQuery(MovResumen.RECUPERA_INFO_REPORTE_GENERICO);
			denunciaQuery.setParameter(MovResumen.P_FECHA_DESDE, fDesde);
			denunciaQuery.setParameter(MovResumen.P_FECHA_HASTA, fHasta);
			denunciaQuery.setParameter(MovResumen.P_C_DISTRITO, cDistrito);
			denunciaQuery.setParameter(MovResumen.P_C_ORGANO, cOrgano);
			denunciaQuery.execute();
			lista = denunciaQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(cuo.concat(" Error al recuperar datos para reporte "+e.getMessage()));
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteDetalladoDTO> recuperaDatosDetalladoReporte(Timestamp fDesde, Timestamp fHasta, String cDistrito, String cOrgano, String cuo) throws Exception {
		List<ReporteDetalladoDTO> lista = new ArrayList<ReporteDetalladoDTO>();
		try {
			StoredProcedureQuery denunciaQuery = em.getCurrentSession().createNamedStoredProcedureQuery(MovSentidofallo.RECUPERA_INFO_REPORTE_DETALLADO);
			denunciaQuery.setParameter(MovSentidofallo.P_FECHA_DESDE, fDesde);
			denunciaQuery.setParameter(MovSentidofallo.P_FECHA_HASTA, fHasta);
			denunciaQuery.setParameter(MovSentidofallo.P_C_DISTRITO, cDistrito);
			denunciaQuery.setParameter(MovSentidofallo.P_C_ORGANO, cOrgano);
			denunciaQuery.execute();
			lista = denunciaQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(cuo.concat(" Error al recuperar datos para reporte "+e.getMessage()));
		}
		return lista;
	}

	@Override
	public List<String> recuperarOrganosJuris(String cDistrito, String cuo) throws Exception {
		List<String> lista = new ArrayList<String>();
		try {
			@SuppressWarnings("unchecked")
			Query<String> denunciaQuery = em.getCurrentSession().createNamedQuery(MovSentidofallo.RECUPERA_JUZGADOS_TODOS);
			denunciaQuery.setParameter(MaeUsuario.C_DISTRITO, cDistrito);
			lista = denunciaQuery.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(cuo.concat(" Error al recuperar lista de organos "+e.getMessage()));
		}
		return lista;
	}

}
