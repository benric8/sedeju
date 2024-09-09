package pe.gob.pj.fallo.service;

public interface EmailService {

	public boolean sendEmail(String textMensaje, String emailDestino, String subject) ;
}
