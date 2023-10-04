package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico>findByActivoTrue(Pageable paginacion);

    //primer cambio en IntelliJ
    @Query("""
            SELECT m FROM Medico m
            WHERE m.activo=true
            AND m.especialidad=:especialidad
            AND m.id NOT IN 
                    (SELECT c.medico.id from Consulta c 
                    WHERE c.data =:fecha)
            ORDER BY rand()
            limit 1
""")
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);

    @Query("""
    SELECT m.activo
    FROM Medico m
    WHERE m.id =:idMedico
""")
    Boolean findActivoById(Long idMedico);
}
