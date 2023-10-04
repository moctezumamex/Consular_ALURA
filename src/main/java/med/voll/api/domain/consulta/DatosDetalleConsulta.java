package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/*ESTO ES UN DTO*/
public record DatosDetalleConsulta(Long id, Long idPaciente, Long idMedico, LocalDateTime fecha) {
    public DatosDetalleConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(),consulta.getData());
    }
}
