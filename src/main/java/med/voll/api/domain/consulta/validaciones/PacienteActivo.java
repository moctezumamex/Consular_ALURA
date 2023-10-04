package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component /*Anotación que le indica a Spring que esta clase ya es operativa y que se puede usar*/
public class PacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta)
    {

        if(datosAgendarConsulta.idPaciente()==null)
        {
            return;
        }

        var pacienteActivo = pacienteRepository.findActivoById(datosAgendarConsulta.idPaciente());

        if(!pacienteActivo)
        {
            throw new ValidationException("No se puede agendar citas a Pacientes inactivos");
        }



    }

}
