package pe.gob.pj.fallo.service;

import java.sql.Timestamp;
import java.util.List;

import pe.gob.pj.fallo.dto.ReporteDTO;
import pe.gob.pj.fallo.dto.ReporteDetalladoDTO;

public interface ReporteService {
	
	public List<ReporteDTO> recuperaDatosReporte(Timestamp fDesde, Timestamp fHasta, String cDistrito, String cOrgano, String cuo) throws Exception;
	
	public List<String> recuperarOrganosJuris(String cDistrito, String cuo) throws Exception;
	
	public List<ReporteDetalladoDTO> recuperaDatosDetalladoReporte(Timestamp fDesde, Timestamp fHasta, String cDistrito, String cOrgano, String cuo) throws Exception;

}
