package pe.gob.pj.fallo.app.apis;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.fallo.bean.MensajeBean;
import pe.gob.pj.fallo.bean.RespuestaBean;
import pe.gob.pj.fallo.client.reniec.PersonaDTO;
import pe.gob.pj.fallo.dto.ResponseComboDTO;
import pe.gob.pj.fallo.dto.ResponseListaUsuariosDTO;
import pe.gob.pj.fallo.dto.UsuarioDTO;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;
import pe.gob.pj.fallo.dto.io.ParametroUsuarioDTO;
import pe.gob.pj.fallo.dto.io.ResponsePersonaDTO;
import pe.gob.pj.fallo.entity.MovPersonaReniec;
import pe.gob.pj.fallo.service.ManUsuarioService;
import pe.gob.pj.fallo.service.ReniecService;
import pe.gob.pj.fallo.utils.ConstantesSF;
import pe.gob.pj.fallo.utils.CryptoUtil;
import pe.gob.pj.fallo.utils.ParametrosDeBusqueda;
import pe.gob.pj.fallo.utils.UtilsCore;
import pe.gob.pj.fallo.utils.ValidarUtil;


@RestController
public class ManUsuarioApi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ManUsuarioApi.class);
	
	@Autowired
	private ManUsuarioService manUsuarioService;
	@Autowired
	private ReniecService reniecService;
	
	@PostMapping(path="/admin/man-usuario/consultaUsuario", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseListaUsuariosDTO> consultaUsuario(@RequestBody ParametroUsuarioDTO usuario, @RequestAttribute String cuo) {
		List<UsuarioDTO> lista=new ArrayList<UsuarioDTO>();
		ResponseListaUsuariosDTO res = new ResponseListaUsuariosDTO();
		try {
			res.setCodigo("200");
			boolean minimoFiltro = true;
			Map<String, Object> parametros = new HashMap<>();
			if (!ValidarUtil.isNull(usuario.getUsuario())) {
				parametros.put(ParametrosDeBusqueda.USUA_ID, Long.parseLong(usuario.getUsuario()));
				minimoFiltro = true;
			}
			if (!ValidarUtil.isNull(usuario.getDocIdentidad())) {
				parametros.put(ParametrosDeBusqueda.USUA_NRO_DOC, usuario.getDocIdentidad().trim());
				minimoFiltro = true;
			}
			if (!ValidarUtil.isNull(usuario.getNombreCompleto())) {
				parametros.put(ParametrosDeBusqueda.USUA_NOMBRE_COMPLETO, usuario.getNombreCompleto().trim().toUpperCase()+"%");
				minimoFiltro = true;
			}
			if (!ValidarUtil.isNull(usuario.getActivo())) {
				parametros.put(ParametrosDeBusqueda.USUA_ESTADO, usuario.getActivo());
				minimoFiltro = true;
			}
			if (!ValidarUtil.isNullOrEmpty(usuario.getPerfil())) {
				parametros.put(ParametrosDeBusqueda.USUA_PERFIL, Long.parseLong(usuario.getPerfil()));
				minimoFiltro = true;
			}
			if(minimoFiltro) {
				lista = manUsuarioService.consultarUsuario(parametros, cuo);
				if(lista!=null && lista.size()>0) {
					logger.info(cuo+" ManUsuarioApi.consultaUsuario consultado");
				}else {
					logger.info(cuo+" ManUsuarioApi.consultaUsuario no consultado");
				}
			}else {
				logger.info(cuo+" ManUsuarioApi.consultaUsuario sin filtros");
			}
		} catch (Exception e) {
			logger.error(cuo+" ERROR:"+e.getMessage());
		}
		res.setData(lista);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}	
	
	@RequestMapping(value ="/admin/man-usuario/obtenerPersonaPorDNI", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponsePersonaDTO> obtenerPersonaPorDNI(@RequestParam String dni, @RequestParam(required = false) String clientePcName,
			@RequestParam(required = false) String clienteIpAddress, @RequestParam(required = false) String clienteMacAddress, @RequestAttribute String cuo, @RequestAttribute String usuarioLogueado) {
		
		
		ResponsePersonaDTO respuesta = new ResponsePersonaDTO();
		try {
			if(!(clientePcName!=null)) {
				clientePcName = UtilsCore.getPCName();
			}
			if(!(clienteIpAddress!=null)) {
				clienteIpAddress = UtilsCore.getIPAddress(true);
			}
			if(!(clienteMacAddress!=null)) {
				clienteMacAddress = UtilsCore.getMACAddress();
			}
			MovPersonaReniec per = manUsuarioService.validarPersonaReniec(dni, cuo);
			if(per != null) {
				respuesta.setPaterno(per.getxPrimerApellido());
				respuesta.setMaterno(per.getxSegundoApellido());
				respuesta.setNombres(per.getxNombre());;
			} else {
				PersonaDTO persona = reniecService.obtenerPersonaPorDNI(dni,clientePcName,clienteIpAddress,clienteMacAddress,usuarioLogueado);
				if(persona.getApellidoPaterno() != null) {
					respuesta.setNombres(persona.getNombres());
					respuesta.setPaterno(persona.getApellidoPaterno());
					String materno = persona.getApellidoMaterno();
					if(ValidarUtil.isNullOrEmpty(persona.getApellidoCasado())){
						respuesta.setMaterno(materno.concat(" ").concat(persona.getApellidoCasado()));	
					}else {
						respuesta.setMaterno(materno);
					}
					MovPersonaReniec temp = new MovPersonaReniec();
					temp.setxDni(dni);
					temp.setxPrimerApellido(respuesta.getPaterno());
					temp.setxSegundoApellido(respuesta.getMaterno());
					temp.setxNombre(respuesta.getNombres());
					StringBuilder nc = new StringBuilder();
					nc.append(temp.getxPrimerApellido()).append(" ");
					nc.append(temp.getxSegundoApellido()).append(" ");
					nc.append(temp.getxNombre());
					temp.setxNombreCompleto(nc.toString());
				    temp.setlActivo(ConstantesSF.ACTIVO);
				    temp.setfAud(new Timestamp((new Date()).getTime()));
					temp.setcAudIp(UtilsCore.getIPAddress(true));
					temp.setcAudMcaddr(UtilsCore.getMACAddress());
					temp.setcAudPc(UtilsCore.getPCName());
					temp.setcAudUid("uc_SentidoFallo_web");
					temp.setbAud(ConstantesSF.Operacion.INSERTAR);
					try {
						manUsuarioService.registrarPersona(temp, cuo);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(cuo.concat(e.getMessage()));
					}				
				} 				
			}
			
		} catch (Exception e) {
			logger.error(cuo+" ERROR:"+e.getMessage());
			return new ResponseEntity<ResponsePersonaDTO>(respuesta, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ResponsePersonaDTO>(respuesta, HttpStatus.OK);
	}
	
	@PostMapping(path="/admin/man-usuario/registrarUsuario", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RespuestaBean registrarUsuario(@RequestBody UsuarioDTO usuario, @RequestAttribute String cuo) {
		
		RespuestaBean respuesta = new RespuestaBean();
		try {
			MensajeBean error = validarCamposRU(usuario);
			if(error!=null) {
				respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR);
				respuesta.setDescripcion(ConstantesSF.Resultado.ERROR);
				respuesta.setError(error);
				return respuesta;
			}	
			//TODO Falta validar que el cUsuario y DocIdentidad no se repitan
			usuario.setcClave(CryptoUtil.encriptar(usuario.getcClave().trim()));
			usuario.setlReseteo(ConstantesSF.ACTIVO);
			usuario.setUsuarioRedOperacion(usuario.getUsuarioOperacion());
			usuario.setTipoOperacion(ConstantesSF.Operacion.INSERTAR);
			usuario.setFechaOperacion(new Date());
			usuario.setIpOperacion(UtilsCore.getIPAddress(true));
			usuario.setMacOperacion(UtilsCore.getMACAddress());
			usuario.setPcOperacion(UtilsCore.getPCName());
			
			Long nUsuario = manUsuarioService.registrarUsuario(usuario, cuo);
			if(nUsuario>0) {
				logger.info("SegUsuarioApi.actualizarContrasena registrado");
				respuesta.setCodigo(ConstantesSF.Resultado.COD_EXITO);
				respuesta.setDescripcion(ConstantesSF.Resultado.REGISTRADO);
			}else {
				throw new Exception("No registrado.");
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
	
    private MensajeBean validarCamposRU(UsuarioDTO usuario) {
    	MensajeBean mensaje = new MensajeBean();
        if (ValidarUtil.isNullOrEmpty(usuario.getcUsuario())) {
        	mensaje.setCodigo("0401");
        	mensaje.setDescripcion("Ingrese el usuario.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getcClave())) {
        	mensaje.setCodigo("0402");
        	mensaje.setDescripcion("Ingrese la clave del usuario.");
        	return mensaje;
        }
    	if (ValidarUtil.isNullOrEmpty(usuario.getxNomUsuario())) {
        	mensaje.setCodigo("0403");
        	mensaje.setDescripcion("Ingrese los nombres del usuario.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getxApePaterno())) {
        	mensaje.setCodigo("0404");
        	mensaje.setDescripcion("Ingrese el apellido paterno.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getxApeMaterno())) {
        	mensaje.setCodigo("0405");
        	mensaje.setDescripcion("Ingrese el apellido materno.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getxDocIdentidad())) {
        	mensaje.setCodigo("0406");
        	mensaje.setDescripcion("Ingrese el numero de documento de identidad.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getcDistritoJudicial())) {
        	mensaje.setCodigo("0407");
        	mensaje.setDescripcion("Ingrese el distrito judicial.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getnPerfil())) {
        	mensaje.setCodigo("0408");
        	mensaje.setDescripcion("Ingrese el perfil del usuario.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getxNomOrgjurisd())) {
        	mensaje.setCodigo("0409");
        	mensaje.setDescripcion("Ingrese el organo jurisdiccional.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getUsuarioOperacion())) {
        	mensaje.setCodigo("0410");
        	mensaje.setDescripcion("Ingrese el usuario operacion.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getlReniec())) {
        	mensaje.setCodigo("0411");
        	mensaje.setDescripcion("Ingrese el indicador reniec.");
        	return mensaje;
        }   
		try {
			int revisar = manUsuarioService.validarUsuario(usuario);
	        if (revisar==1) {
	        	mensaje.setCodigo("0412");
	        	mensaje.setDescripcion("El usuario se repite.");
	        	return mensaje;
	        }           
	        if (revisar==2) {
	        	mensaje.setCodigo("0413");
	        	mensaje.setDescripcion("El DNI se repite .");
	        	return mensaje;
	        }           
		} catch (Exception e) {
			e.printStackTrace();
		}
        if (!ValidarUtil.isNullOrEmpty(usuario.getxCorreo()) && !ValidarUtil.validateEmail(usuario.getxCorreo())) {
        	mensaje.setCodigo("0414");
        	mensaje.setDescripcion("El correo no tiene el formato correcto. Utilice caracteres de A-Z, a-z, 0-9. ");
        	return mensaje;
        } 
        return null;
    }
    
	@PostMapping(path="/admin/man-usuario/modificarUsuario", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RespuestaBean modificarUsuario(@RequestBody UsuarioDTO usuario, @RequestAttribute String cuo) {
		
		RespuestaBean respuesta = new RespuestaBean();
		try {
			MensajeBean error = validarCamposMU(usuario);
			if(error!=null) {
				respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR);
				respuesta.setDescripcion(ConstantesSF.Resultado.ERROR);
				respuesta.setError(error);
				return respuesta;
			}	
			usuario.setUsuarioRedOperacion(usuario.getUsuarioOperacion());
			usuario.setTipoOperacion(ConstantesSF.Operacion.ACTUALIZAR);
			usuario.setFechaOperacion(new Date());
			usuario.setIpOperacion(UtilsCore.getIPAddress(true));
			usuario.setMacOperacion(UtilsCore.getMACAddress());
			usuario.setPcOperacion(UtilsCore.getPCName());
						
			int flag = manUsuarioService.modificarUsuario(usuario, cuo);
			if(flag==1) {
				logger.info("SegUsuarioApi.actualizarContrasena actualizado");
				respuesta.setCodigo(ConstantesSF.Resultado.COD_EXITO);
				respuesta.setDescripcion(ConstantesSF.Resultado.MODIFICADO);
			}else {
				throw new Exception("No modificado.");
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

    private MensajeBean validarCamposMU(UsuarioDTO usuario) {
    	MensajeBean mensaje = new MensajeBean();
        if (ValidarUtil.isNullOrEmpty(usuario.getnUsuario())) {
        	mensaje.setCodigo("0421");
        	mensaje.setDescripcion("Ingrese el codigo del usuario.");
        	return mensaje;
        }
    	if (ValidarUtil.isNullOrEmpty(usuario.getxNomUsuario())) {
        	mensaje.setCodigo("0422");
        	mensaje.setDescripcion("Ingrese los nombres del usuario.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getxApePaterno())) {
        	mensaje.setCodigo("0423");
        	mensaje.setDescripcion("Ingrese el apellido paterno.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getxApeMaterno())) {
        	mensaje.setCodigo("0424");
        	mensaje.setDescripcion("Ingrese el apellido materno.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getcDistritoJudicial())) {
        	mensaje.setCodigo("0425");
        	mensaje.setDescripcion("Ingrese el distrito judicial.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getnPerfil())) {
        	mensaje.setCodigo("0426");
        	mensaje.setDescripcion("Ingrese el perfil del usuario.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getxNomOrgjurisd())) {
        	mensaje.setCodigo("0427");
        	mensaje.setDescripcion("Ingrese el organo jurisdiccional.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getUsuarioOperacion())) {
        	mensaje.setCodigo("0428");
        	mensaje.setDescripcion("Ingrese el usuario operacion.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(usuario.getlActivo())) {
        	mensaje.setCodigo("0429");
        	mensaje.setDescripcion("Ingrese el estado activo o inactivo.");
        	return mensaje;
        }
        if (!ValidarUtil.isNullOrEmpty(usuario.getxCorreo()) && !ValidarUtil.validateEmail(usuario.getxCorreo())) {
        	mensaje.setCodigo("0430");
        	mensaje.setDescripcion("El correo no tiene el formato correcto. Utilice caracteres de A-Z, a-z, 0-9. ");
        	return mensaje;
        } 
        return null;
    }
    
	@RequestMapping(value ="/admin/man-usuario/listarMaestros", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseComboDTO> listarMaestros(@RequestParam String codigo, @RequestAttribute String cuo) {
		List<ComboDTO> lista = new ArrayList<ComboDTO>();
		ResponseComboDTO res = new ResponseComboDTO();
		try {
			lista = manUsuarioService.listarMaestros(codigo, cuo);
			res.setCodigo("200");
		} catch (Exception e) {
			logger.error(cuo+" ERROR:"+e.getMessage());
			res.setCodigo("404");
		}
		res.setData(lista);
		return new ResponseEntity<ResponseComboDTO>(res, HttpStatus.OK);
	}
	
	@PostMapping(path="/admin/man-usuario/resetearContrasena", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RespuestaBean resetearContrasena(@RequestBody ContrasenaDTO contrasena, @RequestAttribute String cuo) {
		
		RespuestaBean respuesta = new RespuestaBean();
		try {
			MensajeBean error = validarCamposRC(contrasena);
			if(error!=null) {
				respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR);
				respuesta.setDescripcion(ConstantesSF.Resultado.ERROR);
				respuesta.setError(error);
				return respuesta;
			}	
			ContrasenaDTO usuario = new ContrasenaDTO();
			usuario.setnUsuario(contrasena.getnUsuario());
			usuario.setNuevaContrasena(CryptoUtil.encriptar(contrasena.getNuevaContrasena()));
			usuario.setnUsuarioAdmin(contrasena.getnUsuarioAdmin());

			int flag = manUsuarioService.resetearContrasena(usuario, cuo);
			if(flag==1) {
				logger.info("SegUsuarioApi.resetearContrasena modificado");
				respuesta.setCodigo(ConstantesSF.Resultado.COD_EXITO);
				respuesta.setDescripcion(ConstantesSF.Resultado.MODIFICADO);
			}else {
				throw new Exception("No modificado.");
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
	
    private MensajeBean validarCamposRC(ContrasenaDTO contrasena) {
    	MensajeBean mensaje = new MensajeBean();
        if (ValidarUtil.isNullOrEmpty(contrasena.getnUsuario())) {
        	mensaje.setCodigo("0501");
        	mensaje.setDescripcion("Ingrese el codigo del usuario.");
        	return mensaje;
        }	
        if (ValidarUtil.isNullOrEmpty(contrasena.getnUsuarioAdmin())) {
        	mensaje.setCodigo("0502");
        	mensaje.setDescripcion("Ingrese el codigo del usuario administrador.");
        	return mensaje;
        }	
        if (ValidarUtil.isNullOrEmpty(contrasena.getNuevaContrasena())) {
        	mensaje.setCodigo("0503");
        	mensaje.setDescripcion("Ingrese la nueva contraseña.");
        	return mensaje;
        }      
        return null;
    }
}
