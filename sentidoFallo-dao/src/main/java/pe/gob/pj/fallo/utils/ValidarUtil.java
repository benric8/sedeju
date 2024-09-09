package pe.gob.pj.fallo.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Copyright (C) 2015 Poder Judicial (Lima - Peru)
 * GERENCIA DE INFORMATICA
 * SUB GERENCIA DE  DESARROLLO DE SISTEMAS DE INFORMACION
 * PROYECTO SINOE - WJAV015 - WJAV016: SINOE

 * All rights reserved. Used by permission
 * This software is the confidential and proprietary information of PJ
 * ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with PJ.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 **/
/**
 * @objetivo: Implementar la clase ValidarUtil
 * @autor: jhenriquez
 * @fecha: 22/07/2015
 **/
public class ValidarUtil implements Serializable {
	private static final long serialVersionUID = -6548616628020040266L;
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final String PATTERN_EMAIL_PJ = "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@PJ\\.GOB\\.PE";

	private static final String PATTERN_IP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private static final String PATTERN_MAC_ADDRESS = "^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$";

	private static final String PATTERN_PC_NAME = "^(?!.*(.)\\1{2})([ 0-9a-zñÑA-Z-_.:/])+$";

	private static final String PATTERN_USUARIO_SIS = "^(?!.*(.)\\1{2})([ 0-9a-zñÑA-Z-_.:/])+$";

	private static final String PATTERN_USUARIO_RED = "^(?!.*(.)\\1{2})([ 0-9a-zñÑA-Z-_.:/])+$";

	private static final String PATTERN_NOMBRE_SO = "^(?!.*(.)\\1{2})([ 0-9a-zñÑA-Z-_.])+$";

	@SuppressWarnings("unused")
	private static final String PATTERN_NAME = "^([ a-zA-Z])+$";

	private static final String PATTERN_TEXTO_GENERICO = "^(?!.*(.)\\1{2})([ 0-9a-zA-Z-_,;.:/#$%=])+$";

	public static boolean isNull(String valor) {
		boolean estado = false;

		if (valor == null || valor.trim().length() <= 0) {
			estado = true;
		}
		return estado;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object valor) {
		if (valor == null) {
			return true;
		}

		if (valor instanceof Collection<?>) {
			return ((Collection) valor).isEmpty();
		}
		if (valor instanceof Set<?>) {
			return ((Set) valor).isEmpty();
		}
		if (valor instanceof Map<?, ?>) {
			return ((Map) valor).isEmpty();
		}

		if (valor instanceof String) {
			if (((String) valor).trim().length() == 0) {
				return true;
			}
		}

		if (valor instanceof Integer) {
			if (((Integer) valor).intValue() <= 0) {
				return true;
			}
		}

		if (valor instanceof Long) {
			if (((Long) valor).intValue() <= 0) {
				return true;
			}
		}

		return false;
	}

	public static boolean isDNI(String valor) {
		boolean estado = false;

		if (valor != null && valor.trim().length() == 8) {
			estado = true;
		} else {
			estado = false;
		}

		return estado;
	}

	public static boolean isRUC(String valor) {
		boolean estado = false;

		if (valor != null && valor.trim().length() == 11) {
			estado = true;
		} else {
			estado = false;
		}

		return estado;
	}

	/**
	 * Valida si el IP tiene formato válido.
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean validarIp(String ip) {
		return ip.matches(PATTERN_IP);
	}

	/**
	 * Valida si la Mac Address tiene formato válido.
	 * 
	 * @param macAddress
	 * @return
	 */
	public static boolean validarMacAddress(String macAddress) {
		return macAddress.matches(PATTERN_MAC_ADDRESS);
	}

	/**
	 * Valida si el nombre de PC tiene un formato válido. Sólo se permiten
	 * letras, números, espacio en blanco y los los caracteres -_.:/ y no se
	 * permiten mas de dos caracteres repetidos consecutivos.
	 * 
	 * @param pcName
	 * @return true si la cadena cumple con el formato
	 *         ^(?!.*(.)\\1{2})([0-9a-zA-Z-_.:/])+$, false en caso contrario.
	 */
	public static boolean validarPcName(String pcName) {
		return pcName.matches(PATTERN_PC_NAME);
	}

	/**
	 * Valida si el nombre de usuario del sistema tiene un formato válido. Sólo
	 * se permiten letras, números, espacio en blanco y los los caracteres -_.:/
	 * y no se permiten mas de dos caracteres repetidos consecutivos.
	 * 
	 * @param pcName
	 * @return true si la cadena cumple con el formato
	 *         ^(?!.*(.)\\1{2})([0-9a-zA-Z-_.:/])+$, false en caso contrario.
	 */
	public static boolean validarUsuarioSis(String usuarioSis) {
		return usuarioSis.matches(PATTERN_USUARIO_SIS);
	}

	/**
	 * Valida si el nombre de usuario de red tiene un formato válido. Sólo se
	 * permiten letras, números, espacio en blanco y los los caracteres -_.:/ y
	 * no se permiten mas de dos caracteres repetidos consecutivos.
	 * 
	 * @param pcName
	 * @return true si la cadena cumple con el formato
	 *         ^(?!.*(.)\\1{2})([0-9a-zA-Z-_.:/])+$, false en caso contrario.
	 */
	public static boolean validarUsuarioRed(String usuarioRed) {
		return usuarioRed.matches(PATTERN_USUARIO_RED);
	}

