package pe.gob.pj.fallo.app.apis;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import pe.gob.pj.fallo.dto.ExpedienteDTO;
import pe.gob.pj.fallo.dto.MagistradoDTO;
import pe.gob.pj.fallo.dto.ResponseHistorialDTO;
import pe.gob.pj.fallo.dto.ResponseSentidoFalloDTO;
import pe.gob.pj.fallo.dto.SentidoDeFalloDTO;
import pe.gob.pj.fallo.dto.SentidoFalloDTO;
import pe.gob.pj.fallo.dto.UsuarioDTO;
import pe.gob.pj.fallo.dto.io.FormatoSentidoFallo;
import pe.gob.pj.fallo.dto.io.ParametroResumenDTO;
import pe.gob.pj.fallo.dto.io.ResponsePersonaDTO;
import pe.gob.pj.fallo.dto.io.ResumenSentidoFalloDTO;
import pe.gob.pj.fallo.entity.MovPersonaReniec;
import pe.gob.pj.fallo.service.ManUsuarioService;
import pe.gob.pj.fallo.service.ReniecService;
import pe.gob.pj.fallo.service.SentidoFalloService;
import pe.gob.pj.fallo.utils.ConstantesSF;
import pe.gob.pj.fallo.utils.ParametrosDeBusqueda;
import pe.gob.pj.fallo.utils.Utils;
import pe.gob.pj.fallo.utils.UtilsCore;
import pe.gob.pj.fallo.utils.ValidarUtil;


