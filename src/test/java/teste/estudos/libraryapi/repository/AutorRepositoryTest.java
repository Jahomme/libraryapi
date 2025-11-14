package teste.estudos.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teste.estudos.libraryapi.model.Autor;
import teste.estudos.libraryapi.model.GeneroLivro;
import teste.estudos.libraryapi.model.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1990, 1, 20));

        var autorSalvo = repository.save(autor);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("9b41ddd6-7b79-47d3-876b-053af4ee13f8");

        Optional<Autor> autor = repository.findById(id);

        if(autor.isPresent()) {
            autor.get().setNome("Maria Vitória das Graças");

            repository.save(autor.get());
        }

    }

    @Test
    public void listarTest() {
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        long count = repository.count();
        System.out.println("Contagem de autores:" + count);
    }

    @Test
    public void createAutorWithLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Michael");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1970, 3, 24));

        Livro livro = new Livro();
        livro.setTitulo("Interstellar");
        livro.setIsbn("1414-5113");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setData_publicacao(LocalDate.of(2012, 11, 18));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setTitulo("Divergent");
        livro2.setIsbn("1654-5152");
        livro2.setPreco(BigDecimal.valueOf(150));
        livro2.setData_publicacao(LocalDate.of(2011, 9, 30));
        livro2.setGenero(GeneroLivro.FICCAO);
        livro2.setAutor(autor);

        autor.setLivros(List.of(livro, livro2));

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());

    }

    @Test
    void listarLivrosAutorTest() {
        var id = UUID.fromString("85cbaf92-7ae3-45b9-b408-92d5392eed8b");
        Optional<Autor> autor = repository.findById(id);

        List<Livro> livros = livroRepository.findByAutor(autor.get());
        livros.forEach(System.out::println);
    }
}
