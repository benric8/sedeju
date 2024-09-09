package pe.gob.pj.fallo.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.fallo.bean.UsuarioSFBean;
import pe.gob.pj.fallo.dao.MaeUsuarioSFDao;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;
import pe.gob.pj.fallo.service.SegUsuarioService;
import pe.gob.pj.fallo.utils.ConfiguracionPropiedades;
import pe.gob.pj.fallo.utils.Propiedades;
import pe.gob.pj.fallo.utils.Utils;

@Service("segUsuarioService")
public class SegUsuarioServiceImpl implements SegUsuarioService, Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SegUsuarioServiceImpl.class);
	
	@Autowired
	private MaeUsuarioSFDao maeUsuarioSFDao;
	/*
	@Autowired
	private SegAudServicioDao segAudServicioDao;*/

	@Override
	@Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public Long autenticarUsuario(String codigoCliente, String codigoAplicativo, String usuario, String clave, String cuo) throws Exception {
		Long result = null;
		Long tiempoInicial = System.currentTimeMillis();
		try {
			String cCliente = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.COD_CLIENTE);
			String cAplicativo = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.COD_APLICATIVO);
			String cUsuario = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.COD_USUARIO);
			String password = ConfiguracionPropiedades.getInstance().getProperty(Propiedades.CONTRASENIA);
			//result = segAudServicioDao.autenticarUsuario(codigoCliente, codigoAplicativo, usuario, clave, cuo);   
			if(Utils.isNull(cCliente).equals(codigoCliente) && Utils.isNull(cAplicativo).equals(codigoAplicativo)
			   && Utils.isNull(cUsuario).equals(usuario) && Utils.isNull(password).equals(clave)) {
				result = 10L;
			} else {
				result = Long.valueOf(0);
				throw new Exception("Resultado nulo o vacio, credenciales inválidas");
			}
			/*if (result.equals(null)) {
				result = Long.valueOf(0);
				throw new Exception("Resultado nulo o vacio");
			}*/
		} catch (Exception ex) {
			logger.error(cuo+(ex.getMessage()));
			throw ex;
		} finally {
			logger.info(cuo+"****** PROCESO SRV M-SEG 01 DURACIÓN=[{}(ms)]", (System.currentTimeMillis() - tiempoInicial)+" ******");
		}
		return result;
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public UsuarioSFBean validarAcceso(String usuario, String clave, String cuo) throws Exception {
		return maeUsuarioSFDao.validarAcceso(usuario, clave, cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = { Exception.class, SQLException.class})
    public int actualizarContrasena(ContrasenaDTO contrasena, String cuo) throws Exception {
		return maeUsuarioSFDao.actualizarContrasena(contrasena, cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public UsuarioSFBean validarCorreo(String email, String cuo) throws Exception {
		return maeUsuarioSFDao.validarCorreo(email, cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public UsuarioSFBean validarToken(String token, Timestamp currentTime, String cuo) throws Exception {
		return maeUsuarioSFDao.validarToken(token, currentTime, cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = { Exception.class, SQLException.class})
	public int actualizarDatosRecuperacion(ContrasenaDTO contrasena, String token, int tipo, String cuo) throws Exception {
		return maeUsuarioSFDao.aactualizarDatosRecuperacion(contrasena, token, tipo, cuo);
	}
}
