package pe.gob.pj.fallo.utils;


import java.io.Serializable;

public class Propiedades implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CODIGO_INSTANCIA = "sentido.fallo.codigo.instancia";
	public static final String CODIGO_ESPECIALIDAD = "sentido.fallo.codigo.especialidad";
	public static final String URL_MANUAL = "sentido.fallo.url.manual";
	public static final String DATASOURCE = "hibernate.connection.datasource";
	public static final String RENIEC_ENDPOINT = "sentido.fallo.reniec.endpoint";
	public static final String RENIEC_DNI_CONSULTA = "sentido.fallo.reniec.dni";
	public static final String RENIEC_USUARIO_CONSULTA = "sentido.fallo.reniec.usuario";
	public static final String CAPTCHA_TOKEN = "sentido.fallo.captcha.token";
	public static final String CAPTCHA_URL = "sentido.fallo.captcha.url";
	public static final String CAPTCHA_ACTIVO = "sentido.fallo.captcha.activo";
	
	public static final String COD_CLIENTE = "sentido.fallo.seguridad.codCliente";
	public static final String COD_APLICATIVO = "sentido.fallo.seguridad.codAplicativo";
	public static final String COD_ROL = "sentido.fallo.seguridad.rol";
	public static final String COD_USUARIO = "sentido.fallo.seguridad.usuario";
	public static final String CONTRASENIA = "sentido.fallo.seguridad.claveUsuario";
	
	public static final String RECURSO_MAIL_HOST = "mail.host";
    public static final String RECURSO_MAIL_PROTOCOLO = "mail.transport.protocol";
    public static final String RECURSO_MAIL_PORT = "mail.smtp.port";
    public static final String RECURSO_MAIL_EMISOR = "mail.smtp.mail.sender";
    public static final String RECURSO_MAIL_LOCALHOST = "mail.smtp.localhost";
    public static final String RECURSO_MAIL_EMISOR_NAME = "mail.smtp.mail.name.sender";
    
    public static final String SENTIDO_FALLO_URL_WEB = "sentido.fallo.urlWeb";
    
    public static final String TIMEOUT_API_CONECTION = "timeout.client.api.conection.segundos";
    public static final String TIMEOUT_API_READ= "timeout.client.api.read.segundos";
    
    public static final String RENIEC_USERNAME= "servicio.reniec.seguridad.usuario";
    public static final String RENIEC_PASSWORD= "servicio.reniec.seguridad.password";
    public static final String RENIEC_CODIGO_CLIENTE= "servicio.reniec.seguridad.codigocliente";
    public static final String RENIEC_CODIGO_ROL= "servicio.reniec.seguridad.codigorol";
    
    
    
    
}