package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter

@Embeddable
public class Direccion {
    private String calle;
    private String numero;
    private String complemento;
    private String distrito;
    private String ciudad;
    private String urbanizacion;
    private String codigopostal;
    private String provincia;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.complemento = direccion.complemento();
        this.ciudad = direccion.ciudad();
        this.urbanizacion = direccion.urbanizacion();
        this.codigopostal = direccion.codigopostal();
        this.provincia = direccion.provincia();
    }

    public Direccion() {

    }

    public Direccion actualizarDatos(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.complemento = direccion.complemento();
        this.ciudad = direccion.ciudad();
        this.urbanizacion = direccion.urbanizacion();
        this.codigopostal = direccion.codigopostal();
        this.provincia = direccion.provincia();
        return this;
    }
}
