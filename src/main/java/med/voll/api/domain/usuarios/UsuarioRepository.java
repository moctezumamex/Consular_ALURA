package med.voll.api.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
    UserDetails findByLogin(String login);
    /*las Interfaces se extienden por otras interfaces*/
    /*las clases extienden solo una otra clase*/
    /*las clases extienden muchas otras interfaces*/



}
