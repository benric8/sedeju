package pe.gob.pj.fallo.app.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import pe.gob.pj.fallo.bean.UsuarioSFBean;
import pe.gob.pj.fallo.utils.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LogManager.getLogger(JwtAuthorizationFilter.class);    
    
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {
        request.setAttribute(Constants.AUD_CUO, Utils.obtenerCodigoUnico());
    	UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {   
    	String remoteIp = request.getRemoteAddr();
    	if(request.getRemoteAddr() == null) {
    		remoteIp = request.getRemoteHost();
    	}
    	String urlReq = request.getRequestURI();
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        String cuo = request.getAttribute(Constants.AUD_CUO).toString();
        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
        String tokenAdmin = "";
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {         	

                Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace("Bearer ", ""));

                String username = parsedToken
                    .getBody()
                    .getSubject();

                List<SimpleGrantedAuthority>  authorities = ((List<?>) parsedToken.getBody()
                    .get("rol")).stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toList());
                
                String ipRemotaDeToken = parsedToken.getBody().get("remoteIp").toString();
                if(!remoteIp.equals(ipRemotaDeToken)) {
                	return null;
                }

                if (StringUtils.isNotEmpty(username)) {
                    request.setAttribute(Constants.AUD_CUO, cuo);
                    if(urlReq.endsWith("validarCaptcha") || urlReq.endsWith("refresh") || urlReq.endsWith("buscarSentidosYFallos") || urlReq.endsWith("login") || urlReq.contains("seguridad/recuperaContrasenia") ) {
                        request.setAttribute(Constants.REMOTE_IP, remoteIp);
                    }
                    boolean entrar = urlReq.endsWith("refresh") && urlReq.contains("admin") ? true: false;
                    if((urlReq.contains("admin") || entrar) && !urlReq.endsWith("login") ) {
                    	 tokenAdmin = request.getHeader(SecurityConstants.TOKEN_ADMIN);
                    	 Jws<Claims> parsedTokenAdmi = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(tokenAdmin);
                    	 /*HashMap<String, Object> usuario =  (HashMap<String, Object>) parsedTokenAdmi.getBody().get("usuario");
                    	 System.out.println(usuario.get("nPerfil"));*/
                    	 
                    	 ObjectMapper objectmaper = new ObjectMapper();
                    	 UsuarioSFBean usuarioSFBean = objectmaper.convertValue(parsedTokenAdmi.getBody().get("usuario"), UsuarioSFBean.class);
                    	 
                    	
                    	String usuarioLogueado = usuarioSFBean.getcUsuario();
                    	if(usuarioLogueado==null || usuarioLogueado.isEmpty()) {
                    		request.setAttribute("usuarioLogueado", username);
                    	} else {
                    		request.setAttribute("usuarioLogueado", usuarioLogueado);
                    	}
                    	 
                    	 if(parsedTokenAdmi.getBody().getSubject().toString().length() <= 0) {
                    		 return null;
                    	 }
                    } else if (urlReq.endsWith("refresh") && !urlReq.endsWith("login")) {
                    	tokenAdmin = request.getHeader(SecurityConstants.TOKEN_ADMIN);
                   	    //Jws<Claims> parsedTokenAdmi = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(tokenAdmin);
                    	boolean validTokenAdmin = (urlReq.contains("admin") || tokenAdmin.length()> 0);
                    	request.setAttribute(Constants.TOKEN_ADMIN, tokenAdmin == null? "": tokenAdmin);
                   	    if(remoteIp.equals(ipRemotaDeToken)) {
                   	    	request.setAttribute(Constants.AUD_CUO, cuo);
    	 					request.setAttribute(Constants.REMOTE_IP, remoteIp);
    	 					request.setAttribute(Constants.VALID_TOKEN_ADMIN, validTokenAdmin);
                   	    }                   	    
                    }
                	return new UsernamePasswordAuthenticationToken(username, null, authorities);
                }
            } catch (ExpiredJwtException exception) {
				String ipRemotaToken = exception.getClaims().get("remoteIp").toString();
				int total = (int) exception.getClaims().get("numero");				
				String subject = exception.getClaims().getSubject();
				if(tokenAdmin.length() == 0) {
					tokenAdmin = request.getHeader(SecurityConstants.TOKEN_ADMIN);
                    tokenAdmin = tokenAdmin==null?"":tokenAdmin;
				}
				boolean validTokenAdmin = (urlReq.contains("admin") || tokenAdmin.length()> 0);
				if(remoteIp.equals(ipRemotaToken) && total <  100 && urlReq.endsWith("refresh")) {	
					request.setAttribute(Constants.TOKEN_ADMIN, tokenAdmin);
					List<SimpleGrantedAuthority>  authorities = ((List<?>) exception.getClaims()
		                    .get("rol")).stream()
		                    .map(authority -> new SimpleGrantedAuthority((String) authority))
		                    .collect(Collectors.toList());
					request.setAttribute(Constants.AUD_CUO, cuo);
					request.setAttribute(Constants.REMOTE_IP, remoteIp);
					request.setAttribute(Constants.VALID_TOKEN_ADMIN, validTokenAdmin);
					return new UsernamePasswordAuthenticationToken(subject, null, authorities);
				}
                log.warn(cuo+"Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn(cuo+"Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn(cuo+"Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                log.warn(cuo+"Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn(cuo+"Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            } catch (Exception e) {
            	log.error(cuo+"No se optubo owner y código de BASE DE DATOS: "+Utils.convertExceptionToString(e));
				e.printStackTrace();
			}
        }
        log.error(cuo+" Token enviado incorrecto: "+token);
        return null;
    }
            
}