@RestController
public class ImpSentidoFalloApi implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ImpSentidoFalloApi.class);
	
	
	@Autowired
	private SentidoFalloService sentidoFalloService;
	
	@Autowired
	private ManUsuarioService manUsuarioService;
	
	@Autowired
	private ReniecService reniecService;

	@PostMapping(path="/admin/sentido-fallo/importarDatos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RespuestaBean importarDatos(@RequestBody ResumenSentidoFalloDTO resumen, @RequestAttribute String cuo, @RequestAttribute String usuarioLogueado) {		
		
		
		RespuestaBean respuesta = new RespuestaBean();
		try {
			if(!(resumen.getPcOperacion()!=null)) {
				resumen.setPcOperacion(UtilsCore.getPCName());
			}
			if(!(resumen.getIpOperacion()!=null)) {
				resumen.setIpOperacion(UtilsCore.getIPAddress(true));
			}
			if(!(resumen.getMacOperacion()!=null)) {
				resumen.setMacOperacion(UtilsCore.getMACAddress());
			}
			
	    	MensajeBean mensaje = new MensajeBean();
	        if (resumen.getUsuario()<=0) {
	        	mensaje.setCodigo("0301");
	        	mensaje.setDescripcion("Ingrese el identificador del usuario.");
				respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR);
				respuesta.setDescripcion(ConstantesSF.Resultado.ERROR);
				respuesta.setError(mensaje);
				return respuesta;
	        }
			UsuarioDTO usuario = manUsuarioService.buscarUsuario(resumen.getUsuario(), cuo);
			MensajeBean error = validarCamposID(resumen, usuario.getxNomOrgjurisd(), cuo);
			if(error!=null) {
				respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR);
				respuesta.setDescripcion(ConstantesSF.Resultado.ERROR);
				respuesta.setError(error);
				return respuesta;
			}
			/*resumen.setIpOperacion(resumen.getIpOperacion());
			resumen.setMacOperacion(resumen.getMacOperacion());
			resumen.setPcOperacion(resumen.getPcOperacion());*/
			resumen.setFechaOperacion(new Date());
			resumen.setUsuarioOperacion(usuario.getcUsuario()); 

			List<FormatoSentidoFallo> listaFormatos = resumen.getDatos();
			List<ExpedienteDTO> listaExpedientes= new ArrayList<ExpedienteDTO>();			
			for (FormatoSentidoFallo formatoSentidoFallo : listaFormatos) {
				ExpedienteDTO expedienteDTO= new ExpedienteDTO();
				expedienteDTO.setFormato(formatoSentidoFallo.getxFormato());
				expedienteDTO.setDescproceso(formatoSentidoFallo.getxProceso());
				expedienteDTO.setDesEspecialidad(formatoSentidoFallo.getxEspecialidad());
				expedienteDTO.setIpOperacion(resumen.getIpOperacion());
				expedienteDTO.setMacOperacion(resumen.getMacOperacion());
				expedienteDTO.setPcOperacion(resumen.getPcOperacion());
				expedienteDTO.setFechaOperacion(new Date());
				expedienteDTO.setUsuarioOperacion(usuario.getcUsuario()); 
				expedienteDTO.setCodDistritoJudicial(usuario.getcDistritoJudicial());
				expedienteDTO.setOrgano(usuario.getxNomOrgjurisd());

				List<SentidoDeFalloDTO> listaSentidoFallos= new ArrayList<SentidoDeFalloDTO>();
				SentidoDeFalloDTO sentidoFalloDTO= new SentidoDeFalloDTO();
				sentidoFalloDTO.setDescripcion(formatoSentidoFallo.getxDescripcionSentidoFallo());
				sentidoFalloDTO.setFechaDecision(formatoSentidoFallo.getxFechaSentidoFallo());
				sentidoFalloDTO.setIdUsuario(usuario.getnUsuario());
				sentidoFalloDTO.setCodDistritoJudicial(usuario.getcDistritoJudicial());
				sentidoFalloDTO.setOrgano(usuario.getxNomOrgjurisd());
				sentidoFalloDTO.setIpOperacion(resumen.getIpOperacion());
				sentidoFalloDTO.setMacOperacion(resumen.getMacOperacion());
				sentidoFalloDTO.setPcOperacion(resumen.getPcOperacion());
				sentidoFalloDTO.setFechaOperacion(new Date());
				sentidoFalloDTO.setUsuarioOperacion(usuario.getcUsuario());
				
				List<MagistradoDTO> listaMagistrados= new ArrayList<MagistradoDTO>();
				if (!ValidarUtil.isNullOrEmpty(formatoSentidoFallo.getDni1())) {
					MagistradoDTO magistradoDTO1= new MagistradoDTO();
					String nom = recuperaDatosReniec(formatoSentidoFallo.getDni1(), cuo,resumen.getPcOperacion(),resumen.getIpOperacion(),resumen.getMacOperacion(),usuarioLogueado );
					if(Utils.isNull(nom).length()<= 0) {
						respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR_VALID_RENIEC);
						respuesta.setDescripcion("Error información ingresada de Magistrado: "+formatoSentidoFallo.getxMagistrado1()+", no se ha podido validar con RENIEC.");
						return respuesta;
					}
					magistradoDTO1.setNombre(nom);
					magistradoDTO1.setDni(formatoSentidoFallo.getDni1());
					magistradoDTO1.setIpOperacion(resumen.getIpOperacion());
					magistradoDTO1.setMacOperacion(resumen.getMacOperacion());
					magistradoDTO1.setPcOperacion(resumen.getPcOperacion());
					magistradoDTO1.setFechaOperacion(new Date());
					magistradoDTO1.setUsuarioOperacion(usuario.getcUsuario());
					listaMagistrados.add(magistradoDTO1);
				}
				if (!ValidarUtil.isNullOrEmpty(formatoSentidoFallo.getDni2())) {
					MagistradoDTO magistradoDTO2= new MagistradoDTO();
					String nom = recuperaDatosReniec(formatoSentidoFallo.getDni2(), cuo,resumen.getPcOperacion(),resumen.getIpOperacion(),resumen.getMacOperacion(),usuarioLogueado );
					if(Utils.isNull(nom).length()<= 0) {
						respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR_VALID_RENIEC);
						respuesta.setDescripcion("Error información ingresada de Magistrado: "+formatoSentidoFallo.getxMagistrado2()+", no se ha podido validar con RENIEC.");
						return respuesta;
					}
					magistradoDTO2.setNombre(nom);
					magistradoDTO2.setDni(formatoSentidoFallo.getDni2());
					magistradoDTO2.setIpOperacion(resumen.getIpOperacion());
					magistradoDTO2.setMacOperacion(resumen.getMacOperacion());
					magistradoDTO2.setPcOperacion(resumen.getPcOperacion());
					magistradoDTO2.setFechaOperacion(new Date());
					magistradoDTO2.setUsuarioOperacion(usuario.getcUsuario());
					listaMagistrados.add(magistradoDTO2);
				}
				if (!ValidarUtil.isNullOrEmpty(formatoSentidoFallo.getDni3())) {
					MagistradoDTO magistradoDTO3= new MagistradoDTO();
					String nom = recuperaDatosReniec(formatoSentidoFallo.getDni3(), cuo,resumen.getPcOperacion(),resumen.getIpOperacion(),resumen.getMacOperacion(),usuarioLogueado );
					if(Utils.isNull(nom).length()<=0) {
						respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR_VALID_RENIEC);
						respuesta.setDescripcion("Error información ingresada de Magistrado: "+formatoSentidoFallo.getxMagistrado3()+", no se ha podido validar con RENIEC.");
						return respuesta;
					}
					magistradoDTO3.setNombre(nom);
					magistradoDTO3.setDni(formatoSentidoFallo.getDni3());
					magistradoDTO3.setIpOperacion(resumen.getIpOperacion());
					magistradoDTO3.setMacOperacion(resumen.getMacOperacion());
					magistradoDTO3.setPcOperacion(resumen.getPcOperacion());
					magistradoDTO3.setFechaOperacion(new Date());
					magistradoDTO3.setUsuarioOperacion(usuario.getcUsuario());
					listaMagistrados.add(magistradoDTO3);					
				}
				if (!ValidarUtil.isNullOrEmpty(formatoSentidoFallo.getDni4())) {					
					MagistradoDTO magistradoDTO4= new MagistradoDTO();
					String nom = recuperaDatosReniec(formatoSentidoFallo.getDni4(), cuo,resumen.getPcOperacion(),resumen.getIpOperacion(),resumen.getMacOperacion(),usuarioLogueado );
					if(Utils.isNull(nom).length() <= 0) {
						respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR_VALID_RENIEC);
						respuesta.setDescripcion("Error información ingresada de Magistrado: "+formatoSentidoFallo.getxMagistrado4()+", no se ha podido validar con RENIEC.");
						return respuesta;
					}
					magistradoDTO4.setNombre(nom);
					magistradoDTO4.setDni(formatoSentidoFallo.getDni4());
					magistradoDTO4.setIpOperacion(resumen.getIpOperacion());
					magistradoDTO4.setMacOperacion(resumen.getMacOperacion());
					magistradoDTO4.setPcOperacion(resumen.getPcOperacion());
					magistradoDTO4.setFechaOperacion(new Date());
					magistradoDTO4.setUsuarioOperacion(usuario.getcUsuario());
					listaMagistrados.add(magistradoDTO4);								
				}
				if (!ValidarUtil.isNullOrEmpty(formatoSentidoFallo.getDni5())) {
					MagistradoDTO magistradoDTO5= new MagistradoDTO();
					String nom = recuperaDatosReniec(formatoSentidoFallo.getDni4(), cuo,resumen.getPcOperacion(),resumen.getIpOperacion(),resumen.getMacOperacion(),usuarioLogueado );
					if(Utils.isNull(nom).length() <= 0) {
						respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR_VALID_RENIEC);
						respuesta.setDescripcion("Error información ingresada de Magistrado: "+formatoSentidoFallo.getxMagistrado5()+", no se ha podido validar con RENIEC.");
						return respuesta;
					}
					magistradoDTO5.setNombre(nom);
					magistradoDTO5.setDni(formatoSentidoFallo.getDni5());
					magistradoDTO5.setIpOperacion(resumen.getIpOperacion());
					magistradoDTO5.setMacOperacion(resumen.getMacOperacion());
					magistradoDTO5.setPcOperacion(resumen.getPcOperacion());
					magistradoDTO5.setFechaOperacion(new Date());
					magistradoDTO5.setUsuarioOperacion(usuario.getcUsuario());
					listaMagistrados.add(magistradoDTO5);						
				}
				sentidoFalloDTO.setListaMagistrados(listaMagistrados);
				listaSentidoFallos.add(sentidoFalloDTO);
				expedienteDTO.setSentidoFallo(listaSentidoFallos);
				listaExpedientes.add(expedienteDTO);
			}
			//resumen.setDatos(null);
			resumen.setListaExpedientes(listaExpedientes);
			int rpta = sentidoFalloService.grabarSentidoFallo(resumen, cuo);
			if(rpta==1) {
				logger.info(cuo+" ImpSentidoFalloApi.importarDatos Se cargo información correctamente");
				respuesta.setCodigo(ConstantesSF.Resultado.COD_EXITO);
				respuesta.setDescripcion(ConstantesSF.Resultado.REGISTRADO);
			}else {
				throw new Exception("No registrado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(cuo+" ERROR:"+e.getMessage());
	    	MensajeBean mensaje = new MensajeBean();
        	mensaje.setCodigo("0001");
        	mensaje.setDescripcion(e.getMessage());
        	respuesta.setError(mensaje);
			respuesta.setCodigo(ConstantesSF.Resultado.COD_ERROR);
			respuesta.setDescripcion(ConstantesSF.Resultado.ERROR);
		}
		return respuesta;
	}	
	
	@SuppressWarnings("unused")
	private MensajeBean validarCamposID(ResumenSentidoFalloDTO resumen, String organo, String cuo) {
    	MensajeBean mensaje = new MensajeBean();
        if (ValidarUtil.isNullOrEmpty(resumen.getEstado())) {
        	mensaje.setCodigo("0303");
        	mensaje.setDescripcion("Ingrese el estado de carga.");
        	return mensaje;
        }
        if (ValidarUtil.isNullOrEmpty(resumen.getDatos())) {
        	mensaje.setCodigo("0304");
        	mensaje.setDescripcion("Ingrese el lista de expedientes.");
        	return mensaje;
        }
        List<FormatoSentidoFallo> listaFormatos = resumen.getDatos();
        Date fecha;
        int cantMagistrado, cantRevision;
        for(FormatoSentidoFallo formato: listaFormatos) {
            if (ValidarUtil.isNullOrEmpty(formato.getxFormato())) {
            	mensaje.setCodigo("0311");
            	mensaje.setDescripcion("Ingrese el codigo de expediente.");
            	return mensaje;
            }
            if (formato.getxFormato().trim().length()>50) {
            	mensaje.setCodigo("0321");
            	mensaje.setDescripcion("El codigo de expediente no debe ser mayor 50 caracteres.");
            	return mensaje;
            }
            if (ValidarUtil.isNullOrEmpty(formato.getxFechaSentidoFallo())) {
            	mensaje.setCodigo("0312");
            	mensaje.setDescripcion("Ingrese la fecha de sentido de fallo.");
            	return mensaje;
            }
            if (formato.getxFechaSentidoFallo().trim().length()>10) {
            	mensaje.setCodigo("0322");
            	mensaje.setDescripcion("La fecha de sentido de fallo no debe ser mayor 10 caracteres.");
            	return mensaje;
            }
            if (ValidarUtil.isNullOrEmpty(formato.getxDescripcionSentidoFallo())) {
            	mensaje.setCodigo("0313");
            	mensaje.setDescripcion("Ingrese la descripcion de sentido de fallo.");
            	return mensaje;
            }
            if (formato.getxDescripcionSentidoFallo().trim().length()>600) {
            	mensaje.setCodigo("0323");
            	mensaje.setDescripcion("La descripcion de sentido de fallo no debe ser mayor 600 caracteres.");
            	return mensaje;
            }
            if (ValidarUtil.isNullOrEmpty(formato.getxEspecialidad())) {
            	mensaje.setCodigo("0314");
            	mensaje.setDescripcion("Ingrese la especialidad de sentido de fallo.");
            	return mensaje;
            } 
            if (formato.getxEspecialidad().trim().length()>30) {
            	mensaje.setCodigo("0324");
            	mensaje.setDescripcion("Ingrese la especialidad de sentido de fallo no debe ser mayor 30 caracteres.");
            	return mensaje;
            }
            try {
            	fecha=new SimpleDateFormat("dd/MM/yyyy").parse(formato.getxFechaSentidoFallo());
            }catch (Exception e) {
            	mensaje.setCodigo("0315");
            	mensaje.setDescripcion("La fecha de sentido de fallo no tiene el formato: dd/MM/yyyy");
            	return mensaje;
			}
            cantMagistrado=0;
           	if (!ValidarUtil.isNullOrEmpty(formato.getxMagistrado1())) cantMagistrado++;
           	if (!ValidarUtil.isNullOrEmpty(formato.getxMagistrado2())) cantMagistrado++;
           	if (!ValidarUtil.isNullOrEmpty(formato.getxMagistrado3())) cantMagistrado++;
           	if (!ValidarUtil.isNullOrEmpty(formato.getxMagistrado4())) cantMagistrado++;
           	if (!ValidarUtil.isNullOrEmpty(formato.getxMagistrado5())) cantMagistrado++;
            if (cantMagistrado<=0) {
            	mensaje.setCodigo("0316");
            	mensaje.setDescripcion("Debe ingresar al menos un magistrado.");
            	return mensaje;
            } 
            cantRevision=0;
            try {
				cantRevision=sentidoFalloService.validarSentidoFallos(formato.getxFormato().trim(), 
					formato.getxFechaSentidoFallo().trim(), organo.trim(), cuo);
	            if (cantRevision>0) {
	            	mensaje.setCodigo("0317");
	            	mensaje.setDescripcion("La combinación de código expediente, fecha de sentido y órgano jurisdiccional ya se encuentra registrado, para expediente["+formato.getxFormato()+"].");
	            	return mensaje;
	            } 
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return null;
    }

	@RequestMapping(path="/admin/sentido-fallo/consultarResumen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseHistorialDTO> consultarResumen(@RequestBody ParametroResumenDTO resumen, @RequestAttribute String cuo) {
		logger.info(cuo+" ImpSentidoFalloApi.consultarResumen inicio");
		List<ResumenSentidoFalloDTO> lista=new ArrayList<ResumenSentidoFalloDTO>();
		ResponseHistorialDTO resp = new ResponseHistorialDTO();
		try {
			resp.setCodigo("200");
			boolean minimoFiltro = false;
			Map<String, Object> parametros = new HashMap<>();
			if (resumen.getnCarga()>0) {
				parametros.put(ParametrosDeBusqueda.RESU_ID, Long.valueOf(resumen.getnCarga()));
				minimoFiltro = true;
			}else if (resumen.getnUsuario()>0) {
				parametros.put(ParametrosDeBusqueda.RESU_USUARIO, Long.valueOf(resumen.getnUsuario()));
				minimoFiltro = true;
			} else if (!ValidarUtil.isNull(resumen.getEstado())) {
				parametros.put(ParametrosDeBusqueda.RESU_ESTADO, resumen.getEstado());
				minimoFiltro = true;
			} 
			if(minimoFiltro) {
				lista = sentidoFalloService.consultarResumen(parametros, cuo);
				if(lista!=null && lista.size()>0) {
					logger.info(cuo+" ImpSentidoFalloApi.consultarResumen consultado");
				}else {
					logger.info(cuo+" ImpSentidoFalloApi.consultarResumen no consultado");
				}
			}else {
				logger.info(cuo+" ImpSentidoFalloApi.consultarResumen sin filtros");
			}
		} catch (Exception e) {
			logger.error(cuo+" ERROR:"+e.getMessage());
		}
		resp.setData(lista);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/sentido-fallo/detallarCarga", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseSentidoFalloDTO> detallarCarga(@RequestParam String id, @RequestAttribute String cuo) {
		logger.info(cuo+" ImpSentidoFalloApi.detallarCarga id="+id);
		List<SentidoFalloDTO> lista = new ArrayList<SentidoFalloDTO>();
		ResponseSentidoFalloDTO resp = new ResponseSentidoFalloDTO();
		try {
			Long nCarga;
			if(id!=null && !id.isEmpty()) {
				nCarga= new Long(id);	
			}else {
				resp.setCodigo("404");
				resp.setData(lista);
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}
			lista = sentidoFalloService.detallarSentidosFallo(nCarga, cuo);
			if(lista.size() > 0) {
				logger.info(cuo+" ImpSentidoFalloApi.detallarCarga consultado cantidad="+lista.size());
				resp.setCodigo("200");
				resp.setData(lista);
				return new ResponseEntity<>(resp, HttpStatus.OK);
			} else {
				logger.info(cuo+" ImpSentidoFalloApi.detallarCarga no consultado");
				resp.setCodigo("404");
				resp.setData(lista);
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}				
		} catch (Exception e) {
			logger.error(cuo+" ERROR:"+e.getMessage());
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	public String recuperaDatosReniec(String dni, String cuo, String pcName, String ipAddress, String macAddress, String usuarioLogueado) {
		StringBuilder nombre = new StringBuilder();
		try {
			if(Utils.isNull(dni).length() != 8) {return null;}
			MovPersonaReniec per = manUsuarioService.validarPersonaReniec(dni, cuo);
			if(per != null) {
				nombre.append(per.getxPrimerApellido()).append(" ");
				nombre.append(per.getxSegundoApellido()).append(", ");
				nombre.append(per.getxNombre());								
			} else {
				PersonaDTO persona = reniecService.obtenerPersonaPorDNI(dni,pcName,ipAddress,macAddress,usuarioLogueado);
				if(persona.getApellidoPaterno() != null) {
					ResponsePersonaDTO respuesta = new ResponsePersonaDTO();
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
					nombre.append(temp.getxPrimerApellido()).append(" ");
					nombre.append(temp.getxSegundoApellido()).append(", ");	
					nombre.append(temp.getxNombre());
				} 				
			}
			return nombre.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
