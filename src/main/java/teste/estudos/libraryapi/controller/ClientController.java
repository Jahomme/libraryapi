package teste.estudos.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import teste.estudos.libraryapi.controller.dto.ClientDTO;
import teste.estudos.libraryapi.controller.mappers.ClientMapper;
import teste.estudos.libraryapi.model.Client;
import teste.estudos.libraryapi.service.ClientService;

import java.net.URI;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody @Valid ClientDTO dto){
        Client client = mapper.toEntity(dto);
        service.salvar(client);

    }
}
