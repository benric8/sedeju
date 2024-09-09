package pe.gob.pj.fallo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "pe.gob.pj.fallo.*" })
@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class, SecurityAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class SentidoFalloAplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SentidoFalloAplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SentidoFalloAplication.class, args);
	}
}