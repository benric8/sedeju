package pe.gob.pj.fallo.utils;

public class SecurityConstants {

	 public static final String AUTH_LOGIN_URL = "/api/authenticate";

	    // Signing key for HS512 algorithm
	    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
	    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

	    // JWT token defaults
	    public static final String TOKEN_HEADER = "Authorization";
	    public static final String TOKEN_ADMIN = "TOKEN_ADMIN";
	    public static final String TOKEN_PREFIX = "Bearer ";
	    public static final String TOKEN_TYPE = "JWT";
	    public static final String TOKEN_ISSUER = "secure-api";
	    public static final String TOKEN_AUDIENCE = "secure-app";
	    
	    // CNFIGURACION BASE DE DATOS DINAMICA
	    public static final String LOCALIZACION="Zona";    
	    public static final String CRE_USERNAME="username";
	    public static final String CRE_PASSWORD="password";
	    public static final String CRE_COD_CLIENTE="codigoCliente";
	    public static final String CRE_COD_APLICATIVO="codigoAplicativo";

	    private SecurityConstants() {
	        throw new IllegalStateException("Cannot create instance of static util class");
	    }
}
