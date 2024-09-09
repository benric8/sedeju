package pe.gob.pj.fallo.dao.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import pe.gob.pj.fallo.dao.SegAudServicioDao;
import pe.gob.pj.fallo.entity.Role;
import pe.gob.pj.fallo.entity.User;

@Component("segAudServicioDao")
public class SegAudServicioDaoImpl implements SegAudServicioDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(SegAudServicioDaoImpl.class);
	
	@Autowired	
	private JdbcTemplate seguridadJT;

	@Override
	public Long autenticarUsuario(final String codigoCliente, final String codigoAplicativo, final String usuario, final String clave, String cuo)	throws  Exception {
		Long tiempoInicial = System.currentTimeMillis();
		Long idUsuario = 0L;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.seguridadJT)
			        .withProcedureName("SP_SEG_VALIDAR_USUARIO_CLAVE")
			        .withoutProcedureColumnMetaDataAccess()			        
			        .useInParameterNames("ID_USUARIO").declareParameters(new SqlOutParameter("ID_USUARIO", Types.INTEGER))
					.useInParameterNames("C_COD_CLIENTE").declareParameters(new SqlParameter("C_COD_CLIENTE", Types.VARCHAR))
					.useInParameterNames("C_COD_APLICATIVO").declareParameters(new SqlParameter("C_COD_APLICATIVO", Types.VARCHAR))
					.useInParameterNames("C_USER").declareParameters(new SqlParameter("C_USER", Types.VARCHAR))
					.useInParameterNames("C_CLAVE").declareParameters(new SqlParameter("C_CLAVE", Types.VARCHAR));
					
			logger.info(cuo.concat("SP_SEG_VALIDAR_USUARIO_CLAVE["+codigoCliente+","+codigoAplicativo+","+usuario+"]"));
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("ID_USUARIO", idUsuario)
					.addValue("C_COD_CLIENTE", codigoCliente)
					.addValue("C_COD_APLICATIVO", codigoAplicativo)
					.addValue("C_USER", usuario)
					.addValue("C_CLAVE", clave);					
			Map<String, Object> out = simpleJdbcCall.execute(in);	
			if (out != null && !out.isEmpty()) {
				idUsuario = Long.parseLong(out.get("ID_USUARIO").toString());
			}	  
		} catch (EmptyResultDataAccessException ex) {
			logger.error(cuo+ex.getMessage());
			throw ex;
		}catch (Exception ex) {
			logger.error(ex.getMessage());
			if (ex.getMessage() != null && ex.getMessage().contains("time out")|| ex.getMessage().contains("timeout")) {
				logger.error(cuo+" NO SE PUDO AUTENTICAR CON BASE DE DATOS DE SEGURIDAD EXCEPTION TIMEOUT ");
				logger.error(cuo+" EXCEPTION: "+ex.getMessage());
				throw ex;
			} else if (ex.getMessage() != null && ex.getMessage().contains("Connection refused")) {
				logger.error(cuo+" NO SE PUDO AUTENTICAR CON BASE DE DATOS DE SEGURIDAD EXCEPTION CONNECTION REFUSED ");
				logger.error(cuo+" EXCEPTION: "+ex.getMessage());
				throw ex;
			} else {
				ex.printStackTrace();
				throw ex;
			}
		} finally {
			logger.info(cuo+"****** PROCESO DAO M-SEG 01 DURACIÓN=[{}(ms)]", (System.currentTimeMillis() - tiempoInicial)+" ******");
		}
		return idUsuario;
	}
	
	@Override
	public User getCredentialsAndRoles(String id) throws Exception{
		Long tiempoInicial = System.currentTimeMillis();
		List<Role> lista= new ArrayList<Role>();
		User u = new User();
		StringBuilder sql= new StringBuilder("");
		try {
			sql.append("SELECT SU.C_USER,SU.C_COD_USUARIO,SU.C_CLAVE,R.C_COD_ROL ");
			sql.append("FROM SEG_USUARIO SU ");
			sql.append("INNER JOIN SEG_ROL_USUARIO RUS ON SU.ID_USUARIO=RUS.ID_USUARIO ");
			sql.append("INNER JOIN SEG_ROL R ON R.ID_ROL=RUS.ID_ROL ");
			sql.append("WHERE SU.ID_USUARIO="+id);
			sql.append(" AND SU.L_ESTADO=1");			      
			List<Map<String, Object>> rows = seguridadJT.queryForList(sql.toString());
			for (@SuppressWarnings("rawtypes") Map row : rows) {				
				Role r = new Role();
				u.setName((String) row.get("C_USER"));
				u.setEmail((String) row.get("C_COD_USUARIO"));
				u.setPassword((String) row.get("C_CLAVE"));
				r.setName((String) row.get("C_COD_ROL"));
				lista.add(r);				
			}
			u.setRoles(lista);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			logger.info("****** PROCESO DAO M-SEG 01 DURACIÓN=[{}(ms)]", (System.currentTimeMillis() - tiempoInicial)+" ******");
		}
		return u;
	}

}
