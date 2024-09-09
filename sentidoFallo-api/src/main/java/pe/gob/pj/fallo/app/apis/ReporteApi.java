package pe.gob.pj.fallo.app.apis;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.fallo.dto.ReporteDTO;
import pe.gob.pj.fallo.dto.ReporteDetalladoDTO;
import pe.gob.pj.fallo.dto.ResponseOrganosDTO;
import pe.gob.pj.fallo.dto.ResponseReporteDTO;
import pe.gob.pj.fallo.dto.ResponseReporteDetalladoDTO;
import pe.gob.pj.fallo.service.ReporteService;

@RestController
public class ReporteApi implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ReporteService reporteService;	
	
	@RequestMapping(value = "/admin/reporte/reporte-resumen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseReporteDTO> reporteRecumen(@RequestParam Timestamp fDesde, @RequestParam Timestamp fHasta, @RequestParam String cDistrito, @RequestParam String cOrgano, @RequestAttribute String cuo) {
		ResponseReporteDTO response = new ResponseReporteDTO();
		try {
			List<ReporteDTO> lista = reporteService.recuperaDatosReporte(fDesde, fHasta, cDistrito, cOrgano, cuo);
			if(lista.size() > 0) {
				response.setCodigo("200");				
				response.setData(lista);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setCodigo("404");
				response.setDescripcion("No se encontraron registros");
				response.setData(lista);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}				
		} catch (Exception e) {
			response.setCodigo("500");
			response.setDescripcion(e.getMessage());
			response.setData(null);
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/reporte/reporte-generico", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseReporteDetalladoDTO> reporteDetalladoPublicacion(@RequestParam Timestamp fDesde, @RequestParam Timestamp fHasta, @RequestParam String cDistrito, @RequestParam String cOrgano, @RequestAttribute String cuo) {
		ResponseReporteDetalladoDTO response = new ResponseReporteDetalladoDTO();
		try {
			List<ReporteDetalladoDTO> lista = reporteService.recuperaDatosDetalladoReporte(fDesde, fHasta, cDistrito, cOrgano, cuo);
			if(lista.size() > 0) {
				response.setCodigo("200");				
				response.setData(lista);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setCodigo("404");
				response.setDescripcion("No se encontraron registros");
				response.setData(lista);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}				
		} catch (Exception e) {
			response.setCodigo("500");
			response.setDescripcion(e.getMessage());
			response.setData(null);
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/listaOrganos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseOrganosDTO> listaOrganos(@RequestParam String cDistrito, @RequestAttribute String cuo) {
		ResponseOrganosDTO res = new ResponseOrganosDTO();
		try {
			res.setData(reporteService.recuperarOrganosJuris(cDistrito, cuo));
			res.setCodigo("200");
			res.setDescripcion("Lista recuperada con exito");
		} catch (Exception e) {
			res.setCodigo("500");
			res.setDescripcion(e.getMessage());
		}
		return new ResponseEntity<ResponseOrganosDTO>(res, HttpStatus.OK);
	}
}
