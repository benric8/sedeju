package pe.gob.pj.fallo.client.reniec;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pagination implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size;
    private int page;
}
