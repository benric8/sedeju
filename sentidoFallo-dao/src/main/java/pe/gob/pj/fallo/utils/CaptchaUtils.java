package pe.gob.pj.fallo.utils;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.log4j.Log4j2;
import pe.gob.pj.fallo.dto.CaptchaValidDTO;

@Log4j2
public class CaptchaUtils {
	
	private static String CAPTCHA_ACTIVO = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.CAPTCHA_ACTIVO);
	public static final boolean validCaptcha(String token, String remoteIp, String cuo) {
		try {
			log.info("*****************************************"+remoteIp);
			if(CAPTCHA_ACTIVO.equals("N")) {
				return Boolean.TRUE;
			}else {
				String URL=ConfiguracionPropiedades.getInstance().getProperty(Propiedades.CAPTCHA_URL);
				String TOKEN=ConfiguracionPropiedades.getInstance().getProperty(Propiedades.CAPTCHA_TOKEN);
				RestTemplate plantilla = new RestTemplate();			
				UriComponents builder = UriComponentsBuilder.fromHttpUrl(URL)
			                .queryParam(Constants.Captcha.P_SECRET_KEY, TOKEN)
			                .queryParam(Constants.Captcha.P_RESPONSE, token)
			                .queryParam(Constants.Captcha.P_REMOTEIP, remoteIp).build();
				log.info(builder.toUriString());
				CaptchaValidDTO resultado = plantilla.postForObject(builder.toUriString(), null, CaptchaValidDTO.class);
				log.info(resultado.getSuccess());
				if(resultado.getSuccess().equals("true")) {
					return Boolean.TRUE;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

}
