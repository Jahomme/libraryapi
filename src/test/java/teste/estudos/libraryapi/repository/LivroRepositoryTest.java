package teste.estudos.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import teste.estudos.libraryapi.model.Autor;
import teste.estudos.libraryapi.model.GeneroLivro;
import teste.estudos.libraryapi.model.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

     @Test
     void salvarTest() {
         Livro livro = new Livro();
         livro.setTitulo("Game of Thrones");
         livro.setIsbn("1234-5678");
         livro.setPreco(BigDecimal.valueOf(100));
         livro.setDataPublicacao(LocalDate.of(2010, 10, 13));
         livro.setGenero(GeneroLivro.FANTASIA);

         Autor autor = autorRepository.findById(UUID.fromString("9b41ddd6-7b79-47d3-876b-053af4ee13f8")).orElse(null);

         livro.setAutor(autor);

         repository.save(livro);

     }

     @Test
     @Transactional
     void findByIdTest() {
         UUID id = UUID.fromString("e4980a5f-d5a8-4385-bbce-2785fcb794a3");
         Livro livro = repository.findById(id).orElse(null);
         System.out.println(livro);
     }
}