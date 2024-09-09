package pe.gob.pj.fallo.dto.io;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginDTO implements Serializable {
   
	private static final long serialVersionUID = 1L;
	
    @NotNull(message="Ingrese el campo usuario.")
    @NotBlank(message = "El campo usuario no puede estar en blanco")
	private String usuario;
    
    @NotNull(message="Ingrese el campo clave.")
    @NotBlank(message = "El campo clave no puede estar en blanco")
    private String clave;
    
    @NotNull(message="Ingrese el campo captcha.")
    @NotBlank(message = "El campo captcha no puede estar en blanco")
    private String captcha;
    
    public LoginDTO() {
		super();
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
