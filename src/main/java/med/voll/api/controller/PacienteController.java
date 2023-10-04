package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> registrar(@RequestBody @Valid DatosRegistroPaciente datos,
                                                            UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente =  pacienteRepository.save(new Paciente(datos));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(
                paciente.getId(),paciente.getNombre(),paciente.getEmail(),
                paciente.getDocumentoidentidad(),paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(),
                        paciente.getDireccion().getUrbanizacion(),
                        paciente.getDireccion().getCodigopostal(),
                        paciente.getDireccion().getProvincia()


                )

        );
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaPaciente>> listar(@PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable paginacion) {
        //return pacienteRepository.findAll(paginacion).map(DatosListaPaciente::new);
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListaPaciente::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> atualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datosActualizarPaciente)
    {
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.atualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(
                new DatosRespuestaPaciente(
                        paciente.getId(),paciente.getNombre(),paciente.getEmail(),
                        paciente.getDocumentoidentidad(),paciente.getTelefono(),
                        new DatosDireccion(paciente.getDireccion().getCalle(),
                                paciente.getDireccion().getDistrito(),
                                paciente.getDireccion().getCiudad(),
                                paciente.getDireccion().getNumero(),
                                paciente.getDireccion().getComplemento(),
                                paciente.getDireccion().getUrbanizacion(),
                                paciente.getDireccion().getCodigopostal(),
                                paciente.getDireccion().getProvincia()


                        )
                )
        );
    }

    //Es para muestrar los datos al buscar la URL que genera el ResposeEntity al guardar un archivo
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> retornaDatosPaciente(@PathVariable Long id)
    {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaPaciente(
                paciente.getId(),paciente.getNombre(),paciente.getEmail(),
                paciente.getDocumentoidentidad(),paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(),
                        paciente.getDireccion().getUrbanizacion(),
                        paciente.getDireccion().getCodigopostal(),
                        paciente.getDireccion().getProvincia()


                )
        )
                ;
        return ResponseEntity.ok(datosMedico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

}
