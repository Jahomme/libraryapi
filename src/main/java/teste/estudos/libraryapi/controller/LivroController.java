package teste.estudos.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.estudos.libraryapi.controller.dto.CadastroLivroDTO;
import teste.estudos.libraryapi.controller.dto.ErroResposta;
import teste.estudos.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import teste.estudos.libraryapi.controller.mappers.LivroMapper;
import teste.estudos.libraryapi.exceptions.RegistroDuplicadoException;
import teste.estudos.libraryapi.model.Livro;
import teste.estudos.libraryapi.service.LivroService;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {

        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.accepted().location(url).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
