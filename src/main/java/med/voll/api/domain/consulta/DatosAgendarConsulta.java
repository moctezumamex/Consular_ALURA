package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

/*ESTO ES UN DTO*/
public record DatosAgendarConsulta(Long id, @NotNull Long idPaciente, Long idMedico, @NotNull @Future LocalDateTime fecha, Especialidad especialidad) {
}
