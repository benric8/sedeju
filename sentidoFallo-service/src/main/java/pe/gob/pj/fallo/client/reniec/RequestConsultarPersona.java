package pe.gob.pj.fallo.client.reniec;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestConsultarPersona implements Serializable {
		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		
		private String formatoRespuesta;
	    private String consultante;
	    private String motivo;
	    private PersonaConsultada personaConsultada;
	    private Pagination pagination;
	    private Auditoria auditoria;
}
