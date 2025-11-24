package teste.estudos.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Autores")
@Slf4j
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado."),
    })
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {


        Autor autor = mapper.toEntity(dto);
        log.trace("Salvando autor: {}", autor.getNome());
        autorService.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Buscar autor", description = "Retorna os dados do autor pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
    })
    public ResponseEntity<DetalhesAutorDTO> obterDetalhes(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor).map(autor -> {
                    DetalhesAutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deletar um autor.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "400", description = "Autor possui livro cadastrado."),
    })
    public ResponseEntity<Object> deletar(@PathVariable String id) {

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.trace("Deletando autor de nome: {} e id: {}", autorOptional.get().getNome(), autorOptional.get().getId());

        autorService.deletarAutor(autorOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parâmetros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso."),
    })
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
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualiza", description = "Atualiza um autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "400", description = "Autor já cadastrado."),
    })
    private ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody AutorDTO dto) {


        UUID idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        log.trace("Atualizando autor de id: {}", autorOptional.get().getId());

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}
