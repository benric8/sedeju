package pe.gob.pj.fallo.dao;

import java.util.List;
import java.util.Map;

import pe.gob.pj.fallo.dto.UsuarioDTO;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;

public interface MaeUsuarioDao {

	public List<UsuarioDTO> consultarUsuario(Map<String, Object> params, String cuo) throws Exception;

	public UsuarioDTO buscarUsuario(Long nUsuario, String cuo) throws Exception;

	public Long registrarUsuario(UsuarioDTO usuario, String cuo) throws Exception;

	public int modificarUsuario(UsuarioDTO usuario, String cuo) throws Exception;

	public List<ComboDTO> listarPerfiles() throws Exception;

	public List<ComboDTO> listarDistritosJudiciales() throws Exception;

	public int resetearContrasena(ContrasenaDTO contrasena, String cuo) throws Exception;

	public int validarUsuario(UsuarioDTO usuario) throws Exception;

}
