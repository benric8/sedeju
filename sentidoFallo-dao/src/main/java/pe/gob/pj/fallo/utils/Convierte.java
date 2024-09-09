package pe.gob.pj.fallo.utils;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Convierte {

	public Convierte() {
	}

	public static String quitarEspacios(String s){
		String result = null;
		
		try {
		result = s.replaceAll("\\s","");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	public static Integer toInteger(String s) {
		Integer result = null;

		try {
			result = Integer.valueOf(s);
		} catch (NumberFormatException e) {
		}

		return result;
	}

	public static Double toDouble(String s) {
		Double result = null;

		if (s != null) {
			try {
				result = Double.valueOf(s);
			} catch (NumberFormatException e) {
			}
		}

		return result;
	}

	public static Long toLong(String s) {
		Long result = null;

		if (s != null) {
			try {
				result = Long.valueOf(s);
			} catch (NumberFormatException e) {
			}
		}

		return result;
	}

	public static Float toFloat(String s) {
		Float result = null;

		if (s != null) {
			try {
				result = Float.valueOf(s);
			} catch (NumberFormatException e) {
			}
		}

		return result;
	}
	
	public static String quitarDecimales(String s){
	    String result = null;
		
		DecimalFormat df = new DecimalFormat("###");
		
		try{
			if(s!=null){
			result = df.format(Double.valueOf(s));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	public static List<Integer> toList(String ids) {
		List<Integer> list = null;

		if (ids != null) {
			String[] idArray = ids.split(",");
			list = new LinkedList<>();
			for (String x : idArray) {
				try {
					Integer idx = Integer.valueOf(x);
					list.add(idx);
				} catch (Exception e) {
					list = null;
					e.getMessage();
				}
			}
		}

		return list;
	}

	// fechas (String viene : dd/mm/aaaa)
	public static java.util.Date toUtilDate(String s) {
		java.util.Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false); // complaciente == NO
		try {
			result = sdf.parse(s);
		} catch (ParseException e) {
		}

		return result;
	}

	public static java.sql.Timestamp toTimestamp(String s, String columna) throws Exception {
		java.sql.Timestamp result = null;
		if (s != null && s.length() > 0) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
				java.util.Date parsedDate = sdf.parse(s);
				result = new Timestamp(parsedDate.getTime());

			} catch (Exception e) {
				System.out.println("ERROR:" + e.getMessage());
				throw new Exception(columna + " : Valor ingresado incorrecto, formato debe ser dd/MM/yyyy HH:mm:ss.S");
			}
		}
	    return result;		
	}
	
	public static java.sql.Timestamp toTimestamp(String s)  {
		java.sql.Timestamp result = null;
		if (s != null && s.length() > 0) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
				java.util.Date parsedDate = sdf.parse(s);
				result = new Timestamp(parsedDate.getTime());

			} catch (Exception e) {
				System.out.println("ERROR:" + e.getMessage());
			}
		}
	    return result;		
	}
	
	public static java.sql.Timestamp getFechaActualTimestamp() {
		
		java.sql.Timestamp result = null;
				
		try {
			Date fechaActual = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
			String parsedDate = sdf.format(fechaActual);
			result = toTimestamp(parsedDate);
		} catch (Exception e) {
			System.out.println("ERROR:"+e.getMessage());
		}
		
		return result;
	}   
	
    public static String getFechaActual() {
		
		String result = null;
				
		try {
			Date fechaActual = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
			result = sdf.format(fechaActual);			
		} catch (Exception e) {
			System.out.println("ERROR:"+e.getMessage());
		}
		
		return result;
	}   
	
	public static java.sql.Date utilToSqlDate(java.util.Date uDate) {
		java.sql.Date result = null;

		if (uDate != null) {
			result = new java.sql.Date(uDate.getTime());
		}

		return result;
	}

	public static java.util.Date sqlToUtilDate(java.sql.Date sDate) {
		java.util.Date result = null;

		if (sDate != null) {
			result = new java.util.Date(sDate.getTime());
		}

		return result;
	}

	public static String utilDateToString(java.util.Date uDate) {
		String result = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (uDate != null) {
			result = sdf.format(uDate);
		}
		return result;
	}

	public static String utilDateToStringWithHours(java.util.Date uDate) {
		String result = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		if (uDate != null) {
			result = sdf.format(uDate);
		}
		return result;
	}

	public static java.util.Date stringToUtilDate(String sfecha) {
		java.util.Date result = null;
		if (sfecha != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			try {
				java.util.Date ufecha = sdf.parse(sfecha);
				result = ufecha;
			} catch (ParseException ex) {
				String error = "Fecha debe ser: dd/mm/yyyy";
				System.out.println(error);
			}
		}

		return result;
	}

	public static java.sql.Date stringToSqlDate(String sfecha) {
		java.sql.Date result = null;
		if (sfecha != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			try {
				java.util.Date ufecha = sdf.parse(sfecha);
				result = new java.sql.Date(ufecha.getTime());
			} catch (ParseException ex) {
				@SuppressWarnings("unused")
				String error = "Fecha debe ser: dd/mm/yyyy";
			}
		}

		return result;
	}

	/**
	 * Metodo que retorna fecha y hora en espaniol (dd/MM/yyyy HH:mm:ss.S) a partir de una fecha Timestamp
	 * @param date es Timestamp
	 */
	public static String timestampToDateTimeStrES(Timestamp date) {
		String result = null;
		try {
			if (date != null) {				
				SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
				result = df1.format(date.getTime());
			}else {
				new Exception("La entrada de fecha no puede ser nulo");
			}
		} catch (Exception e) {
			throw e;
		}

		return result;
	}
	
	/**
	 * Metodo que retorna fecha en español (dd/MM/yyyy) a partir de una fecha Timestamp
	 * @param date es Timestamp
	 */
	public static String timestampToDateStrES(Timestamp date) {
		String result = null;
		try {
			if (date != null) {				
				SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
				result = df1.format(date.getTime());
			}else {
				new Exception("La entrada de fecha no puede ser nulo");
			}
		} catch (Exception e) {
			throw e;
		}

		return result;
	}
	
	/**
	 * Metodo que retorna fecha en español (dd/MM/yyyy) a partir de una fecha Timestamp
	 * @param date es Timestamp
	 * @throws Exception 
	 */
	public static Date stringToUtilDateTimeES(String date) throws Exception {
		Date result = null;
		try {
			if (date != null) {				
				SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
				result = df1.parse(date);
			}else {
				new Exception("La entrada de fecha y hora no puede ser nulo");
			}
		} catch (Exception e) {
			throw e;
		}

		return result;
	}
	
	/**
	 * Metodo que retorna hora (HH:mm:ss) a partir de una fecha Timestamp
	 * @param date es Timestamp
	 */
	public static String timestampToHour(Timestamp date) {
		String result = null;
		try {
			if (date != null) {				
				SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
				result = df2.format(date.getTime());
			}else {
				new Exception("La entrada de fecha no puede ser nulo");
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public static String timestampToString(java.sql.Timestamp uDate) {
		String result = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
		if (uDate != null) {
			result = sdf.format(uDate);
		}
		return result;
	}
	
	public static String getYearTimestamp(java.sql.Timestamp uDate) {
		String result = null;
        
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		if (uDate != null) {
			result = sdf.format(uDate);
		}
		return result;
	}

	public static String toEncode(String texto) throws UnsupportedEncodingException {
		texto = texto.replace("&Aacute;", "Ã?");
		texto = texto.replace("Ã?", "&Aacute;");
		texto = texto.replace("&Eacute;", "Ã‰");
		texto = texto.replace("&Iacute;", "Ã¿");
		texto = texto.replace("&Oacute;", "Ã“");
		texto = texto.replace("&Uacute;", "Ãš");
		texto = texto.replace("&Ntilde;", "Ã‘");
		return texto;
	}

	public static String sinTilde(String texto) {

		texto = texto.replace("Á", "A");
		texto = texto.replace("É", "E");
		texto = texto.replace("Í", "I");
		texto = texto.replace("Ó", "O");
		texto = texto.replace("Ú", "U");
		texto = texto.replace("Ñ", "N");

		texto = texto.replace("á", "a");
		texto = texto.replace("é", "e");
		texto = texto.replace("í", "i");
		texto = texto.replace("ó", "o");
		texto = texto.replace("ú", "u");
		texto = texto.replace("ñ", "n");

		return texto;
	}
	
	public static Timestamp addDay(Date fecha, int dias) {
		try {
			Calendar c = Calendar.getInstance(); 
			c.setTime(fecha); 
			c.add(Calendar.DATE, dias);
			Date returnVal = c.getTime();
			return new Timestamp(returnVal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
