package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component /*Anotación que le indica a Spring que esta clase ya es operativa y que se puede usar*/
public class PacienteConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

public void validar(DatosAgendarConsulta datosAgendarConsulta){
    var primerHorario = datosAgendarConsulta.fecha().withHour(7);
    var ultimoHorario = datosAgendarConsulta.fecha().withHour(18);

    var pacienteConConsulta = consultaRepository.existsByPacienteIdAndDataBetween(
            datosAgendarConsulta.idPaciente(),primerHorario,ultimoHorario);


if(pacienteConConsulta)
{
    throw new ValidationException("El paciente ya tiene una consulta para ese día");
}
}

}
