package pe.gob.pj.fallo.service.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import pe.gob.pj.fallo.service.EmailService;
import pe.gob.pj.fallo.utils.ConfiguracionPropiedades;
import pe.gob.pj.fallo.utils.MailBuilder;
import pe.gob.pj.fallo.utils.Propiedades;

@Service("emailService")
public class EmailServiceImpl implements Serializable, EmailService{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	
	
	@Override
	public boolean sendEmail(String textMensaje, String emailDestino, String subject)  {		
		try {
			return sendEmailTool(textMensaje, emailDestino, subject);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	private boolean sendEmailTool(String textMessage, String email,String subject) throws UnsupportedEncodingException {
		boolean send = false;	
		try {			
			String mailEmisor = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RECURSO_MAIL_EMISOR);
			String nomEmisor = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.RECURSO_MAIL_EMISOR_NAME);
			MimeMessage message = new MimeMessage(MailBuilder.build());
            message.setSubject(subject);
	        message.setContent(textMessage, "text/html; charset=utf-8");
	        message.setFrom(new InternetAddress(mailEmisor, nomEmisor));
            if (email.indexOf(',') > 0){
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	        }else{
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
	        }
	        Transport.send(message);
		} catch (Exception e) {
			logger.error("Hubo un error al enviar el mail: {}", e);
		}
		return send;
	}	
}
