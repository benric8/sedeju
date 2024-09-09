package pe.gob.pj.fallo.service;

import java.util.List;
import java.util.Map;

import pe.gob.pj.fallo.dto.SentidoFalloDTO;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.dto.io.ResumenSentidoFalloDTO;

public interface SentidoFalloService {

	public List<SentidoFalloDTO> listaSentidosFallo(String cuo, String xFormato, String cTipoRecurso, Integer numRecurso, Integer anioRecurso, String cOrgano, Integer tipo) throws Exception;

	public int grabarSentidoFallo(ResumenSentidoFalloDTO resumen, String cuo) throws Exception;

	public List<ResumenSentidoFalloDTO> consultarResumen(Map<String, Object> params, String cuo) throws Exception;

	public int validarSentidoFallos(String formato, String fecha, String organo, String cuo) throws Exception;

	public List<SentidoFalloDTO> detallarSentidosFallo(Long nCarga, String cuo) throws Exception;
	
	public List<ComboDTO> recuperarTiposDeRecursos(String cuo) throws Exception;
	
	public List<ComboDTO> recuperarOrganosJurisdiccionales(String cuo) throws Exception;
	
}
