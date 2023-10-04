package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamientoClinico implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datosAgendarConsulta)
    {
        var domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha().getDayOfWeek());

        var antesDeApertura= datosAgendarConsulta.fecha().getHour()<7;

        var despuesDeCierre=datosAgendarConsulta.fecha().getHour()>19;

        if(domingo||antesDeApertura||despuesDeCierre)
        {
            throw new ValidationException("El horario de la clinica es de Lunes A Sabado"
                    + "  de 7 am a 7 pm");
        }

    }
}
