package pe.gob.pj.fallo.dao;

import java.util.List;
import java.util.Map;

import pe.gob.pj.fallo.dto.ExpedienteDTO;
import pe.gob.pj.fallo.dto.MagistradoDTO;
import pe.gob.pj.fallo.dto.SentidoDeFalloDTO;
import pe.gob.pj.fallo.dto.SentidoFalloDTO;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.dto.io.ResumenSentidoFalloDTO;

public interface SentidoFalloDao {

	public List<SentidoFalloDTO> listaSentidosFallo(String cuo, String xFormato, String cTipoRecurso, Integer numRecurso, Integer anioRecurso, String cOrgano, Integer tipo) throws Exception;

	public Long consultarExpedienteFallo(ExpedienteDTO expediente) throws Exception;

	public Long grabarExpediente(ExpedienteDTO expediente, String cuo) throws Exception;

	public Long grabarSentidoFallo(SentidoDeFalloDTO sentidoFallo, String cuo) throws Exception;

	public Long grabarMagistrado(MagistradoDTO magistrado, String cuo) throws Exception;

	public Long grabarResumen(ResumenSentidoFalloDTO resumen, String cuo) throws Exception;

	public List<ResumenSentidoFalloDTO> consultarResumen(Map<String, Object> params, String cuo) throws Exception;

	public int validarSentidoFallos(String formato, String fecha, String organo, String cuo) throws Exception;

	public List<SentidoFalloDTO> detallarSentidosFallo(Long nCarga, String cuo) throws Exception;

	public List<ComboDTO> recuperarTiposDeRecursos(String cuo) throws Exception;
	
	public List<ComboDTO> recuperarOrganosJurisdiccionales(String cuo) throws Exception;
	
}
