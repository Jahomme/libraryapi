package teste.estudos.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import teste.estudos.libraryapi.controller.dto.UsuarioDTO;
import teste.estudos.libraryapi.controller.mappers.UsuarioMapper;
import teste.estudos.libraryapi.service.UsuarioService;

@Slf4j
@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid UsuarioDTO dto) {

        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);


    }
}
