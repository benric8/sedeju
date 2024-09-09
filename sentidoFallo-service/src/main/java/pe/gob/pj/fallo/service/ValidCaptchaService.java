package pe.gob.pj.fallo.service;

public interface ValidCaptchaService {
	
	public boolean validaCapctcha(String cuo, String token, String remoteIp) throws Exception;

}
