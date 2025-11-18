package teste.estudos.libraryapi.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import teste.estudos.libraryapi.exceptions.CampoInvalidoException;
import teste.estudos.libraryapi.exceptions.RegistroDuplicadoException;
import teste.estudos.libraryapi.model.Livro;
import teste.estudos.libraryapi.repository.LivroRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;


    public void validar(Livro livro) {
        if(existeIsbnCadastrado(livro)) {
            throw new RegistroDuplicadoException("Livro com isbn já cadastrado!");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicacao a partir de 2020!");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    public boolean existeIsbnCadastrado(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());


        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
