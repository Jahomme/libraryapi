package teste.estudos.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teste.estudos.libraryapi.model.Usuario;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    public Usuario findByLogin(String login);

    public Usuario findByEmail(String email);

}
