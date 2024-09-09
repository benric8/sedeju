package pe.gob.pj.fallo.client.reniec;



import java.io.Serializable;

import lombok.Data;

@Data
public class PersonaDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nroDocumentoIdentidad;
    private String codigoVerificacion;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String apellidoCasado;
    private String nombres;
    private String codigoUbigeoDepartamentoDomicilio;
    private String codigoUbigeoProvinciaDomicilio;
    private String codigoUbigeoDistritoDomicilio;
    private String codigoUbigeoLocalidadDomicilio;
    private String departamentoDomicilio;
    private String provinciaDomicilio;
    private String distritoDomicilio;
    private String localidadDomicilio;
    private String estadoCivil;
    private String gradoInstruccion;
    private String estatura;
    private String sexo;
    private String documentoSustentatorioTipoDocumento;
    private String documentoSustentatorioNroDocumento;
    private String codigoUbigeoDepartamentoNacimiento;
    private String codigoUbigeoProvinciaNacimiento;
    private String codigoUbigeoDistritoNacimiento;
    private String codigoUbigeoLocalidadNacimiento;
    private String departamentoNacimiento;
    private String provinciaNacimiento;
    private String distritoNacimiento;
    private String localidadNacimiento;
    private String fechaNacimiento;
    private String documentoPadreTipDocumento;
    private String documentoPadreNumDocumento;
    private String nombrePadre;
    private String documentoMadreTipoDocumento;
    private String documentoMadreNumeroDocumento;
    private String nombreMadre;
    private String fechaInscripcion;
    private String fechaEmision;
    private String fechaFallecimiento;
    private String constanciaVotacion;
    private String fechaCaducidad;
    private String restricciones;
    private String prefijoDireccion;
    private String direccion;
    private String nroDireccion;
    private String blockOChalet;
    private String interior;
    private String urbanizacion;
    private String etapa;
    private String manzana;
    private String lote;
    private String preBlockOChalet;
    private String preDptoPisoInterior;
    private String preUrbCondResid;
    private String reservado;
    private Long longitudFoto;
    private Long longitudFirma;
    private Long reservadoFotoFirma1;
    private String reservadoFotoFirma2;
    private String foto;
    private String firma;

    
}

