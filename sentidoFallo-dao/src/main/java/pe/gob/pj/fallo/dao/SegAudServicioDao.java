package pe.gob.pj.fallo.dao;

import pe.gob.pj.fallo.entity.User;

public interface SegAudServicioDao {
	
	public Long autenticarUsuario(final String codigoCliente, final String codigoAplicativo, final String usuario, final String clave, String cuo) throws Exception;
	
	public User getCredentialsAndRoles(String id) throws Exception;

}
