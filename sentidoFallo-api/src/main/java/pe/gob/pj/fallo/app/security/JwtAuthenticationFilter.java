package pe.gob.pj.fallo.app.security;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pe.gob.pj.fallo.service.SegUsuarioService;
import pe.gob.pj.fallo.utils.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger logger = LogManager.getLogger(JwtAuthorizationFilter.class);

	/*@Autowired
	private SegUsuarioService segUsuarioService;*/

	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SegUsuarioService service) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
		//this.setSegUsuarioService(service);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {		
		String cuo= Utils.obtenerCodigoUnico();
		String username = request.getHeader(SecurityConstants.CRE_USERNAME);
		String password = request.getHeader(SecurityConstants.CRE_PASSWORD);
		String codigoCliente = request.getHeader(SecurityConstants.CRE_COD_CLIENTE);
		String codigoAplicativo= request.getHeader(SecurityConstants.CRE_COD_APLICATIVO);
		Long idUsuario = 0L;
		try {
			username= EncryptUtils.decryptPastFrass(username);
			//password= EncryptUtils.decryptPastFrass(password);
			byte[] decodedBytes = Base64.getDecoder().decode(password);
			password = new String(decodedBytes);
			//idUsuario = segUsuarioService.autenticarUsuario(codigoCliente, codigoAplicativo, username, password,cuo);
			String cCliente = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.COD_CLIENTE);
			String cAplicativo = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.COD_APLICATIVO);
			String cUsuario = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.COD_USUARIO);
			String passwordP = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.CONTRASENIA);   
			if(Utils.isNull(cCliente).equals(codigoCliente) && Utils.isNull(cAplicativo).equals(codigoAplicativo)
			   && Utils.isNull(cUsuario).equals(username) && Utils.isNull(passwordP).equals(password)) {
				idUsuario = 10L;
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error(cuo+"ERROR AUTENTIFICANDO USUARIO CON BASE DE DATOS DE SEGURIDAD :"+Utils.convertExceptionToString(e));
			return null;
		}
		if (idUsuario == 10L) {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(idUsuario.toString(), password));
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain, Authentication authentication) throws IOException {
		User user = ((User) authentication.getPrincipal());
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

		String token = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
				.setIssuer(SecurityConstants.TOKEN_ISSUER)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE)
				.setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 300000))
				.claim("rol", roles)
				.claim("remoteIp", request.getRemoteAddr())
				.claim("numero", 1)
				.compact();
		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
		response.setContentType("application/json");
		response.getWriter().write("{\"token\":\""+token+"\"}");
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		logger.error("ERROR CON LA UTORIZACION DE SPRING SECURITY: "+failed.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
/*
	public void setSegUsuarioService(SegUsuarioService segUsuarioService) {
		this.segUsuarioService = segUsuarioService;
	}	*/
}