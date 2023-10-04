package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

@Component /*Anotación que le indica a Spring que esta clase ya es operativa y que se puede usar*/
public interface ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta datosAgendarConsulta);
}
