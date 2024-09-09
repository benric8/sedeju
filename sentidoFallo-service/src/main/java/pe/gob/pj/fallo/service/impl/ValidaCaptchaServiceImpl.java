package pe.gob.pj.fallo.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import pe.gob.pj.fallo.dto.CaptchaValidDTO;
import pe.gob.pj.fallo.service.ValidCaptchaService;
import pe.gob.pj.fallo.utils.ConfiguracionPropiedades;
import pe.gob.pj.fallo.utils.Constants;
import pe.gob.pj.fallo.utils.Propiedades;

/**
 * Servicio usado para validar captcha con Google
 * @author Madiazr
 * @Date 16/01/2021
 */
@Service("validCaptchaService")
public class ValidaCaptchaServiceImpl implements ValidCaptchaService, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean validaCapctcha(String cuo, String token, String remoteIp) throws Exception {
		try {
			System.out.println("*****************************************");
			RestTemplate plantilla = new RestTemplate();			
			String URL=ConfiguracionPropiedades.getInstance().getProperty(Propiedades.CAPTCHA_URL);
			String TOKEN=ConfiguracionPropiedades.getInstance().getProperty(Propiedades.CAPTCHA_TOKEN);
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(URL)
		                .queryParam(Constants.Captcha.P_SECRET_KEY, TOKEN)
		                .queryParam(Constants.Captcha.P_RESPONSE, token)
		                .queryParam(Constants.Captcha.P_REMOTEIP, remoteIp).build();
			CaptchaValidDTO resultado = plantilla.getForObject(builder.toUriString(), CaptchaValidDTO.class);
			if(resultado.getSuccess().equals("true")) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}	

}
