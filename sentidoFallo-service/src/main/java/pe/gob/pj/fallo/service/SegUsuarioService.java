package pe.gob.pj.fallo.service;

import java.sql.Timestamp;

import pe.gob.pj.fallo.bean.UsuarioSFBean;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;

public interface SegUsuarioService {

	public Long autenticarUsuario(String codigoCliente, String codigoAplicativo, String usuario, String clave, String cuo) throws Exception;

	public UsuarioSFBean validarAcceso(String usuario, String clave, String cuo) throws Exception;

	public int actualizarContrasena(ContrasenaDTO contrasena, String cuo) throws Exception;
	
    public UsuarioSFBean validarCorreo(String email, String cuo) throws Exception;
	
	public UsuarioSFBean validarToken(String token, Timestamp currentTime, String cuo) throws Exception;
	
	public int actualizarDatosRecuperacion(ContrasenaDTO contrasena, String token, int tipo, String cuo) throws Exception;
}
