package teste.estudos.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.estudos.libraryapi.model.Autor;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
