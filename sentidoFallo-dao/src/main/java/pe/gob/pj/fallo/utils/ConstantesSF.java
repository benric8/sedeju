package pe.gob.pj.fallo.utils;

import java.io.Serializable;

public class ConstantesSF implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String CUO_PREFIX 	= "cuo";
	public static final String ACTIVO		= "1";
	public static final String INACTIVO		= "0";
	public static final String LETRA_VACIO 	= "";
	
	public static class Resultado{
		public static final String COD_EXITO = "0000";
		public static final String COD_ERROR_VALID_RENIEC = "5555";
		public static final String VALIDADO = "VALIDADO";
		public static final String MODIFICADO = "MODIFICADO";
		public static final String REGISTRADO = "REGISTRADO";
		public static final String COD_ERROR = "0001";
		public static final String ERROR = "ERROR";
	}
	
	public static class Operacion{
		public static final String INSERTAR = "I";
		public static final String ACTUALIZAR = "U";
		public static final String UC_CONEXION = "uc_SentidoFallo_web";
	}
	
	public static class Maestro{
		public static final String PERFIL = "PERFIL";
		public static final String DISTRITO = "DISTRITO";
	}
	
	public static class Perfiles{
		public static final long ADMINISTRADOR = 1;
		public static final long JUEZ = 2;
	}
	
	public static class Email{
		public static final String SUBJECT = "RECUPERACIÓN DE CONTRASEÑA - APLICATIVO SENTIDO FALLO";
	}
}
