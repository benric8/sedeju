package pe.gob.pj.fallo.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
	
	/* Para recuperar credenciales de consume de servicio*/
	@Bean(name = "seguridadDS")
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:/SegAudSentidoFalloDS");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}
	
	@Bean(name = "txManagerSeguridad")
	public DataSourceTransactionManager getTransactionManagerSeguridad(@Qualifier("seguridadDS") DataSource seguridadDs) throws IllegalArgumentException, NamingException, SQLException {
      DataSourceTransactionManager tm = new DataSourceTransactionManager();
	  tm.setDataSource(seguridadDs);
	  tm.setDefaultTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
	  tm.setRollbackOnCommitFailure(true);
	  tm.setGlobalRollbackOnParticipationFailure(true);
	  tm.afterPropertiesSet();
	  return tm;
	}
	
	@Bean(name = "seguridadJT")
    @Autowired
    public JdbcTemplate seguridadJT(@Qualifier("seguridadDS") DataSource seguridaDS) {
        return new JdbcTemplate(seguridaDS);
    }
	
	// Para conectarse con BD Centralizada 
	@Bean(name = "sentidoFalloWEBDS")
	public DataSource sijCentralizadaDS() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:/SentidoFalloWEBDS");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}
	/*
	@Bean(name = "txManagerSijCentralizada")
	public DataSourceTransactionManager getTransactionManagerSijCentralizada(@Qualifier("sijCentralizadaDS") DataSource sijCentralizada) throws IllegalArgumentException, NamingException, SQLException {
      DataSourceTransactionManager tm = new DataSourceTransactionManager();
	  tm.setDataSource(sijCentralizada);
	  tm.setDefaultTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
	  tm.setRollbackOnCommitFailure(true);
	  tm.setGlobalRollbackOnParticipationFailure(true);
	  tm.afterPropertiesSet();
	  return tm;
	} */
	
	@Bean(name = "sessionPrincipal")
	public SessionFactory getSessionFactory(@Qualifier("sentidoFalloWEBDS") DataSource sijCentralizada) throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setPackagesToScan("pe.gob.pj.fallo");
		sessionFactoryBean.setHibernateProperties(getHibernateProperties());
		sessionFactoryBean.setDataSource(sijCentralizada);
		sessionFactoryBean.afterPropertiesSet();

		return sessionFactoryBean.getObject();
	}

	@Bean(name = "txManagerCentralizada")
	public HibernateTransactionManager getTransactionManager(@Qualifier("sessionPrincipal") SessionFactory sessionFactory) throws IOException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	} 
	

	private static Properties getHibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.SybaseDialect");
		hibernateProperties.put("hibernate.show_sql", true);
		// other properties
		return hibernateProperties;
	}
}
