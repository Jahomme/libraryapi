package teste.estudos.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.estudos.libraryapi.model.Autor;
import teste.estudos.libraryapi.model.Livro;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);

    boolean existsByAutor(Autor autor);
}
