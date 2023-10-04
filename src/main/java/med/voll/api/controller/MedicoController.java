package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController // Para indicar que es un controler
@RequestMapping("/medicos") // Para mapear el path medicos
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        /*medicoRepository.save(new Medico(datosRegistroMedico));*/
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
                medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(),
                        medico.getDireccion().getUrbanizacion(),
                        medico.getDireccion().getCodigopostal(),
                        medico.getDireccion().getProvincia()
                ));
        //Debe Retornar 201
        // Y debe retorna una URL de donde encontrar al medico
        //Ejemplo: hhtp://localhost:8080/medicos/(id_del_Medico)
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> ListadoMedicos(@PageableDefault(size = 2) Pageable paginacion){
       //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico)
    {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                                   medico.getDireccion().getDistrito(),
                                   medico.getDireccion().getCiudad(),
                                   medico.getDireccion().getNumero(),
                                   medico.getDireccion().getComplemento(),
                                   medico.getDireccion().getUrbanizacion(),
                                   medico.getDireccion().getCodigopostal(),
                                   medico.getDireccion().getProvincia()


        )
        ));
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id)
    {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(
                medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(),
                        medico.getDireccion().getUrbanizacion(),
                        medico.getDireccion().getCodigopostal(),
                        medico.getDireccion().getProvincia()


                )
        );
        return ResponseEntity.ok(datosMedico);
    }

    //DELETE LOGICO, DESACTIVAR USUARIO

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id)
    {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    /*
    //DELETE POR COMPLETO DE LA BASE DE DATOS
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id)
    {
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);

    }
    */

}