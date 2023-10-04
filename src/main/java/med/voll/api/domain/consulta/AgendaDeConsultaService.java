package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    /* @Autowired Es para inyectar la información del
    repositorio en la variable consultaRepository */
    @Autowired
    private ConsultaRepository consultaRepository;

    /*Los validadores que se encuentren en la lista de esta interfaz se van a inyectar en la clase con
    * ayuda del @Autowired*/
    @Autowired
    List<ValidadorDeConsultas> validadorDeConsultasList;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta)
    {
        if(!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent())
        {
            throw new ValidacionDeIntegridad("Este Id de Paciente no fue encontrado");
        }

        if(datosAgendarConsulta.idMedico()!=null && !medicoRepository.existsById(datosAgendarConsulta.idMedico()))
        {
            throw new ValidacionDeIntegridad("Este Id de Medico no fue encontrado");
        }

        /*Los validadores que se encuentren en la lista de esta interfaz se van a inyectar en la clase con
         * ayuda del @Autowired*/
        validadorDeConsultasList.forEach(v->v.validar(datosAgendarConsulta));

        var paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        //var medico = medicoRepository.findById(datosAgendarConsulta.idMedico()).get();
        var medico = seleccionarMedico(datosAgendarConsulta);

        if(medico==null)
        {
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(null, medico, paciente,datosAgendarConsulta.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico()!=null)
        {
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad()==null)
        {
            throw new ValidacionDeIntegridad("Seleccione una especialidad");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(),datosAgendarConsulta.fecha());
    }
}
