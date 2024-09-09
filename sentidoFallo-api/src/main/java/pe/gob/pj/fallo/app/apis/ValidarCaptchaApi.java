package pe.gob.pj.fallo.app.apis;

import java.io.Serializable;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.fallo.dto.EncriptaClaveDTO;
import pe.gob.pj.fallo.service.ValidCaptchaService;
import pe.gob.pj.fallo.utils.EncryptUtils;

/**
 * Api Rest que sirve para validar Captcha
 * @author madiazr
 * @date 19/01/2021
 */
@RestController
public class ValidarCaptchaApi implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ValidCaptchaService validCaptchaService;

	/**
	 * Método que sirve para validar captcha de google
	 * @param token token generado en la web
	 * @param ipRemota ip remota que consulta operación
	 * @param cuo código unico de log
	 * @return
	 */
	@RequestMapping(value = "validarCaptcha", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> validarCaptcha(@RequestParam String token, @RequestAttribute String ipRemota,  @RequestAttribute String cuo) {
		try {
			if(validCaptchaService.validaCapctcha(cuo, token, ipRemota)) {
				return new ResponseEntity<String>("{\"validado\": true }", HttpStatus.OK); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"validado\": false }", HttpStatus.OK);
	}
	
	@RequestMapping(value = "encriptarClaves", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EncriptaClaveDTO> genrarClave(@RequestParam String usuario, @RequestParam String clave, @RequestAttribute String cuo) {
		EncriptaClaveDTO d = new EncriptaClaveDTO();
		try {
			d.setUsuario(EncryptUtils.encryptPastFrass( usuario ));
			String encodedString = Base64.getEncoder().encodeToString(clave.getBytes());
			d.setClave(encodedString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<EncriptaClaveDTO>(d, HttpStatus.OK);
	}

}
