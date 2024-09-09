package pe.gob.pj.fallo.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import pe.gob.pj.fallo.service.SegUsuarioService;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SegUsuarioService segUsuarioService;
	
	@Autowired
	private UserDetailsService customUserDetailsService;


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		http.cors().and().csrf().disable()		
		        .authorizeRequests()
		        .antMatchers("/").permitAll()
		        .antMatchers("/encriptarClaves").permitAll()
		        .antMatchers("/css/**").permitAll()
		        .anyRequest().authenticated()
		        .and()
		        .addFilter(new JwtAuthorizationFilter(authenticationManager()))
				.addFilter(new JwtAuthenticationFilter(authenticationManager(), segUsuarioService))				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {	
		//auth.authenticationProvider(authenticationProvider)
		/*auth.inMemoryAuthentication().withUser("INTER_USER").password(passwordEncoder().encode("123456"))
				.authorities("ROLE_USER");*/
		auth
    	.userDetailsService(customUserDetailsService)
    	.passwordEncoder(passwordEncoder());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void setSegUsuarioService(SegUsuarioService segUsuarioService) {
		this.segUsuarioService = segUsuarioService;
	}	
}