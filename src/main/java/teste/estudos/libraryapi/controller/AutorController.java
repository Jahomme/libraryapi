package teste.estudos.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.estudos.libraryapi.controller.dto.AutorDTO;
import teste.estudos.libraryapi.controller.dto.DetalhesAutorDTO;
import teste.estudos.libraryapi.controller.mappers.AutorMapper;
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
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {

        Autor autor = mapper.toEntity(dto);
        autorService.salvar(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesAutorDTO> obterDetalhes(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor).map(autor -> {
                    DetalhesAutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletarAutor(autorOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<DetalhesAutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<DetalhesAutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    private ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody AutorDTO dto) {


        UUID idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}