	/**
	 * Valida si el nombre del sistema operativo tiene un formato válido. Sólo
	 * se permiten letras, números, espacio en blanco y los los caracteres -_. y
	 * no se permiten mas de dos caracteres repetidos consecutivos.
	 * 
	 * @param pcName
	 * @return true si la cadena cumple con el formato
	 *         ^(?!.*(.)\\1{2})([0-9a-zA-Z-_.])+$, false en caso contrario.
	 */
	public static boolean validarNombreSo(String nombreSo) {
		return nombreSo.matches(PATTERN_NOMBRE_SO);
	}

	/**
	 * Valida si una cadena tiene un formato válido. Sólo se permiten letras,
	 * números, espacio en blanco y los los caracteres -_,;.:/#$%= y no se
	 * permiten mas de dos caracteres repetidos consecutivos.
	 * 
	 * @param pcName
	 * @return true si la cadena cumple con el formato ^(?!.*(.)\\1{2})([
	 *         0-9a-zA-Z-_,;.:/#$%=])+$, false en caso contrario.
	 */
	public static boolean validarTextoGenerico(String texto) {
		return texto.matches(PATTERN_TEXTO_GENERICO);
	}

	public static boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean validateEmail_PJ(String email) {
		Pattern pattern = Pattern.compile(PATTERN_EMAIL_PJ);
		String correo = email.trim().toUpperCase();
		Matcher matcher = pattern.matcher(correo);
		return matcher.matches();
	}

	public static boolean validaFormatoNumerico(Object field) {

		String campo = field.toString();
		for (char letra : campo.toCharArray()) {
			if (!Character.isDigit(letra)) {
				return false;
			}

		}
		return true;
	}

	public static boolean validaFormatoNumerosUltimoCaracter(Object field) {

		String campo = field.toString();
		int c = 1;
		if (campo.length() > 1) {
			for (char letra : campo.toCharArray()) {
				if (c <= campo.length() - 1) {
					if (!Character.isDigit(letra)) {
						return false;
					}
				}
				c++;
			}
		} else {
			return false;
		}
		return true;
	}

	public static boolean validaCadenaSinNumeros(Object field) {

		String campo = field.toString();
		for (char letra : campo.toCharArray()) {
			if (Character.isDigit(letra)) {
				return false;
			}

		}
		return true;
	}

	public static boolean validarFormatoEspecial(Object field, boolean flagNumerico, String caracteresEspeciales) {
		Boolean letraValido;
		Boolean digitoValido;

		Object value = field;
		String campo = value.toString();
		for (char letra : campo.toCharArray()) {

			Boolean esLetra = Character.isLetter(letra);
			Boolean esDigito = Character.isDigit(letra);
			Boolean esEspecial = (caracteresEspeciales.contains(String.valueOf(letra)));

			letraValido = esLetra.equals(true);

			if (flagNumerico) {
				digitoValido = esDigito.equals(flagNumerico);
			} else {
				digitoValido = false;
			}

			if (!(esEspecial || letraValido || digitoValido)) {
				return false;
			}

		}

		return true;
	}

	public static boolean validarFormatoEspecialConEspacioEnBlanco(Object field, boolean flagNumerico, String caracteresEspeciales) {
		Boolean letraValido;
		Boolean digitoValido;

		Object value = field;
		String campo = value.toString();
		for (char letra : campo.toCharArray()) {

			Boolean esLetra = Character.isLetter(letra);
			Boolean esDigito = Character.isDigit(letra);
			Boolean esEspacio = Character.isWhitespace(letra);
			Boolean esEspecial = (caracteresEspeciales.contains(String.valueOf(letra)));

			letraValido = esLetra.equals(true);

			if (flagNumerico) {
				digitoValido = esDigito.equals(flagNumerico);
			} else {
				digitoValido = false;
			}

			if (!(esEspecial || letraValido || digitoValido || esEspacio)) {
				return false;
			}

		}

		return true;
	}

	public static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static Boolean validarExpresion(String regex, String cadena) {
		Matcher matcher = null;
		try {

			if (StringUtils.isBlank(regex) && StringUtils.isBlank(cadena)) {
				return Boolean.FALSE;
			}

			Pattern pattern = Pattern.compile(regex);
			matcher = pattern.matcher(cadena.trim());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Boolean.FALSE;
		}

		return matcher.find();
	}

	public static boolean validaValor(Map.Entry<String, Object> map, String campo) {
		boolean estado = false;
		if (map.getKey().equals(campo)) {
			if (map.getValue() instanceof String && !ValidarUtil.isNull((String) map.getValue())) {
				estado = true;
			} else if (map.getValue() instanceof Long && !ValidarUtil.isNull(map.getValue().toString())) {
				estado = true;
			} else if( map.getValue() instanceof Collection && !ValidarUtil.isNullOrEmpty(map.getValue())){
				estado = true;
			} else if(!ValidarUtil.isNullOrEmpty(map.getValue())){
				estado = true;
			}	
		}
		return estado;
	}
	
	public static String isBlank(String val) {
		if(val == null) {
			return "";
		} else if(val.trim().length() <= 0) {
			return "";
		}
		return val;
	}
	
	public static Integer isCero(Integer val) {
		if(val == null) {
			return 0;
		} else if(val == 0) {
			return 0;
		}
		return val;
	}
}