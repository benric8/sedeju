package pe.gob.pj.fallo.service.security;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.fallo.entity.Role;
import pe.gob.pj.fallo.entity.User;
import pe.gob.pj.fallo.utils.ConfiguracionPropiedades;
import pe.gob.pj.fallo.utils.Propiedades;
import pe.gob.pj.fallo.utils.Utils;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService, Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	/*
	@Autowired
	private SegAudServicioDao segAudServicioDao;	*/

	@Override
    @Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("******** REGISTRANDO ACCESOS EN SPRING SECURITY DINAMICAMENTE ********");
		User user=new User();		
		try {
			//user=segAudServicioDao.getCredentialsAndRoles(username);
			if(Utils.isLong(username) > 0) {
				String cUsuario = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.COD_USUARIO);
				String password = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.CONTRASENIA);
				String rolName = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.COD_ROL);
				user.setId(1);
				user.setPassword(passwordEncoder.encode(password));
				user.setName(cUsuario);
				List<Role> roles= new ArrayList<Role>();
				Role rol= new Role();
				rol.setId(1);
				rol.setName(rolName);
				roles.add(rol);
				user.setRoles(roles);
			} else {
				throw new Exception("Usuario con ID:  " + username + " not found");
			}
			
			/*int i=1;
			for (Role role : user.getRoles()) {
				Role rol= new Role();
				rol.setId(i);
				rol.setName(role.getName());
				roles.add(rol);
				i++;
			}	*/					
		} catch (Exception e) {
			logger.debug("ERROR AL RECUPERAR USUARIO Y ROLES PARA SPRING SECURITY: "+Utils.convertExceptionToString(e));			
			e.printStackTrace();
			new UsernameNotFoundException("Usuario con ID:  " + username + " not found");
		}	
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),getAuthorities(user));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}
}

