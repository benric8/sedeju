package pe.gob.pj.fallo.dto;

public class UsuarioDTO extends AuditoriaDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nUsuario;
    private String cUsuario;
    private String cClave;
    private String xNomUsuario;
    private String xApePaterno;
    private String xApeMaterno;
    private String xDocIdentidad;
    private String cDistritoJudicial;
    private String xNomOrgjurisd;
    private String lActivo;
    private Long nPerfil;
    private PerfilDTO perfilDTO;
    private DistritoJudicialDTO distritoJudicialDTO;
	private String lReseteo;
	private String lReniec;
	private String xCorreo;

    public UsuarioDTO() {
		super();
		perfilDTO = new PerfilDTO();
		distritoJudicialDTO = new DistritoJudicialDTO();
	}
	public Long getnUsuario() {
		return nUsuario;
	}
	public void setnUsuario(Long nUsuario) {
		this.nUsuario = nUsuario;
	}
	public String getcUsuario() {
		return cUsuario;
	}
	public void setcUsuario(String cUsuario) {
		this.cUsuario = cUsuario;
	}
	public String getcClave() {
		return cClave;
	}
	public void setcClave(String cClave) {
		this.cClave = cClave;
	}
	public String getxNomUsuario() {
		return xNomUsuario;
	}
	public void setxNomUsuario(String xNomUsuario) {
		this.xNomUsuario = xNomUsuario;
	}
	public String getxApePaterno() {
		return xApePaterno;
	}
	public void setxApePaterno(String xApePaterno) {
		this.xApePaterno = xApePaterno;
	}
	public String getxApeMaterno() {
		return xApeMaterno;
	}
	public void setxApeMaterno(String xApeMaterno) {
		this.xApeMaterno = xApeMaterno;
	}
	public String getxDocIdentidad() {
		return xDocIdentidad;
	}
	public void setxDocIdentidad(String xDocIdentidad) {
		this.xDocIdentidad = xDocIdentidad;
	}
	public String getcDistritoJudicial() {
		return cDistritoJudicial;
	}
	public void setcDistritoJudicial(String cDistritoJudicial) {
		this.cDistritoJudicial = cDistritoJudicial;
	}
	public String getxNomOrgjurisd() {
		return xNomOrgjurisd;
	}
	public void setxNomOrgjurisd(String xNomOrgjurisd) {
		this.xNomOrgjurisd = xNomOrgjurisd;
	}
	public String getlActivo() {
		return lActivo;
	}	
	public void setlActivo(String lActivo) {
		this.lActivo = lActivo;
	}
	public Long getnPerfil() {
		return nPerfil;
	}
	public void setnPerfil(Long nPerfil) {
		this.nPerfil = nPerfil;
	}
	public PerfilDTO getPerfilDTO() {
		return perfilDTO;
	}
	public void setPerfilDTO(PerfilDTO perfilDTO) {
		this.perfilDTO = perfilDTO;
	}
	public DistritoJudicialDTO getDistritoJudicialDTO() {
		return distritoJudicialDTO;
	}
	public void setDistritoJudicialDTO(DistritoJudicialDTO distritoJudicialDTO) {
		this.distritoJudicialDTO = distritoJudicialDTO;
	}
	public String getlReseteo() {
		return lReseteo;
	}
	public void setlReseteo(String lReseteo) {
		this.lReseteo = lReseteo;
	}
	public String getlReniec() {
		return lReniec;
	}
	public void setlReniec(String lReniec) {
		this.lReniec = lReniec;
	}
	public String getxCorreo() {
		return xCorreo;
	}
	public void setxCorreo(String xCorreo) {
		this.xCorreo = xCorreo;
	}
}