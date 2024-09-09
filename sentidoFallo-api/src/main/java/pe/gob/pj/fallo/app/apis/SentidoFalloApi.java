package pe.gob.pj.fallo.app.apis;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.fallo.dto.ResponseComboDTO;
import pe.gob.pj.fallo.dto.ResponseSentidoFalloDTO;
import pe.gob.pj.fallo.dto.SentidoFalloDTO;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.service.SentidoFalloService;
import pe.gob.pj.fallo.utils.CaptchaUtils;

/**
 * Api Rest que sirve recuperar lista de sentidos fallo
 * @author madiazr
 * @date 19/01/2021
 */
@RestController
public class SentidoFalloApi implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SentidoFalloService sentidoFalloService;

	@RequestMapping(value = "buscarSentidosYFallos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseSentidoFalloDTO> buscarSentidosFallo(@RequestParam String xFormato, @RequestParam String cTipoRecurso, @RequestParam Integer numRecurso, @RequestParam Integer anioRecurso, @RequestParam String cOrgano, @RequestParam Integer tipo, @RequestParam String token, @RequestAttribute String ipRemota, @RequestAttribute String cuo) {
		ResponseSentidoFalloDTO response = new ResponseSentidoFalloDTO();
		try {
			if(CaptchaUtils.validCaptcha(token, ipRemota, cuo)) {
				List<SentidoFalloDTO> lista = sentidoFalloService.listaSentidosFallo(cuo, xFormato, cTipoRecurso, numRecurso, anioRecurso, cOrgano, tipo);
				if(lista.size() > 0) {
					response.setCodigo("200");
					response.setData(lista);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					response.setCodigo("404");
					response.setData(lista);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}				
			} else {
				response.setCodigo("400");
				response.setData(null);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@RequestMapping(value = "recuperarTiposRecurso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseComboDTO> recuperaListaTiposRecurso(@RequestAttribute String cuo) {
		ResponseComboDTO res = new ResponseComboDTO();
		try {
			List<ComboDTO> lista =sentidoFalloService.recuperarTiposDeRecursos(cuo);
			res.setCodigo("200");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo("500");
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseComboDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "recuperaJuzgados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseComboDTO> recuperaJuzgados(@RequestAttribute String cuo) {
		ResponseComboDTO res = new ResponseComboDTO();
		try {
			List<ComboDTO> lista =sentidoFalloService.recuperarOrganosJurisdiccionales(cuo);
			lista = lista.stream().sorted(Comparator.comparing( ComboDTO::getDescripcion)).collect(Collectors.toList());
			res.setCodigo("200");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo("500");
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseComboDTO>(res, HttpStatus.OK);
	}
}
