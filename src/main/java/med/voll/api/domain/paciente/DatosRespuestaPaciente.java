package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente(Long id, String nombre, String email, String documentoidentidad, String telefono, DatosDireccion datosDireccion) {
    }

