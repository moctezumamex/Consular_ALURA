package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {
    private Boolean activo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String documentoidentidad;
    private String telefono;

    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documentoidentidad = datos.documentoidentidad();
        this.direccion = new Direccion(datos.direccion());
    }

    public void atualizarDatos(DatosActualizacionPaciente datosActualizarPaciente) {
        if (datosActualizarPaciente.nombre() != null)
            this.nombre = datosActualizarPaciente.nombre();

        if (datosActualizarPaciente.telefono() != null)
            this.telefono = datosActualizarPaciente.telefono();

        if (datosActualizarPaciente.direccion() != null)
        this.direccion = direccion.actualizarDatos(datosActualizarPaciente.direccion());

    }

    public void desactivarPaciente() {
        this.activo = false;
    }

}
