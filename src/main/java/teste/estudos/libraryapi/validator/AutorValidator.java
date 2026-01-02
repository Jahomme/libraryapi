package teste.estudos.libraryapi.validator;

import org.springframework.stereotype.Component;
import teste.estudos.libraryapi.exceptions.RegistroDuplicadoException;
import teste.estudos.libraryapi.model.Autor;
import teste.estudos.libraryapi.repository.AutorRepository;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    public boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> optionalAutor = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );

        if(autor.getId() == null) {
            return optionalAutor.isPresent();
        }

        return optionalAutor.isPresent() && !autor.getId().equals(optionalAutor.get().getId());
    }
}
