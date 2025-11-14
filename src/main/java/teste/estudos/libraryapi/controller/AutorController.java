package teste.estudos.libraryapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import teste.estudos.libraryapi.controller.dto.AutorDTO;
import teste.estudos.libraryapi.controller.dto.DetalhesAutorDTO;
import teste.estudos.libraryapi.controller.dto.ErroResposta;
import teste.estudos.libraryapi.exceptions.OperacaoNaoPermitidaException;
import teste.estudos.libraryapi.exceptions.RegistroDuplicadoException;
import teste.estudos.libraryapi.model.Autor;
import teste.estudos.libraryapi.service.AutorService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autorDTO) {
        try {
            Autor autorEntidade = autorDTO.mapearParaAutor();
            autorService.salvar(autorEntidade);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorEntidade.getId()).toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesAutorDTO> obterDetalhes(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.obterAutorPorId(idAutor);

        if(autor.isPresent()) {
            Autor entidade = autor.get();
            DetalhesAutorDTO dto = new DetalhesAutorDTO(entidade.getId(), entidade.getNome(), entidade.getDataNascimento(), entidade.getNacionalidade());

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        try {
            UUID idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterAutorPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            autorService.deletarAutor(autorOptional.get());

            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroDto = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping
    public ResponseEntity<List<DetalhesAutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisar(nome, nacionalidade);
        List<DetalhesAutorDTO> lista = resultado
                .stream()
                .map(autor -> new DetalhesAutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    private ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody AutorDTO dto) {

        try {
            UUID idAutor = UUID.fromString(id);

            Optional<Autor> autorOptional = autorService.obterAutorPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }


            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
