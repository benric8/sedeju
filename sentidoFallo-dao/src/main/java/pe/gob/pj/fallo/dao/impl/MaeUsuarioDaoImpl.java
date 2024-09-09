package pe.gob.pj.fallo.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.fallo.dao.MaeUsuarioDao;
import pe.gob.pj.fallo.dto.io.ComboDTO;
import pe.gob.pj.fallo.dto.io.ContrasenaDTO;
import pe.gob.pj.fallo.dto.DistritoJudicialDTO;
import pe.gob.pj.fallo.dto.PerfilDTO;
import pe.gob.pj.fallo.dto.UsuarioDTO;
import pe.gob.pj.fallo.entity.MaeDistritosJudiciale;
import pe.gob.pj.fallo.entity.MaePerfil;
import pe.gob.pj.fallo.entity.MaeUsuario;
import pe.gob.pj.fallo.utils.ConstantesSF;
import pe.gob.pj.fallo.utils.ParametrosDeBusqueda;
import pe.gob.pj.fallo.utils.UtilsCore;
import pe.gob.pj.fallo.utils.ValidarUtil;

@Component("maeUsuarioDao")
public class MaeUsuarioDaoImpl implements MaeUsuarioDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MaeUsuarioDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionPrincipal")
	private SessionFactory factoria;
	
	@Override
	public List<UsuarioDTO> consultarUsuario(Map<String, Object> params, String cuo) throws Exception {
		
		logger.info(cuo+" MaeUsuarioDaoImpl.consultarUsuario inicio");
		List<UsuarioDTO> resultado = new ArrayList<UsuarioDTO>();
		List<Entry<String, Object>> listaParams = new ArrayList<Entry<String, Object>>();
		StringBuilder hql = new StringBuilder();
		hql.append("select o from MaeUsuario as o ");
		for (Map.Entry<String, Object> map : params.entrySet()) {
			if (ValidarUtil.validaValor(map, ParametrosDeBusqueda.USUA_ID)) {
				hql.append((hql.indexOf(" WHERE") > 0) ? " AND" : " WHERE");
				hql.append(" o.nUsuario=:").append(ParametrosDeBusqueda.USUA_ID);
				listaParams.add(map);
			} else if (ValidarUtil.validaValor(map, ParametrosDeBusqueda.USUA_NRO_DOC)) {
				hql.append((hql.indexOf(" WHERE") > 0) ? " AND" : " WHERE");
				hql.append(" o.xDocIdentidad=:").append(ParametrosDeBusqueda.USUA_NRO_DOC);
				listaParams.add(map);
			} else if (ValidarUtil.validaValor(map, ParametrosDeBusqueda.USUA_ESTADO)) {
				hql.append((hql.indexOf(" WHERE") > 0) ? " AND" : " WHERE");
				hql.append(" o.lActivo=:").append(ParametrosDeBusqueda.USUA_ESTADO);
				listaParams.add(map);
			} else if (ValidarUtil.validaValor(map, ParametrosDeBusqueda.USUA_NOMBRE_COMPLETO)) {				
				hql.append((hql.indexOf(" WHERE") > 0) ? " AND" : " WHERE");
				hql.append(" UPPER(CONCAT(CONCAT(CONCAT(CONCAT(o.xNomUsuario,' '),o.xApePaterno),' '),o.xApeMaterno)) LIKE :").append(ParametrosDeBusqueda.USUA_NOMBRE_COMPLETO);
				listaParams.add(map);
			} else if (ValidarUtil.validaValor(map, ParametrosDeBusqueda.USUA_PERFIL)) {
				hql.append((hql.indexOf(" WHERE") > 0) ? " AND" : " WHERE");
				hql.append(" o.maePerfil.nPerfil=:").append(ParametrosDeBusqueda.USUA_PERFIL);
				listaParams.add(map);
			} 
		}
		Query<MaeUsuario> query = factoria.getCurrentSession().createQuery(hql.toString(), MaeUsuario.class);
		for (Map.Entry<String, Object> map : listaParams) {
			query.setParameter( map.getKey(), map.getValue() );
		}
		List<MaeUsuario> list = query.getResultList();
		for (MaeUsuario u : list) {
			UsuarioDTO userDto = new UsuarioDTO();
			userDto.setnUsuario(u.getNUsuario());
			userDto.setcUsuario(u.getCUsuario());
			userDto.setxNomUsuario(u.getXNomUsuario());
			userDto.setxApePaterno(u.getXApePaterno());
			userDto.setxApeMaterno(u.getXApeMaterno());
			userDto.setxDocIdentidad(u.getXDocIdentidad());
			userDto.setxNomOrgjurisd(u.getXNomOrgjurisd());
			userDto.setlActivo(u.getLActivo());
			PerfilDTO perfil = new PerfilDTO();
			if(u.getMaePerfil()!=null) {
				userDto.setnPerfil(u.getMaePerfil().getNPerfil());
				perfil.setnPerfil(u.getMaePerfil().getNPerfil());
				perfil.setxDescripcion(u.getMaePerfil().getXDescripcion());
				userDto.setPerfilDTO(perfil);
			}else {
				userDto.setnPerfil(null);
				userDto.setPerfilDTO(perfil);
			}
			DistritoJudicialDTO distrito = new DistritoJudicialDTO();
			if(u.getMaeDistritosJudiciale()!=null) {
				userDto.setcDistritoJudicial(u.getMaeDistritosJudiciale().getC_distritoJudicial());
				distrito.setCodigo(u.getMaeDistritosJudiciale().getC_distritoJudicial());
				distrito.setDescripcion(u.getMaeDistritosJudiciale().getX_nomDistrito());
				userDto.setDistritoJudicialDTO(distrito);
			}else {
				userDto.setcDistritoJudicial(null);
				userDto.setDistritoJudicialDTO(distrito);
			}
			userDto.setlReseteo(u.getLReseteo());
			userDto.setlReniec(u.getLReniec());
			userDto.setxCorreo(u.getXCorreo());
			resultado.add(userDto);
		}
		return resultado;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public UsuarioDTO buscarUsuario(Long nUsuario, String cuo) throws Exception {
		
		UsuarioDTO resultado = null;
		Query query = factoria.getCurrentSession().createNamedQuery(MaeUsuario.BUSCAR_POR_ID);
		query.setParameter(MaeUsuario.P_ID, nUsuario);
		List<MaeUsuario> lista = (List<MaeUsuario>) query.list();
		MaeUsuario registro;
		if(lista!=null && lista.size()>0) {
			registro = lista.get(0);
			resultado = new UsuarioDTO();
			resultado.setnUsuario(registro.getNUsuario());
			resultado.setcUsuario(registro.getCUsuario());
			resultado.setxDocIdentidad(registro.getXDocIdentidad());
			resultado.setxApePaterno(registro.getXApePaterno());
			resultado.setxApeMaterno(registro.getXApeMaterno());
			resultado.setxNomUsuario(registro.getXNomUsuario());
			resultado.setlActivo(registro.getLActivo());
			resultado.setcDistritoJudicial(registro.getMaeDistritosJudiciale().getC_distritoJudicial());
			resultado.setxNomOrgjurisd(registro.getXNomOrgjurisd());
			resultado.setnPerfil(registro.getMaePerfil().getNPerfil());	
			resultado.setxCorreo(registro.getXCorreo());
		}
		return resultado;
	}
	
	@Override
	public Long registrarUsuario(UsuarioDTO usuario, String cuo) throws Exception {
		
		MaeUsuario registro = new MaeUsuario();
		registro.setCUsuario(usuario.getcUsuario());
		registro.setCClave(usuario.getcClave());
		registro.setXNomUsuario(usuario.getxNomUsuario());
		registro.setXApePaterno(usuario.getxApePaterno());
		registro.setXApeMaterno(usuario.getxApeMaterno());
		registro.setXDocIdentidad(usuario.getxDocIdentidad());
		MaeDistritosJudiciale distrito = new MaeDistritosJudiciale();
		distrito.setC_distritoJudicial(usuario.getcDistritoJudicial());
		registro.setMaeDistritosJudiciale(distrito);
		registro.setXNomOrgjurisd(usuario.getxNomOrgjurisd());
		registro.setLActivo(usuario.getlActivo());
		MaePerfil perfil = new MaePerfil();
		perfil.setNPerfil(usuario.getnPerfil());
		registro.setMaePerfil(perfil);
		registro.setLReseteo(usuario.getlReseteo());
		registro.setLReniec(usuario.getlReniec());
		registro.setXCorreo(usuario.getxCorreo());
		
		registro.setFAud(new java.sql.Timestamp((usuario.getFechaOperacion()).getTime()));
		registro.setBAud(usuario.getTipoOperacion());
		registro.setCAudUidred(usuario.getUsuarioRedOperacion());
		registro.setCAudUid(usuario.getUsuarioOperacion());
		registro.setCAudPc(usuario.getPcOperacion());
		registro.setCAudIp(usuario.getIpOperacion());
		registro.setCAudMcaddr(usuario.getMacOperacion());
		Long pk = (Long) factoria.getCurrentSession().save(registro);
		logger.info("Entidad {}", registro.toString());
		return pk;
	}
	
	@Override
	public int modificarUsuario(UsuarioDTO usuario, String cuo) throws Exception {
		
		MaeUsuario registro = factoria.getCurrentSession().get(MaeUsuario.class, usuario.getnUsuario());
		//registro.setCUsuario(usuario.getcUsuario());
		//registro.setCClave(CryptoUtil.encriptar(usuario.getcClave().trim()));
		registro.setXNomUsuario(usuario.getxNomUsuario());
		registro.setXApePaterno(usuario.getxApePaterno());
		registro.setXApeMaterno(usuario.getxApeMaterno());
		//registro.setXDocIdentidad(usuario.getxDocIdentidad());
		MaeDistritosJudiciale distrito = new MaeDistritosJudiciale();
		distrito.setC_distritoJudicial(usuario.getcDistritoJudicial());
		registro.setMaeDistritosJudiciale(distrito);
		registro.setXNomOrgjurisd(usuario.getxNomOrgjurisd());
		registro.setLActivo(usuario.getlActivo());
		MaePerfil perfil = new MaePerfil();
		perfil.setNPerfil(usuario.getnPerfil());
		registro.setMaePerfil(perfil);
		registro.setXCorreo(usuario.getxCorreo());
		
		registro.setFAud(new java.sql.Timestamp((usuario.getFechaOperacion()).getTime()));
		registro.setBAud(usuario.getTipoOperacion());
		registro.setCAudUidred(usuario.getUsuarioRedOperacion());
		registro.setCAudUid(usuario.getUsuarioOperacion());
		registro.setCAudPc(usuario.getPcOperacion());
		registro.setCAudIp(usuario.getIpOperacion());
		registro.setCAudMcaddr(usuario.getMacOperacion());
		factoria.getCurrentSession().update(registro);
		logger.info("Entidad {}", registro.toString());
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ComboDTO> listarPerfiles() throws Exception {
		
		List<ComboDTO> lista = new ArrayList<ComboDTO>();
		Query query = factoria.getCurrentSession().createNamedQuery(MaePerfil.LISTAR);
		List<MaePerfil> listaPerfiles = (List<MaePerfil>) query.list();
		ComboDTO combo;
		for(MaePerfil perfil: listaPerfiles) {
			combo = new ComboDTO();
			combo.setCodigo(perfil.getNPerfil()+"");
			combo.setDescripcion(perfil.getXDescripcion());
			lista.add(combo);
		}
		return lista;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ComboDTO> listarDistritosJudiciales() throws Exception {
		
		List<ComboDTO> lista = new ArrayList<ComboDTO>();
		Query query = factoria.getCurrentSession().createNamedQuery(MaeDistritosJudiciale.LISTAR);
		List<MaeDistritosJudiciale> listaDistritos = (List<MaeDistritosJudiciale>) query.list();
		ComboDTO combo;
		for(MaeDistritosJudiciale distrito: listaDistritos) {
			combo = new ComboDTO();
			combo.setCodigo(distrito.getC_distritoJudicial());
			combo.setDescripcion(distrito.getX_nomDistrito());
			lista.add(combo);
		}
		return lista;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int resetearContrasena(ContrasenaDTO contrasena, String cuo) throws Exception {
		
		Query query = factoria.getCurrentSession().createNamedQuery(MaeUsuario.BUSCAR_POR_ID);
		query.setParameter(MaeUsuario.P_ID, contrasena.getnUsuarioAdmin());
		List<MaeUsuario> lista = (List<MaeUsuario>) query.list();
		MaeUsuario admin = lista.get(0);
		if(!(admin.getMaePerfil().getNPerfil()==ConstantesSF.Perfiles.ADMINISTRADOR)) return 0;
		MaeUsuario registro = factoria.getCurrentSession().get(MaeUsuario.class, contrasena.getnUsuario());
		registro.setCClave(contrasena.getNuevaContrasena().trim());
		registro.setLReseteo(ConstantesSF.ACTIVO);
		registro.setFAud(new java.sql.Timestamp((new Date()).getTime()));
		registro.setBAud(ConstantesSF.Operacion.ACTUALIZAR);
		registro.setCAudUidred(contrasena.getnUsuarioAdmin()+"");
		registro.setCAudUid(contrasena.getnUsuarioAdmin()+"");
		registro.setCAudPc(UtilsCore.getPCName());
		registro.setCAudIp(UtilsCore.getIPAddress(true));
		registro.setCAudMcaddr(UtilsCore.getMACAddress());
		factoria.getCurrentSession().update(registro);
		logger.info("Entidad {}", registro.toString());
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int validarUsuario(UsuarioDTO usuario) throws Exception {
		Query query1 = factoria.getCurrentSession().createNamedQuery(MaeUsuario.BUSCAR_POR_USUARIO);
		query1.setParameter(MaeUsuario.P_USUARIO, usuario.getcUsuario());
		List<MaeUsuario> lista1 = (List<MaeUsuario>) query1.list();
		if(lista1!=null && lista1.size()>0) return 1;
		Query query2 = factoria.getCurrentSession().createNamedQuery(MaeUsuario.BUSCAR_POR_DNI);
		query2.setParameter(MaeUsuario.P_DNI, usuario.getxDocIdentidad());
		List<MaeUsuario> lista2 = (List<MaeUsuario>) query2.list();
		if(lista2!=null && lista2.size()>0) return 2;
		return 0;
	}
}