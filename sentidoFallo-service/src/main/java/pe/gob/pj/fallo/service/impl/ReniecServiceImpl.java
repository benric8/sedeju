package pe.gob.pj.fallo.service.impl;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.fallo.client.reniec.Auditoria;
import pe.gob.pj.fallo.client.reniec.Pagination;
import pe.gob.pj.fallo.client.reniec.PersonaConsultada;
import pe.gob.pj.fallo.client.reniec.PersonaDTO;
import pe.gob.pj.fallo.client.reniec.ReniecResponse;
import pe.gob.pj.fallo.client.reniec.RequestConsultarPersona;
import pe.gob.pj.fallo.dto.TokenAuthResponse;
import pe.gob.pj.fallo.service.ReniecService;
import pe.gob.pj.fallo.utils.ConfiguracionPropiedades;
import pe.gob.pj.fallo.utils.ConstantesSF;
import pe.gob.pj.fallo.utils.EncryptUtils;
import pe.gob.pj.fallo.utils.Propiedades;
import pe.gob.pj.fallo.utils.UtilsCore;
import pe.gob.pj.fallo.utils.ValidarUtil;
import pe.gob.pj.ws.cliente.reniec.consultas.bean.ParametroConsultaDTO;

@Slf4j
@Service("reniecService")
public class ReniecServiceImpl implements ReniecService {

	
	@Autowired
    private RestTemplate restTemplate;
	

