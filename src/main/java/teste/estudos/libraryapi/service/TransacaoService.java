package teste.estudos.libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teste.estudos.libraryapi.model.Autor;
import teste.estudos.libraryapi.model.GeneroLivro;
import teste.estudos.libraryapi.model.Livro;
import teste.estudos.libraryapi.repository.AutorRepository;
import teste.estudos.libraryapi.repository.LivroRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("Fulano");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1970, 3, 24));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setTitulo("Livro de fulano");
        livro.setIsbn("1414-5113");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setDataPublicacao(LocalDate.of(2012, 11, 18));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Fulano")){
            throw new RuntimeException("Teste Rollback!");
        }
    }
}
