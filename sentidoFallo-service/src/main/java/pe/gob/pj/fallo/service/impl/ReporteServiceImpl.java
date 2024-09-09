package pe.gob.pj.fallo.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.fallo.dao.ReporteDao;
import pe.gob.pj.fallo.dto.ReporteDTO;
import pe.gob.pj.fallo.dto.ReporteDetalladoDTO;
import pe.gob.pj.fallo.service.ReporteService;

@Service("reporteService")
public class ReporteServiceImpl implements ReporteService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReporteDao reporteDao;

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, timeout = 500)
	public List<ReporteDTO> recuperaDatosReporte(Timestamp fDesde, Timestamp fHasta, String cDistrito, String cOrgano, String cuo) throws Exception {
		return reporteDao.recuperaDatosReporte(fDesde, fHasta, cDistrito, cOrgano, cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, timeout = 60)
	public List<String> recuperarOrganosJuris(String cDistrito, String cuo) throws Exception {
		return reporteDao.recuperarOrganosJuris(cDistrito, cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, timeout = 240)
	public List<ReporteDetalladoDTO> recuperaDatosDetalladoReporte(Timestamp fDesde, Timestamp fHasta, String cDistrito, String cOrgano, String cuo) throws Exception {
		return reporteDao.recuperaDatosDetalladoReporte(fDesde, fHasta, cDistrito, cOrgano, cuo);
	}

	
}
