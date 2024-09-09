package pe.gob.pj.fallo.utils;

import javax.mail.Session;
import java.util.Properties;

/**
 * Propiedades de conexion de envio de email
 * @author Madiazr
 */
public class MailBuilder {   

    public static final Session build() throws Exception{
        Properties propiedades = new Properties();
        try{
        	String host = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RECURSO_MAIL_HOST);
        	String protocol = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RECURSO_MAIL_PROTOCOLO);
        	String port = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RECURSO_MAIL_PORT);
        	String mailLocal = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RECURSO_MAIL_LOCALHOST);
        	String mailEmisor = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RECURSO_MAIL_EMISOR);
        	
            propiedades.setProperty(Propiedades.RECURSO_MAIL_HOST, host);
            propiedades.setProperty(Propiedades.RECURSO_MAIL_PROTOCOLO, protocol);
            propiedades.setProperty(Propiedades.RECURSO_MAIL_PORT, port);
            propiedades.setProperty(Propiedades.RECURSO_MAIL_LOCALHOST, mailLocal);
            propiedades.setProperty(Propiedades.RECURSO_MAIL_EMISOR, mailEmisor);

            return Session.getDefaultInstance(propiedades,null);
            //mail = new MailImpl(session, mailProperties.getSmtpCorreoEmisor());
           // return session;
        }catch (Exception ex){
            throw new Exception(ex.getMessage(), ex.getCause());
        }
    }
}