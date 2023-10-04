package med.voll.api.domain.paciente;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente>findByActivoTrue(Pageable paginacion);

    @Query("""
    SELECT p.activo
    FROM Paciente p
    WHERE p.id =:idPaciente
""")
    Boolean findActivoById(Long idPaciente);
}
