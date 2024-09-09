package pe.gob.pj.fallo.dto;

public class MagistradoDTO extends AuditoriaDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String dni;
	private String nombre;
	private Long idSentidoFallo;
	
	public MagistradoDTO() {
		super();
	}
	
	public MagistradoDTO(String nombre) {
		super();
		this.nombre = nombre;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getIdSentidoFallo() {
		return idSentidoFallo;
	}
	public void setIdSentidoFallo(Long idSentidoFallo) {
		this.idSentidoFallo = idSentidoFallo;
	}
}
