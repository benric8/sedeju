package pe.gob.pj.fallo.utils;


import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.Set;

public class ConfiguracionPropiedades implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	Properties settings = null;
	public final static String PROPERTIES_FILENAME = "sentidoFallo-web.properties";

	private ConfiguracionPropiedades() {
		settings = new Properties();
		try {
			settings.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILENAME));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static ConfiguracionPropiedades getInstance() {
		return ConfigurationHolder.INSTANCE;
	}

	private static class ConfigurationHolder {
		private static final ConfiguracionPropiedades INSTANCE = new ConfiguracionPropiedades();
	}

	public String getProperty(String key) {
		if (settings == null) {
			return null;
		} else {
			return settings.getProperty(key);
		}
	}

	public Set<Object> getKeys() {
		if (settings == null) {
			return null;
		} else {
			return settings.keySet();
		}
	}
}
