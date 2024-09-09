package pe.gob.pj.fallo.app.apis;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pe.gob.pj.fallo.bean.MensajeBean;
import pe.gob.pj.fallo.bean.RespuestaBean;
import pe.gob.pj.fallo.bean.UsuarioSFBean;
import pe.gob.pj.fallo.dto.ResponseLoginDTO;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;
import pe.gob.pj.fallo.dto.io.LoginDTO;
import pe.gob.pj.fallo.service.EmailService;
import pe.gob.pj.fallo.service.SegUsuarioService;
import pe.gob.pj.fallo.utils.CaptchaUtils;
import pe.gob.pj.fallo.utils.ConfiguracionPropiedades;
import pe.gob.pj.fallo.utils.ConstantesSF;
import pe.gob.pj.fallo.utils.CryptoUtil;
import pe.gob.pj.fallo.utils.EncryptUtils;
import pe.gob.pj.fallo.utils.Propiedades;
import pe.gob.pj.fallo.utils.SecurityConstants;
import pe.gob.pj.fallo.utils.ValidarUtil;


@RestController
public class SegUsuarioApi implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SegUsuarioApi.class);
	
	@Autowired
	private SegUsuarioService segUsuarioService;
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * Método que sirver para autenticar usuarios de panel de administración
	 * @param login información de login
	 * @param ipRemota direccion ip desde donde realiza la operación
	 * @param cuo código unico de log
	 * @return
	 */
	@RequestMapping(value ="/seguridad/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody LoginDTO login, @RequestAttribute String ipRemota, @RequestAttribute String cuo) {		
		ResponseLoginDTO res = new ResponseLoginDTO();
		try {
			logger.info(cuo.concat(" Recuperando atenticacion para panel de administración"));
			res.setCodigo("500");
			if(CaptchaUtils.validCaptcha(login.getCaptcha(), ipRemota, cuo)) {
				UsuarioSFBean usuarioSFBean = segUsuarioService.validarAcceso(login.getUsuario(), CryptoUtil.encriptar(login.getClave()), cuo);
				if(usuarioSFBean != null) {
					byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
					String tokenResult = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(usuarioSFBean.getcUsuario())
							.setExpiration(new Date(System.currentTimeMillis() + 300000))
							.claim("usuario", usuarioSFBean)
							.claim("remoteIp", ipRemota)
							.claim("numero", 1)
							.compact();
					res.setCodigo("200");
					res.setToken(tokenResult);
					return new ResponseEntity<>(res, HttpStatus.OK);
				} 	
			} else {
				res.setCodigo("400");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Error al autenticar usuario, en panel de administracion");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}	
	
	/**
	 * Método que sirve para extender tiempo de expiración de TOKEN genrando uno nuevo
	 * @param token token original de operacón
	 * @param ipRemota ip remota que consulta
	 * @param tokenAdmin token de login de administración
	 * @param validTokenAdmin confirma se valid Token de Admin
	 * @param cuo código unico de log
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/seguridad/refresh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseLoginDTO> refreshToken(@RequestParam String token, @RequestAttribute String ipRemota, @RequestAttribute String tokenAdmin,@RequestAttribute boolean validTokenAdmin, @RequestAttribute String cuo){
		ResponseLoginDTO res= new ResponseLoginDTO();
		try {
			byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
			res.setCodigo("500");
			res.setExededReload("0");
			res.setExededReloadAdmin("0");
			if(validTokenAdmin) {
				String newTokenAdmin = validTokenAdmin(tokenAdmin, signingKey,  ipRemota);
				if("1".equals(newTokenAdmin)) {
					res.setExededReloadAdmin("1");
				} else {
					res.setTokenAdmin(newTokenAdmin);
				}	
			}
			try {				
				Jws<Claims> parsedToken = Jwts.parser()
	                    .setSigningKey(signingKey)
	                    .parseClaimsJws(token.replace("Bearer ", ""));
				List<String> roles = (List<String>) parsedToken.getBody().get("rol");
				String ipRemotaToken = parsedToken.getBody().get("remoteIp").toString();
				int total = (int) parsedToken.getBody().get("numero");
				String subject = parsedToken.getBody().getSubject();
				if(ipRemota.equals(ipRemotaToken) && total <  100) {
					String tokenResult = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(subject)
							.setExpiration(new Date(System.currentTimeMillis() + 300000))
							.claim("rol", roles)
							.claim("remoteIp", ipRemota)
							.claim("numero", total + 1)
							.compact();
					res.setCodigo("200");
					res.setToken(tokenResult);
					return new ResponseEntity<>(res, HttpStatus.OK);
				} else {
					res.setExededReload("1");
					res.setCodigo("401");
					return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
				}
			} catch (ExpiredJwtException e) {
				List<String> roles = (List<String>) e.getClaims().get("rol");
				String ipRemotaToken = e.getClaims().get("remoteIp").toString();
				int total = (int) e.getClaims().get("numero");
				String subject = e.getClaims().getSubject();
				if(ipRemota.equals(ipRemotaToken) && total <  100) {
					String tokenResult = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(subject)
							.setExpiration(new Date(System.currentTimeMillis() + 300000))
							.claim("rol", roles)
							.claim("remoteIp", ipRemota)
							.claim("numero", total + 1)
							.compact();
					res.setCodigo("200");
					res.setToken(tokenResult);
					return new ResponseEntity<>(res, HttpStatus.OK);
				} else {
					res.setCodigo("401");
					res.setExededReload("1");
					return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Método que sirver para validar Token de administador
	 * @param tokenAdmin token de administrador
	 * @param signingKey clave privada de token
	 * @param ipRemota ip remota que consulta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String validTokenAdmin(String tokenAdmin, byte[] signingKey, String ipRemota) {
		try {
			try {				
				Jws<Claims> parsedToken = Jwts.parser()
	                    .setSigningKey(signingKey)
	                    .parseClaimsJws(tokenAdmin);
				HashMap<String, Object> usuario =  (HashMap<String, Object>) parsedToken.getBody().get("usuario");
				String ipRemotaToken = parsedToken.getBody().get("remoteIp").toString();
				int total = (int) parsedToken.getBody().get("numero");
				String subject = parsedToken.getBody().getSubject();
				if(ipRemota.equals(ipRemotaToken) && total <  100) {
					String tokenResult = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(subject)
							.setExpiration(new Date(System.currentTimeMillis() + 100000))
							.claim("usuario", usuario)
							.claim("remoteIp", ipRemota)
							.claim("numero", total + 1)
							.compact();
					return tokenResult;
				} else {
					return "1";
				}
			} catch (ExpiredJwtException e) {
				//UsuarioSFBean usuario =  (UsuarioSFBean) e.getClaims().get("rol");
				HashMap<String, Object> usuario =  (HashMap<String, Object>) e.getClaims().get("usuario");
				String ipRemotaToken = e.getClaims().get("remoteIp").toString();
				int total = (int) e.getClaims().get("numero");
				String subject = e.getClaims().getSubject();
				if(ipRemota.equals(ipRemotaToken) && total <  100) {
					String tokenResult = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(subject)
							.setExpiration(new Date(System.currentTimeMillis() + 100000))
							.claim("usuario", usuario)
							.claim("remoteIp", ipRemota)
							.claim("numero", total + 1)
							.compact();
					return tokenResult;
				} else {
					return "1";
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	@PostMapping(path="/admin/seguridad/actualizarContrasena", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RespuestaBean actualizarContrasena(@RequestBody ContrasenaDTO contrasena, @RequestAttribute String cuo) {

		RespuestaBean respuesta = new RespuestaBean();
		try {
			MensajeBean error = validarCamposAC(contrasena);
			if(error!=null) {
				respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR);
				respuesta.setDescripcion(ConstantesSF.Resultado.ERROR);
				respuesta.setError(error);
				return respuesta;
			}
			ContrasenaDTO usuario = new ContrasenaDTO();
			usuario.setnUsuario(contrasena.getnUsuario());
			usuario.setContrasenaActual(CryptoUtil.encriptar(contrasena.getContrasenaActual()));
			usuario.setNuevaContrasena(CryptoUtil.encriptar(contrasena.getNuevaContrasena()));
			int resultado = segUsuarioService.actualizarContrasena(usuario, cuo);
			if(resultado==1) {
				logger.info("SegUsuarioApi.actualizarContrasena actualizado");
				respuesta.setCodigo(ConstantesSF.Resultado.COD_EXITO);
				respuesta.setDescripcion(ConstantesSF.Resultado.MODIFICADO);
			}else {
				throw new Exception("No actualizado.");
			}
		} catch (Exception e) {
			logger.error("ERROR:"+e.getMessage());
	    	MensajeBean mensaje = new MensajeBean();
        	mensaje.setCodigo("0001");
        	mensaje.setDescripcion(e.getMessage());
        	respuesta.setError(mensaje);
			respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR);
			respuesta.setDescripcion(ConstantesSF.Resultado.ERROR);
		}
		return respuesta;
	}	
	
	@PostMapping(path="/seguridad/recuperaContrasenia", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaBean> recuperarContraseniaConEmail(@RequestParam String email, @RequestParam String captcha, @RequestAttribute String ipRemota, @RequestAttribute String cuo) {
		RespuestaBean res = new RespuestaBean();
		String token = "";
		try {
			if(CaptchaUtils.validCaptcha(captcha, ipRemota, cuo)) {
				UsuarioSFBean user = segUsuarioService.validarCorreo(email, cuo);
				if(user != null) {
					if(user.getnUsuario() > 0) {
						ContrasenaDTO usuario = new ContrasenaDTO();
						Random r = new Random();
					    int val = r.nextInt(1000);
						usuario.setnUsuario(user.getnUsuario());
						token = EncryptUtils.encryptPastFrass( user.getcUsuario()+'_'+val );
						token = token.replaceAll("[^a-zA-Z0-9]+","");
						segUsuarioService.actualizarDatosRecuperacion(usuario, token, 1, cuo);					
						String urlWeb = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.SENTIDO_FALLO_URL_WEB);
						urlWeb = urlWeb+"#/recuperarContraseniaConCorreo?token="+token;
						emailService.sendEmail("<p>Estimado(a) "+user.getxNomUsuario()+", para generar nueva contraseña por favor ingresar en el siguiente Link  </p><p>"+urlWeb+"</p><p>Gracias.</p>", email, ConstantesSF.Email.SUBJECT);
						res.setCodigo("200");
						res.setDescripcion("CORREO ENVIADO CORRECTAMENTE");
					} else {
						res.setCodigo("400");
						res.setDescripcion("Correo indicado no se encuentra registrado o relacionado a un usuario activo, por favor comunicarse con administrador.");
					}
				} else {
					res.setCodigo("400");
					res.setDescripcion("Correo indicado no se encuentra registrado o relacionado a un usuario activo, por favor comunicarse con administrador.");
				}	
			} else {
				res.setCodigo("401");
				res.setDescripcion("Código captcha inválido o expirado, por favor ingresar captcha nuevamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setCodigo("500");
			res.setDescripcion(e.getMessage());
		}
		return new ResponseEntity<RespuestaBean>(res, HttpStatus.OK);
	}
	
	@PostMapping(path="/seguridad/confirmaRecuperacionContrasenia", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaBean> recuperarContraseniaConEmail(@RequestParam String token, @RequestBody ContrasenaDTO contrasenia,  @RequestAttribute String cuo) {
		RespuestaBean res = new RespuestaBean();
		try {
			Date current = new Date();
			Timestamp fecha = new Timestamp(current.getTime());
			UsuarioSFBean user = segUsuarioService.validarToken(token, fecha, cuo);
			if(user != null) {
				if(user.getnUsuario() > 0) {
					if((contrasenia.getNuevaContrasena() == null? "":contrasenia.getNuevaContrasena()).length()> 5) {
						ContrasenaDTO usuario = new ContrasenaDTO();
						usuario.setnUsuario(user.getnUsuario());
						usuario.setContrasenaActual(CryptoUtil.encriptar(contrasenia.getContrasenaActual()));
						usuario.setNuevaContrasena(CryptoUtil.encriptar(contrasenia.getNuevaContrasena()));
						segUsuarioService.actualizarDatosRecuperacion(usuario, token, 2, cuo);
						res.setCodigo("200");
						res.setDescripcion("Su nueva clave se ha registrado con exito, usuario ["+user.getcUsuario()+"]");
					} else {
						res.setCodigo("400");
						res.setDescripcion("Contraseña ingresada es invalida, debe tener como mínimo 6 caracteres.");
					}
				} else {
					res.setCodigo("400");
					res.setDescripcion("Token inválido o expirado, se le recomienda generar nuevo token de recuperación.");
				}
			} else {
				res.setCodigo("400");
				res.setDescripcion("Token inválido o expirado, se le recomienda generar nuevo token de recuperación.");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			res.setCodigo("500");
			res.setDescripcion(e.getMessage());
		}
		return new ResponseEntity<RespuestaBean>(res, HttpStatus.OK);
	}
	
	@GetMapping(path="/seguridad/verificarToken", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaBean> verificarTokenValido(@RequestParam String token, @RequestAttribute String cuo) {
		RespuestaBean res = new RespuestaBean();
		try {
			Date current = new Date();
			Timestamp fecha = new Timestamp(current.getTime());
			UsuarioSFBean user = segUsuarioService.validarToken(token, fecha, cuo);
			if(user != null) {
				if(user.getnUsuario() > 0) {					
					res.setCodigo("200");
					res.setDescripcion("Token válido");
				} else {
					res.setCodigo("400");
					res.setDescripcion("Token(url) inválido o expirado, se le recomienda generar nuevo token de recuperación.");
				}
			} else {
				res.setCodigo("400");
				res.setDescripcion("Token(url) inválido o expirado, se le recomienda generar nuevo token de recuperación.");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			res.setCodigo("500");
			res.setDescripcion(e.getMessage());
		}
		return new ResponseEntity<RespuestaBean>(res, HttpStatus.OK);
	}
	
    private MensajeBean validarCamposAC(ContrasenaDTO contrasena) {
    	MensajeBean mensaje = new MensajeBean();
        if (contrasena.getnUsuario()<=0) {
        	mensaje.setCodigo("0201");
        	mensaje.setDescripcion("Ingrese su usuario.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(contrasena.getContrasenaActual())) {
        	mensaje.setCodigo("0202");
        	mensaje.setDescripcion("Ingrese su contraseña actual.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(contrasena.getNuevaContrasena())) {
        	mensaje.setCodigo("0203");
        	mensaje.setDescripcion("Ingrese su nueva contraseña.");
        	return mensaje;
        }
        return null;
    }

}
