package pe.gob.pj.fallo.utils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.RandomStringUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

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
 * @objetivo: Implementar la clase CryptoUtil
 * @autor: jhenriquez
 * @fecha: 22/07/2015
 *
 *
 *         ------------------------------------------------------------------------
 *         Modificaciones Fecha Nombre Descripcion
 *         ------------------------------------------------------------------------
 *         01/02/2017 Oscar Mateo Se modifico la clase para generar clave
 *         aleatoria para los WS se adicionaron los siguiente metodos
 *         encryptText, decryptText, encryptStrongText, decryptStrongText
 *
 **/
public class CryptoUtil implements Serializable {
	private static final long serialVersionUID = -1856464103545143507L;

	private static final String KEY_ENCRYPTION_PASSWORD = "S1n03P0d3rJud1c1al";

	public static void main(String[] args) {
		try {
			System.out.println("password=" + encriptar("ws3d1ct0s", "SHA1"));
			System.out.println("password=" + encriptar("123456a"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String encriptar(String dato) throws NoSuchAlgorithmException {

		MessageDigest md;
		byte[] buffer, digest;
		String hash = "";

		buffer = dato.getBytes();
		md = MessageDigest.getInstance("SHA1");
		md.update(buffer);
		digest = md.digest();

		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}

		return hash;

	}

	public static String encriptar(String dato, String algoritmo) throws NoSuchAlgorithmException {

		MessageDigest md;
		byte[] buffer, digest;
		String hash = "";

		buffer = dato.getBytes();
		md = MessageDigest.getInstance(algoritmo);
		md.update(buffer);
		digest = md.digest();

		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}

		return hash;

	}

	public static String generarClaveVisual() {
		String clave = RandomStringUtils.randomAlphanumeric(7);
		return clave;
	}

	public static String generarClaveVisual(int cant) {
		String clave = RandomStringUtils.randomAlphanumeric(cant);
		return clave;
	}

	public static String encryptText(String plainText) {
		BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
		basicTextEncryptor.setPassword(KEY_ENCRYPTION_PASSWORD);
		String encryptText = basicTextEncryptor.encrypt(plainText);

		return encryptText;
	}

	public static String decryptText(String encryptedText) {
		BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
		basicTextEncryptor.setPassword(KEY_ENCRYPTION_PASSWORD);
		String decryptText = basicTextEncryptor.decrypt(encryptedText);

		return decryptText;
	}

	public static String encryptStrongText(String plainText) {
		StrongTextEncryptor strongTextEncryptor = new StrongTextEncryptor();
		strongTextEncryptor.setPassword(KEY_ENCRYPTION_PASSWORD);
		String encryptText = strongTextEncryptor.encrypt(plainText);

		return encryptText;
	}

	public static String decryptStrongText(String encryptedText) {
		StrongTextEncryptor strongTextEncryptor = new StrongTextEncryptor();
		strongTextEncryptor.setPassword(KEY_ENCRYPTION_PASSWORD);
		String decryptText = strongTextEncryptor.decrypt(encryptedText);

		return decryptText;
	}
}
