package teste.estudos.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import teste.estudos.libraryapi.model.Autor;
import teste.estudos.libraryapi.model.Livro;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    List<Livro> findByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);

    boolean existsByAutor(Autor autor);
}
