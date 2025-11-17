package teste.estudos.libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import teste.estudos.libraryapi.model.GeneroLivro;
import teste.estudos.libraryapi.model.Livro;
import teste.estudos.libraryapi.repository.LivroRepository;
import teste.estudos.libraryapi.repository.specs.LivroSpecs;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro){
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Livro livro){
        repository.delete(livro);
    }

    public List<Livro> pesquisa(
            String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){

//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero));

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null){
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if(genero != null){
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }


        return repository.findAll(specs);
    }
}
