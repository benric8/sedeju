package pe.gob.pj.fallo.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.fallo.dao.SentidoFalloDao;
import pe.gob.pj.fallo.dto.ExpedienteDTO;
import pe.gob.pj.fallo.dto.MagistradoDTO;
import pe.gob.pj.fallo.dto.SentidoDeFalloDTO;
import pe.gob.pj.fallo.dto.SentidoFalloDTO;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.dto.io.ResumenSentidoFalloDTO;
import pe.gob.pj.fallo.service.SentidoFalloService;

@Service("sentidoFalloService")
public class SentidoFalloServiceImpl implements SentidoFalloService, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SentidoFalloServiceImpl.class);
	
	@Autowired
	private SentidoFalloDao sentidoFalloDao;

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, timeout = 180)
	public List<SentidoFalloDTO> listaSentidosFallo(String cuo, String xFormato, String cTipoRecurso, Integer numRecurso, Integer anioRecurso, String cOrgano,Integer tipo) throws Exception {
		logger.info(cuo.concat("Recuperando información de sentidos de repositorio...."));
		return sentidoFalloDao.listaSentidosFallo(cuo, xFormato, cTipoRecurso, numRecurso, anioRecurso, cOrgano, tipo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = { Exception.class, SQLException.class})
	public int grabarSentidoFallo(ResumenSentidoFalloDTO resumen, String cuo) throws Exception {
		int resultado=0;
		Long pkResumen=null, pkExpediente=null, pkSentidoFallo=null;
		pkResumen= sentidoFalloDao.grabarResumen(resumen, cuo);
		if(pkResumen==null) return resultado;
		List<ExpedienteDTO> expedientes = resumen.getListaExpedientes();
		for(ExpedienteDTO expediente: expedientes) {
			pkExpediente = sentidoFalloDao.consultarExpedienteFallo(expediente);	
			if (pkExpediente==null) {
				pkExpediente= sentidoFalloDao.grabarExpediente(expediente, cuo);
				if(pkExpediente==null) return resultado;
			}
			for (SentidoDeFalloDTO sentidoFallo : expediente.getSentidoFallo()) {
				sentidoFallo.setIdExpediente(pkExpediente);
				sentidoFallo.setnCarga(pkResumen);
				pkSentidoFallo= sentidoFalloDao.grabarSentidoFallo(sentidoFallo, cuo);	
				if(pkSentidoFallo==null) return resultado;
				for (MagistradoDTO magistrado: sentidoFallo.getListaMagistrados()) {
					 magistrado.setIdSentidoFallo(pkSentidoFallo);
					 sentidoFalloDao.grabarMagistrado(magistrado, cuo);	
				}
			}
		}
		resultado=1;
		return resultado;
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public List<ResumenSentidoFalloDTO> consultarResumen(Map<String, Object> params, String cuo) throws Exception {
		return sentidoFalloDao.consultarResumen(params, cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public int validarSentidoFallos(String formato, String fecha, String organo, String cuo) throws Exception {
		return sentidoFalloDao.validarSentidoFallos(formato, fecha, organo, cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public List<SentidoFalloDTO> detallarSentidosFallo(Long nCarga, String cuo) throws Exception {
		return sentidoFalloDao.detallarSentidosFallo(nCarga, cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, timeout = 180)
	public List<ComboDTO> recuperarTiposDeRecursos(String cuo) throws Exception {		
		return sentidoFalloDao.recuperarTiposDeRecursos(cuo);
	}	
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, timeout = 180)
	public List<ComboDTO> recuperarOrganosJurisdiccionales(String cuo) throws Exception{
		return sentidoFalloDao.recuperarOrganosJurisdiccionales(cuo);
	}

}