	/** Reniec **/
	private String ENDPOINT_SERVICIO_RENIEC = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RENIEC_ENDPOINT);
	private String DNI_CONSULTANTE_SERVICIO_RENIEC = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RENIEC_DNI_CONSULTA);
	private String USUARIO_CONSULTANTE_SERVICIO_RENIEC = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RENIEC_USUARIO_CONSULTA);
	private String TIPO_CONSULTA_SERVICIO_RENIEC_POR_DATOS = "1";
	private String TIPO_CONSULTA_SERVICIO_RENIEC_POR_DNI = "2";
	private final String USERNAME = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RENIEC_USERNAME);
	private final String PASSWORD = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RENIEC_PASSWORD);
	private final String CODIGO_CLIENTE = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RENIEC_CODIGO_CLIENTE);
	private final String CODIGO_ROL = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RENIEC_CODIGO_ROL);

	RequestConsultarPersona requestConsultarPersona = new RequestConsultarPersona();
	PersonaConsultada personaConsultada = new PersonaConsultada();
	Pagination pagination = new Pagination();

	public Auditoria getDataAuditoria(String pcName, String ipAddress, String macAddress, String usuario) throws Exception {
		Auditoria auditoria = new  Auditoria();
		
		
		auditoria.setUsuario(usuario);
		auditoria.setNombrePc(pcName);
		auditoria.setNumeroIp(ipAddress);
		auditoria.setDireccionMac(macAddress);

		return auditoria;
	}
	
	public TokenAuthResponse getTokenAuthentication() throws Exception {
		TokenAuthResponse response = null;
		String URIAuthentication = ENDPOINT_SERVICIO_RENIEC + "/api/authenticate";
		HttpHeaders headers = new HttpHeaders();
		headers.add("username",USERNAME);
		headers.add("password",PASSWORD);
		headers.add("codigoCliente",CODIGO_CLIENTE);
		headers.add("codigoRol",CODIGO_ROL);
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>  (headers);
		UriComponentsBuilder builderUrl = UriComponentsBuilder.fromUriString(URIAuthentication);
		
		try {
			ResponseEntity<TokenAuthResponse> responseEntity = restTemplate.postForEntity(builderUrl.buildAndExpand().toUri(),entity, TokenAuthResponse.class);
			if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
				response = responseEntity.getBody();
			} else {
				throw new Error("Ocurrio un error al obtener token de authentication"+responseEntity.getStatusCodeValue());
			}
		}catch(Exception e) {
			throw e;
		}
		return response;
	}
	
	public PersonaDTO obtenerPersonaPorDNI(String dni, String pcName, String ipAddress, String macAddress, String usuario) throws Exception {
		PersonaDTO persona = null;
		
		String URIConsultaReniec = ENDPOINT_SERVICIO_RENIEC+"/consultar/persona";
		
		personaConsultada.setTipoConsulta(TIPO_CONSULTA_SERVICIO_RENIEC_POR_DNI);
		personaConsultada.setNroDocumentoIdentidad(dni);
		pagination.setSize(1);
		pagination.setPage(0);
		requestConsultarPersona.setFormatoRespuesta("json");
		requestConsultarPersona.setConsultante(DNI_CONSULTANTE_SERVICIO_RENIEC);
		requestConsultarPersona.setMotivo("Obtener los apelidos y nombre de la persona");
		requestConsultarPersona.setPersonaConsultada(personaConsultada);
		requestConsultarPersona.setPagination(pagination);
		requestConsultarPersona.setAuditoria(getDataAuditoria(pcName,ipAddress,macAddress,usuario));
		
		try {
			
			TokenAuthResponse token = getTokenAuthentication();
			log.info("la respuesta del toke {}",token.getToken());
						
	        // Configurar los encabezados
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization", "Bearer " + token.getToken());
	        
	        UriComponentsBuilder builderUrl = UriComponentsBuilder.fromUriString(URIConsultaReniec);
	        // Crear la entidad de la solicitud HTTP con el cuerpo y los encabezados
	        HttpEntity<RequestConsultarPersona> requestEntity = new HttpEntity<>(requestConsultarPersona, headers);

	        // Realizar la solicitud HTTP POST al API
	        ResponseEntity<ReniecResponse> responseEntity =  restTemplate.exchange(builderUrl.buildAndExpand().toUri(), HttpMethod.POST, requestEntity, ReniecResponse.class);
	        if(responseEntity.getBody().getCodigo().equals("0000")) {
	        	persona = responseEntity.getBody().getData();	        	
	        }else {
	        	throw new Error("Ocurrio un error al consultar el servicio de RENIEC");
	        }
	        
		} catch (Exception e) {
		  throw e;
		}
		return persona;
	}

	/**
	 * Método para consultar personas que coincidan con los datos ingresados.
	 * @param primerApellido Primer apellido de la persona.
	 * @param segundoApellido Segundo apellido de la persona.
	 * @param nombres Nombre de la persona.
	 * @param totalRegistrosRetorna Cantidad de registros a retornar en la consulta.
	 * @param posicionRegistro Posición del total de registros desde donde se retornará la información.
	 * @return Lista de coincidencias.
	 * @throws Exception Excepción durante la ejecución del método.
	 */
	
	  public List<PersonaDTO> obtenerListPersonaPorNombres(String primerApellido,
		  String segundoApellido, String nombres, String totalRegistrosRetorna, String
		  posicionRegistro)throws Exception { List<PersonaDTO> lista = null; try{
		  ParametroConsultaDTO consulta = new ParametroConsultaDTO();
		  consulta.setReqDniConsultante(DNI_CONSULTANTE_SERVICIO_RENIEC);
		  consulta.setReqUsuario(USUARIO_CONSULTANTE_SERVICIO_RENIEC);
		  consulta.setReqIp(UtilsCore.getIPAddress(true));
		  consulta.setReqTipoConsulta(TIPO_CONSULTA_SERVICIO_RENIEC_POR_DATOS);
		  consulta.setReqNombres(!ValidarUtil.isNullOrEmpty(nombres)?nombres.
		  toUpperCase().trim():ConstantesSF.LETRA_VACIO);
		  consulta.setReqApellidoPaterno(!ValidarUtil.isNullOrEmpty(primerApellido)?
		  primerApellido.toUpperCase().trim():ConstantesSF.LETRA_VACIO);
		  consulta.setReqApellidoMaterno(!ValidarUtil.isNullOrEmpty(segundoApellido)?
		  segundoApellido.toUpperCase().trim():ConstantesSF.LETRA_VACIO);
		  consulta.setReqNroRegistros(totalRegistrosRetorna);
		  consulta.setReqGrupo(posicionRegistro);
		  consulta.setReqTrama(ConstantesSF.LETRA_VACIO);
		  consulta.setReqDni(ConstantesSF.LETRA_VACIO);
		  consulta.setReqDniApoderado(ConstantesSF.LETRA_VACIO);
		  consulta.setReqTipoVinculoApoderado(ConstantesSF.LETRA_VACIO);
		  //abrirConexion(); lista = proxy.consultaReniecPersonaPorDatos(consulta);
	  }catch (Exception ex){ ex.printStackTrace(); } return lista; }
	 
}
