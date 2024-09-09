package pe.gob.pj.fallo.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.fallo.dao.MaeUsuarioDao;
import pe.gob.pj.fallo.dao.MaeUsuarioSFDao;
import pe.gob.pj.fallo.dto.UsuarioDTO;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;
import pe.gob.pj.fallo.entity.MovPersonaReniec;
import pe.gob.pj.fallo.service.ManUsuarioService;
import pe.gob.pj.fallo.utils.ConstantesSF;

@Service("manUsuarioService")
public class ManUsuarioServiceImpl implements ManUsuarioService {
	
	@Autowired
	private MaeUsuarioDao maeUsuarioDao;
	
	@Autowired
	private MaeUsuarioSFDao maeUsuarioSFDao;

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public List<UsuarioDTO> consultarUsuario(Map<String, Object> params, String cuo) throws Exception {
		return maeUsuarioDao.consultarUsuario(params, cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public UsuarioDTO buscarUsuario(Long nUsuario, String cuo) throws Exception {
		return maeUsuarioDao.buscarUsuario(nUsuario, cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = { Exception.class, SQLException.class})
	public Long registrarUsuario(UsuarioDTO usuario, String cuo) throws Exception {
		return maeUsuarioDao.registrarUsuario(usuario, cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = { Exception.class, SQLException.class})
	public int modificarUsuario(UsuarioDTO usuario, String cuo) throws Exception {
		return maeUsuarioDao.modificarUsuario(usuario, cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public List<ComboDTO> listarMaestros(String codigo, String cuo) throws Exception {
		List<ComboDTO> lista=null;
		if(codigo.equals(ConstantesSF.Maestro.PERFIL)) {
			lista = maeUsuarioDao.listarPerfiles();
		}else if(codigo.equals(ConstantesSF.Maestro.DISTRITO)) {
			lista = maeUsuarioDao.listarDistritosJudiciales();
		}
		return lista; 
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = { Exception.class, SQLException.class})
	public int resetearContrasena(ContrasenaDTO contrasena, String cuo) throws Exception {
		return maeUsuarioDao.resetearContrasena(contrasena, cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, rollbackFor = Exception.class)
	public int validarUsuario(UsuarioDTO usuario) throws Exception {
		return maeUsuarioDao.validarUsuario(usuario);
	}

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = true, timeout = 60, rollbackFor = Exception.class)
	public MovPersonaReniec validarPersonaReniec(String dni, String cuo) throws Exception {		
		return maeUsuarioSFDao.validarPersonaReniec(dni, cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerCentralizada", readOnly = false, timeout = 60, rollbackFor = Exception.class)
	public void registrarPersona(MovPersonaReniec persona, String cuo) throws Exception {
		maeUsuarioSFDao.registrarPersona(persona, cuo);		
	}
}
