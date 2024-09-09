package pe.gob.pj.fallo.dao;

import java.sql.Timestamp;

import pe.gob.pj.fallo.bean.UsuarioSFBean;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;
import pe.gob.pj.fallo.entity.MovPersonaReniec;

public interface MaeUsuarioSFDao {

	public UsuarioSFBean validarAcceso(String usuario, String clave, String cuo) throws Exception;
	
	public UsuarioSFBean validarCorreo(String email, String cuo) throws Exception;
	
	public UsuarioSFBean validarToken(String token, Timestamp currentTime, String cuo) throws Exception;

	public int actualizarContrasena(ContrasenaDTO contrasena, String cuo) throws Exception;
	
	public int aactualizarDatosRecuperacion(ContrasenaDTO contrasena, String token, int tipo, String cuo) throws Exception;
	
	public MovPersonaReniec validarPersonaReniec(String dni, String cuo) throws Exception;
	
	public void registrarPersona(MovPersonaReniec persona, String cuo) throws Exception;

}
