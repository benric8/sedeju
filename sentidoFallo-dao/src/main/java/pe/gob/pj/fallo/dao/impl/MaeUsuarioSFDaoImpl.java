package pe.gob.pj.fallo.dao.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.fallo.bean.UsuarioSFBean;
import pe.gob.pj.fallo.dao.MaeUsuarioSFDao;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;
import pe.gob.pj.fallo.entity.MaeUsuario;
import pe.gob.pj.fallo.entity.MovPersonaReniec;
import pe.gob.pj.fallo.utils.ConstantesSF;
import pe.gob.pj.fallo.utils.Convierte;
import pe.gob.pj.fallo.utils.UtilsCore;

@Component("maeUsuarioSFDao")
public class MaeUsuarioSFDaoImpl implements MaeUsuarioSFDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MaeUsuarioSFDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionPrincipal")
	private SessionFactory factoria;
	
	@Override
    public UsuarioSFBean validarAcceso(String usuario, String clave, String cuo) throws Exception {
    	UsuarioSFBean usuarioSFBean = null;
    	logger.info(cuo+" MaeUsuarioSFDaoImpl.validarAcceso inicio");
    	Query<MaeUsuario> query = factoria.getCurrentSession().createNamedQuery(MaeUsuario.LOGIN_USER, MaeUsuario.class);
    	query.setParameter(MaeUsuario.P_USUARIO, usuario);
    	query.setParameter(MaeUsuario.P_CLAVE, clave);
    	MaeUsuario user = query.getSingleResult();
    	if(user != null) {
    		usuarioSFBean = new UsuarioSFBean();
    		usuarioSFBean.setnUsuario(user.getNUsuario());
            usuarioSFBean.setcUsuario(user.getCUsuario());
            usuarioSFBean.setxNomUsuario(user.getXNomUsuario());
            usuarioSFBean.setxApePaterno(user.getXApePaterno());
            usuarioSFBean.setxApeMaterno(user.getXApeMaterno());
            usuarioSFBean.setxDocIdentidad(user.getXDocIdentidad());
            usuarioSFBean.setxNomOrgJurisd(user.getXNomOrgjurisd());
            usuarioSFBean.setlActivo(user.getLActivo());
            usuarioSFBean.setnPerfil(user.getMaePerfil().getNPerfil());
            usuarioSFBean.setcDistritoJudicial(user.getMaeDistritosJudiciale().getC_distritoJudicial());
            usuarioSFBean.setxPerfil(user.getMaePerfil().getXDescripcion());
            usuarioSFBean.setxDistritoJudicial(user.getMaeDistritosJudiciale().getX_nomDistrito());
            usuarioSFBean.setlReseteo(user.getLReseteo());
            usuarioSFBean.setlReniec(user.getLReniec());
    	}
    	return usuarioSFBean;
    }	
	
	@Override
    public UsuarioSFBean validarCorreo(String email, String cuo) throws Exception {
    	UsuarioSFBean usuarioSFBean = null;    	
    	try {
    		logger.info(cuo+" Validando correo");
        	Query<MaeUsuario> query = factoria.getCurrentSession().createNamedQuery(MaeUsuario.FIND_BY_EMAIL, MaeUsuario.class);
        	query.setParameter(MaeUsuario.P_EMAIL, email.toUpperCase());
        	MaeUsuario user = query.getSingleResult();
        	if(user != null) {
        		usuarioSFBean = new UsuarioSFBean();
        		usuarioSFBean.setnUsuario(user.getNUsuario());
                usuarioSFBean.setcUsuario(user.getCUsuario());
                usuarioSFBean.setlReniec(user.getLReniec());
                usuarioSFBean.setxNomUsuario(user.getXNomUsuario());
        	}
		} catch (NoResultException e) {
			logger.error(cuo.concat(e.getMessage()));
		}
    	return usuarioSFBean;
    }	
	
	@Override
    public UsuarioSFBean validarToken(String token, Timestamp currentTime, String cuo) throws Exception {    
		UsuarioSFBean usuarioSFBean = null;
    	try {    		
        	logger.info(cuo+" Validando correo");
        	Query<MaeUsuario> query = factoria.getCurrentSession().createNamedQuery(MaeUsuario.FIND_BY_TOKEN, MaeUsuario.class);
        	query.setParameter(MaeUsuario.P_TOKEN, token);
        	query.setParameter(MaeUsuario.P_FACTUAL, currentTime);
        	MaeUsuario user = query.getSingleResult();
        	if(user != null) {
        		usuarioSFBean = new UsuarioSFBean();
        		usuarioSFBean.setnUsuario(user.getNUsuario());
                usuarioSFBean.setcUsuario(user.getCUsuario());
        	}
		} catch (NoResultException e) {
			logger.error(cuo.concat(e.getMessage()));
		}
    	return usuarioSFBean;
    }	

	@SuppressWarnings("rawtypes")
	@Override
    public int actualizarContrasena(ContrasenaDTO contrasena, String cuo) throws Exception {    	
		logger.info(cuo+" MaeUsuarioSFDaoImpl.actualizarContrasena inicio");
        String numIp = UtilsCore.getIPAddress(true);
        String mcAddr = UtilsCore.getMACAddress();
        String pcName = UtilsCore.getPCName();
        String usuario = String.valueOf(contrasena.getnUsuario());
        Long nUsuario = new Long(contrasena.getnUsuario());
		Query query = factoria.getCurrentSession().createNamedQuery( MaeUsuario.UPD_CAMBIO_CLAVE );
		query.setParameter("nueva", contrasena.getNuevaContrasena());
		query.setParameter("usuario", usuario);
		query.setParameter("nUsuario", nUsuario);
		query.setParameter("pcname", pcName);
		query.setParameter("numip", numIp);
		query.setParameter("mcaddr", mcAddr);
		query.setParameter("actual", contrasena.getContrasenaActual());
		query.setParameter("reseteo", ConstantesSF.INACTIVO);
		int resultado = query.executeUpdate();
    	return resultado;
    }	
	
	@SuppressWarnings("rawtypes")
	@Override
    public int aactualizarDatosRecuperacion(ContrasenaDTO contrasena, String token, int tipo, String cuo) throws Exception {    	
		logger.info(cuo+" MaeUsuarioSFDaoImpl.actualizarContrasena inicio");
		int resultado = -1;
        String numIp = UtilsCore.getIPAddress(true);
        String mcAddr = UtilsCore.getMACAddress();
        String pcName = UtilsCore.getPCName();
        String usuario = String.valueOf(contrasena.getnUsuario());
        Long nUsuario = new Long(contrasena.getnUsuario());
        if(tipo == 1) {
        	Date current = new Date();
        	Query query = factoria.getCurrentSession().createNamedQuery( MaeUsuario.UPD_GENERA_TOKEN_RESETEO );    		
    		query.setParameter("usuario", usuario);
    		query.setParameter("nUsuario", nUsuario);
    		query.setParameter("pcname", pcName);
    		query.setParameter("numip", numIp);
    		query.setParameter("mcaddr", mcAddr);
    		//query.setParameter("actual", contrasena.getContrasenaActual());
    		query.setParameter("token", token);
    		query.setParameter("fechaToken", Convierte.addDay(current, 1));
    		resultado = query.executeUpdate();
        } else {
        	Query query = factoria.getCurrentSession().createNamedQuery( MaeUsuario.UPD_CONFIRMA_TOKEN_RESETEO );
    		query.setParameter("nueva", contrasena.getNuevaContrasena());
    		query.setParameter("usuario", usuario);
    		query.setParameter("nUsuario", nUsuario);
    		query.setParameter("pcname", pcName);
    		query.setParameter("numip", numIp);
    		query.setParameter("mcaddr", mcAddr);
    		resultado = query.executeUpdate();
        }		
    	return resultado;
    }
	
	@Override
    public MovPersonaReniec validarPersonaReniec(String dni, String cuo) throws Exception {
		MovPersonaReniec persona = null;    	
    	try {
    		logger.info(cuo+" Validando persona");
        	Query<MovPersonaReniec> query = factoria.getCurrentSession().createNamedQuery(MovPersonaReniec.VALIDAR_PERSONA, MovPersonaReniec.class);
        	query.setParameter(MovPersonaReniec.P_DNI, dni);
        	persona = query.getSingleResult();        	
		} catch (NoResultException e) {
			logger.error(cuo.concat(e.getMessage()));
		}
    	return persona;
    }	
	
	@Override
    public void registrarPersona(MovPersonaReniec persona, String cuo) throws Exception {  	
    	try {
    		logger.info(cuo+"registrando persona "+persona.getxDni());
    		factoria.getCurrentSession().save(persona);   	
		} catch (NoResultException e) {
			logger.error(cuo.concat(e.getMessage()));
		}
    }	
}