package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

public void validar(DatosAgendarConsulta datosAgendarConsulta){

    if(datosAgendarConsulta.idMedico()==null) return;

    var medicoConConsulta = consultaRepository.existsByMedicoIdAndData(datosAgendarConsulta.idMedico(),datosAgendarConsulta.fecha());

if(medicoConConsulta)
{
    throw new ValidationException("Medico con Consulta agendada en ese horario");
}
}

}
