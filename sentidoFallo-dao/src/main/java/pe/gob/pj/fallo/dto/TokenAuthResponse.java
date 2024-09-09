package pe.gob.pj.fallo.dto;

import java.io.Serializable;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenAuthResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String token;
	private String exps;
	private String refs;

}
