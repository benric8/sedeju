package pe.gob.pj.fallo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class UsuarioSFBean implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private long nUsuario;
	private String cUsuario;
	private String cClave;
	private String xNomUsuario;
	private String xApePaterno;
	private String xApeMaterno;
	private String xDocIdentidad;
	private String cDistritoJudicial;
	private String xNomOrgJurisd;
	private String lActivo;
	private long nPerfil;
	private String xDistritoJudicial;
	private String xPerfil;
	private String lReseteo;
	private String lReniec;
	private String xTokenRecuperacion;
	private Timestamp fExpiracionToken;
	
    public UsuarioSFBean() {
		super();
	}
	public long getnUsuario() {
        return nUsuario;
    }
    public void setnUsuario(long nUsuario) {
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
    public String getxNomOrgJurisd() {
        return xNomOrgJurisd;
    }
    public void setxNomOrgJurisd(String xNomOrgJurisd) {
        this.xNomOrgJurisd = xNomOrgJurisd;
    }
    public String getlActivo() {
        return lActivo;
    }
    public void setlActivo(String lActivo) {
        this.lActivo = lActivo;
    }
	public long getnPerfil() {
		return nPerfil;
	}
	public void setnPerfil(long nPerfil) {
		this.nPerfil = nPerfil;
	}
	public String getxDistritoJudicial() {
		return xDistritoJudicial;
	}
	public void setxDistritoJudicial(String xDistritoJudicial) {
		this.xDistritoJudicial = xDistritoJudicial;
	}
	public String getxPerfil() {
		return xPerfil;
	}
	public void setxPerfil(String xPerfil) {
		this.xPerfil = xPerfil;
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
	public String getxTokenRecuperacion() {
		return xTokenRecuperacion;
	}
	public void setxTokenRecuperacion(String xTokenRecuperacion) {
		this.xTokenRecuperacion = xTokenRecuperacion;
	}
	public Timestamp getfExpiracionToken() {
		return fExpiracionToken;
	}
	public void setfExpiracionToken(Timestamp fExpiracionToken) {
		this.fExpiracionToken = fExpiracionToken;
	}
	@Override
	public String toString() {
		return "UsuarioSFBean [nUsuario=" + nUsuario + ", cUsuario=" + cUsuario + ", cClave=" + cClave
				+ ", xNomUsuario=" + xNomUsuario + ", xApePaterno=" + xApePaterno + ", xApeMaterno=" + xApeMaterno
				+ ", xDocIdentidad=" + xDocIdentidad + ", cDistritoJudicial=" + cDistritoJudicial
				+ ", xDistritoJudicial=" + xDistritoJudicial + ", xNomOrgJurisd=" + xNomOrgJurisd + ", lActivo="
				+ lActivo + ", nPerfil=" + nPerfil + ", xPerfil=" + xPerfil + "]";
	}
}
