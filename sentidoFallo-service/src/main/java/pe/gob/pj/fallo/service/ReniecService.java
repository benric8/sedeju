package pe.gob.pj.fallo.service;


import java.util.List;

import pe.gob.pj.fallo.client.reniec.PersonaDTO;

public interface ReniecService {
	PersonaDTO obtenerPersonaPorDNI(String dni, String pcName, String ipAddress, String macAddress, String usuario) throws Exception;
	List<PersonaDTO> obtenerListPersonaPorNombres(String primerApellido, String segundoApellido, String nombres, String totalRegistrosRetorna, String posicionRegistro)throws Exception;
}